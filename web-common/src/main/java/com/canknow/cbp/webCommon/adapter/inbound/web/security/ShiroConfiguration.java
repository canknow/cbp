package com.canknow.cbp.webCommon.adapter.inbound.web.security;

import com.canknow.cbp.utils.ConvertUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ShiroConfiguration {

    private static final String ANON = "anon";

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public ShiroFilterFactoryBean filterFactory(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //自定义过滤器
        Map<String, Filter> filterMap = new ConcurrentHashMap<>();
        filterMap.put("jwt", new JWTHttpAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //权限控制map
        LinkedHashMap<String,String> filterRules = new LinkedHashMap<>();

        // 配置不会被拦截的链接 顺序判断
        filterRules.put("/", ANON);
        filterRules.put("/doc.html", ANON);
        filterRules.put("/**/*.js", ANON);
        filterRules.put("/**/*.css", ANON);
        filterRules.put("/**/*.html", ANON);
        filterRules.put("/**/*.svg", ANON);
        filterRules.put("/**/*.pdf", ANON);
        filterRules.put("/**/*.jpg", ANON);
        filterRules.put("/**/*.png", ANON);
        filterRules.put("/**/*.ico", ANON);
        filterRules.put("/swagger-ui.html", ANON);
        filterRules.put("/v2/api-docs", ANON);
        filterRules.put("/swagger-resources/**", ANON);
        filterRules.put("/webjars/**", ANON);
        filterRules.put("/static/**", ANON);
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterRules.put("/**/authorization/login", ANON);
        filterRules.put("/**/authorization/logout", ANON);

        filterRules.put("/**/*.ttf", "anon");
        filterRules.put("/**/*.woff", "anon");
        filterRules.put("/**/*.woff2", "anon");

        // 所有请求通过我们自己的JWT Filter
        filterRules.put("/**", "jwt");

        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRules);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("authorizingRealm") JWTAuthorizingRealm authorizingRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        securityManager.setSessionManager(sessionManager());

        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(ConvertUtils.getInt(port));
        redisManager.setTimeout(0);

        if (!StringUtils.isEmpty(redisPassword)) {
            redisManager.setPassword(redisPassword);
        }
        return redisManager;
    }

    /**
     * 身份认证Realm，此处的注入不可以缺少。否则会在UserRealm中注入对象会报空指针.
     * @return
     */
    @Bean("authorizingRealm")
    public JWTAuthorizingRealm authorizingRealm(@Qualifier("credentialMatcher") CredentialsMatcher matcher){
        JWTAuthorizingRealm authorizingRealm = new JWTAuthorizingRealm();
        authorizingRealm.setCredentialsMatcher(matcher);
        return authorizingRealm;
    }

    /**
     * 哈希密码比较器。在myShiroRealm中作用参数使用
     * 登陆时会比较用户输入的密码，跟数据库密码配合盐值salt解密后是否一致。
     * @return
     */
    @Bean("credentialMatcher")
    public JWTCredentialsMatcher hashedCredentialsMatcher(){
        return new JWTCredentialsMatcher();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
