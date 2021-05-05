package com.canknow.cbp.webCommon.adapter.inbound.web.auditLog;

import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.canknow.cbp.web.adapter.inbound.web.clientInfo.ClientInfo;
import com.canknow.cbp.web.adapter.inbound.web.utils.ClientInfoUtil;
import com.canknow.cbp.base.application.dto.ApiResult;
import com.canknow.cbp.base.auditLog.RequestInfo;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import com.canknow.cbp.web.adapter.inbound.web.utils.IpUtil;
import com.canknow.cbp.webCommon.adapter.inbound.web.security.TokenHelper;
import com.canknow.cbp.common.domain.auditLog.AuditLog;
import com.canknow.cbp.base.auditLog.annotation.AuditLogIgnore;
import com.canknow.cbp.common.domain.auditLog.IAuditLogRepository;
import com.canknow.cbp.utils.DateUtils;
import com.canknow.cbp.utils.JsonUtils;
import com.canknow.cbp.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(value = {"application.aop.log.enable"}, matchIfMissing = true)
public class AuditLogInterceptor {
    private static final String REQUEST_ID = "requestId";

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 本地线程变量，保存请求参数信息到当前线程中
     */
    protected static ThreadLocal<RequestInfo> requestInfoThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<AuditLog> auditLogThreadLocal = new ThreadLocal<>();

    @Autowired
    private IApplicationSession applicationSession;

    @Autowired
    private IAuditLogRepository auditLogRepository;

    @Pointcut("@annotation(com.canknow.cbp.base.auditLog.annotation.Audit)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return handle(joinPoint);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        handleAfterThrowing(exception);
    }

    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 获取当前的HttpServletRequest对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // HTTP请求信息对象
            RequestInfo requestInfo = new RequestInfo();

            // 请求路径 /api/foobar/add
            String requestURI = request.getRequestURI();
            requestInfo.setRequestURI(requestURI);
            // 获取实际路径 /foobar/add
            String realPath = getRealPath(requestURI);
            requestInfo.setRealPath(realPath);

            // 获取请求类名和方法名称
            Signature signature = joinPoint.getSignature();

            // 获取真实的方法对象
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            // 处理操作日志信息
            createAuditLog(method);

            // IP地址
            String ip = IpUtil.getRequestIp();
            requestInfo.setIp(ip);

            // 获取请求方式
            String requestMethod = request.getMethod();
            requestInfo.setRequestMethod(requestMethod);

            // 获取请求内容类型
            String contentType = request.getContentType();
            requestInfo.setContentType(contentType);

            // 判断控制器方法参数中是否有RequestBody注解
            Annotation[][] annotations = method.getParameterAnnotations();
            boolean isRequestBody = isRequestBody(annotations);
            requestInfo.setRequestBody(isRequestBody);

            // 设置请求参数
            Object requestParamObject = getRequestParamObject(joinPoint, request, requestMethod, contentType, isRequestBody);
            requestInfo.setParam(requestParamObject);
            requestInfo.setTime(DateUtils.getDateTimeString(LocalDateTime.now()));

            // 获取请求头token
            String token = TokenHelper.getTokenFromRequest(request);
            requestInfo.setToken(token);

            if (StringUtils.isNotBlank(token)) {
                requestInfo.setTokenMd5(DigestUtils.md5Hex(token));
            }

            // 用户浏览器代理字符串
            requestInfo.setUserAgent(request.getHeader("User-Agent"));

            setRequestId(requestInfo);

            requestInfoThreadLocal.set(requestInfo);
        }
        catch (Exception e) {
            log.error("请求日志AOP处理异常", e);
        }

        // 执行目标方法,获得返回值
        // 方法异常时，会调用子类的@AfterThrowing注解的方法，不会调用下面的代码，异常单独处理
        long beginTime = SystemClock.now();
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long duration = SystemClock.now() - beginTime;

        handleAfterReturn(result, duration,null);
        return result;
    }

    public void handleAfterThrowing(Exception exception) {
        RequestInfo requestInfo = requestInfoThreadLocal.get();
        AuditLog auditLog = auditLogThreadLocal.get();
        saveAuditLog(requestInfo, auditLog, null, null, exception);
        remove();
    }

    protected void setRequestId(RequestInfo requestInfo) {
        String requestId = UUIDUtils.getUuid();
        MDC.put(REQUEST_ID, requestId);
        requestInfo.setRequestId(requestId);
    }

    protected void handleAfterReturn(Object result, long duration, Exception exception) {
        // 获取RequestInfo
        RequestInfo requestInfo = requestInfoThreadLocal.get();
        // 获取OperationLogInfo
        AuditLog auditLog = auditLogThreadLocal.get();
        // 调用抽象方法，是否保存日志操作，需要子类重写该方法，手动调用saveSysOperationLog
        saveAuditLog(requestInfo, auditLog, result, duration, null);
        // 释放资源
        remove();
    }

    protected void remove() {
        requestInfoThreadLocal.remove();
        auditLogThreadLocal.remove();
        MDC.clear();
    }

    @Async
    protected void saveAuditLog(RequestInfo requestInfo, AuditLog auditLog, Object result, Long duration, Exception exception) {
        if (auditLog.isIgnore()) {
            return;
        }
        if (requestInfo != null) {
            auditLog.setIp(requestInfo.getIp())
                    .setRequestMethod(requestInfo.getRequestMethod())
                    .setRequestId(requestInfo.getRequestId())
                    .setRequestMethod(requestInfo.getRequestMethod())
                    .setContentType(requestInfo.getContentType())
                    .setRequestBody(requestInfo.getRequestBody())
                    .setToken(requestInfo.getTokenMd5());

            auditLog.setParam(JsonUtils.objectToJson(requestInfo.getParam()));
            ClientInfo clientInfo = ClientInfoUtil.get(requestInfo.getUserAgent());

            if (clientInfo != null) {
                auditLog.setBrowserName(clientInfo.getBrowserName())
                        .setBrowserVersion(clientInfo.getBrowserVersion())
                        .setEngineName(clientInfo.getEngineName())
                        .setEngineVersion(clientInfo.getEngineVersion())
                        .setOsName(clientInfo.getOsName())
                        .setPlatformName(clientInfo.getPlatformName())
                        .setDeviceName(clientInfo.getDeviceName())
                        .setDeviceModel(clientInfo.getDeviceModel());
            }
        }

        try {
            if (requestInfo != null) {
                auditLog.setIp(requestInfo.getIp())
                        .setRequestUrl(requestInfo.getRequestURI())
                        .setRequestId(requestInfo.getRequestId())
                        .setRequestMethod(requestInfo.getRequestMethod())
                        .setContentType(requestInfo.getContentType())
                        .setRequestBody(requestInfo.getRequestBody())
                        .setToken(requestInfo.getTokenMd5());
            }

            // 设置响应结果
            if (result != null && result instanceof ApiResult) {
                ApiResult<?> apiResult = (ApiResult<?>) result;
                auditLog.setSuccess(apiResult.isSuccess())
                        .setCode(apiResult.getCode())
                        .setMessage(apiResult.getMessage());
            }

            auditLog.setDuration(duration);

            // 设置当前登录信息
            auditLog.setUserId(applicationSession.getUserId());
            auditLog.setUserName(applicationSession.getUserName());

            // 设置异常信息
            if (exception != null) {
                Integer errorCode = null;
                String exceptionMessage = exception.getMessage();
                // 异常字符串长度截取
                auditLog.setSuccess(false)
                        .setCode(errorCode)
                        .setExceptionMessage(exceptionMessage)
                        .setExceptionName(exception.getClass().getName());
            }
            // 保存日志到数据库
            auditLogRepository.insert(auditLog);
        }
        catch (Exception e) {
            if (e instanceof JWTDecodeException) {
                throw (JWTDecodeException) e;
            }
            log.error("保存系统操作日志失败", e);
        }
    }

    protected Object getRequestParamObject(ProceedingJoinPoint joinPoint, HttpServletRequest request, String requestMethod, String contentType, boolean isRequestBody) {
        Object paramObject = null;
        if (isRequestBody) {
            // POST,application/json,RequestBody的类型,简单判断,然后序列化成JSON字符串
            Object[] args = joinPoint.getArgs();
            paramObject = getArgsObject(args);
        }
        else {
            // 获取getParameterMap中所有的值,处理后序列化成JSON字符串
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getParamJSONObject(paramsMap);
        }
        return paramObject;
    }

    protected JSONObject getParamJSONObject(Map<String, String[]> paramsMap) {
        if (MapUtils.isEmpty(paramsMap)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            // 没有值
            if (values == null) {
                jsonObject.put(key, null);
            }
            else if (values.length == 1) {
                // 一个值
                jsonObject.put(key, values[0]);
            }
            else {
                // 多个值
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }

    protected Object getArgsObject(Object[] args) {
        if (args == null) {
            return null;
        }
        // 去掉HttpServletRequest和HttpServletResponse
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                continue;
            }
            if (arg instanceof ModelAndView) {
                continue;
            }
            realArgs.add(arg);
        }
        if (realArgs.size() == 1) {
            return realArgs.get(0);
        }
        else {
            return realArgs;
        }
    }

    protected boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }

    private String getRealPath(String requestPath) {
        // 如果项目路径不为空，则去掉项目路径，获取实际访问路径
        if (StringUtils.isNotBlank(contextPath)) {
            return requestPath.substring(contextPath.length());
        }
        return requestPath;
    }

    private void createAuditLog(Method method) {
        // 设置控制器类名称和方法名称
        AuditLog auditLog = new AuditLog()
                .setControllerName(method.getDeclaringClass().getName())
                .setActionName(method.getName());

        // 获取Module类注解
        Class<?> controllerClass = method.getDeclaringClass();

        // 获取OperationLogIgnore注解
        AuditLogIgnore classOperationLogIgnore = controllerClass.getAnnotation(AuditLogIgnore.class);

        if (classOperationLogIgnore != null) {
            // 不记录日志
            auditLog.setIgnore(true);
        }
        // 判断方法是否要过滤
        AuditLogIgnore operationLogIgnore = method.getAnnotation(AuditLogIgnore.class);

        if (operationLogIgnore != null) {
            auditLog.setIgnore(true);
        }
        auditLogThreadLocal.set(auditLog);
    }
}
