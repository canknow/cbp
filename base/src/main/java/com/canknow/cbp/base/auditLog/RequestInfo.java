package com.canknow.cbp.base.auditLog;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RequestInfo implements Serializable {
    private static final long serialVersionUID = 1421424612944015973L;

    /**
     * 请求路径
     * /api/foobar/add
     */
    private String requestURI;

    /**
     * 请求ID
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String requestId;

    /**
     * 请求实际路径
     * /foobar/add
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String realPath;

    /**
     * 请求IP地址
     */
    private String ip;

    /**
     * 请求方式，GET/POST
     */
    private String requestMethod;

    /**
     * 请求内容类型
     */
    private String contentType;

    /**
     * 判断控制器方法参数中是否有RequestBody注解
     */
    private Boolean requestBody;

    /**
     * 请求参数对象
     */
    private Object param;

    /**
     * 请求时间字符串
     */
    private String time;

    /**
     * 请求token
     */
    private String token;

    /**
     * 请求token MD5值
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String tokenMd5;

    /**
     * 用户代理字符串
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String userAgent;

    private String requiresRoles;

    private String requiresPermissions;

    private Boolean requiresAuthentication;

    private Boolean requiresUser;

    private Boolean requiresGuest;

}
