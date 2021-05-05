package com.canknow.cbp.web.adapter.inbound.web.interceptors;

import com.canknow.cbp.base.application.dto.ApiResult;
import com.canknow.cbp.base.enums.IApplicationPackageProvider;
import com.canknow.cbp.web.adapter.inbound.web.consts.ProjectConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@ControllerAdvice
public class ResponseWrap implements ResponseBodyAdvice {
    @Autowired
    private List<IApplicationPackageProvider> applicationPackageProviders;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.hasMethodAnnotation(DisableResponseWrap.class)) {
            return false;
        }

        String packageName = methodParameter.getContainingClass().getPackage().getName();

        for (IApplicationPackageProvider applicationPackageProvider : applicationPackageProviders) {
            if (packageName.startsWith(applicationPackageProvider.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (!(body instanceof ApiResult)) {
            return new ApiResult<>(body);
        }

        return body;
    }
}
