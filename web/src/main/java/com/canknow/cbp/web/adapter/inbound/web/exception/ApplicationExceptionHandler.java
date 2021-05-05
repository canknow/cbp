package com.canknow.cbp.web.adapter.inbound.web.exception;

import com.canknow.cbp.base.application.dto.ApiCode;
import com.canknow.cbp.base.application.dto.ApiResult;
import com.canknow.cbp.base.exceptions.BusinessException;
import com.canknow.cbp.base.exceptions.InvalidParameterException;
import com.canknow.cbp.base.localization.ILocalizationManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    @Autowired
    private ILocalizationManager localizationManager;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<?> handle(HttpServletRequest request, Exception e) {
        logger(request, e);
        return ApiResult.fail(e.getMessage());
    }

    // <1> 处理 form data方式调用接口校验失败抛出的异常
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handle(HttpServletRequest request, BindException e) {
        logger(request, e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return createValidExceptionResult(fieldErrors);
    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
        logger(request, e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return createValidExceptionResult(fieldErrors);
    }

    // <3> 处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream().map(constraintViolation->localizationManager.getLocalizationText(constraintViolation.getMessage()))
                .collect(Collectors.toList());
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, localizationManager.getLocalizationText("requestParameterException"), collect);
    }

    private ApiResult<?> createValidExceptionResult(List<FieldError> fieldErrors) {
        List<String> collect = fieldErrors.stream()
                .map(defaultMessageSourceResolvable -> localizationManager.getLocalizationText(defaultMessageSourceResolvable.getDefaultMessage()))
                .collect(Collectors.toList());
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, localizationManager.getLocalizationText("requestParameterException"), collect);
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handle(HttpServletRequest request, InvalidParameterException e) {
        logger(request, e);
        return ApiResult.fail(e.getApiCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<?> handle(HttpServletRequest request, BusinessException e) {
        logger(request, e);
        return ApiResult.fail(e.getApiCode(), e.getMessage());
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult<?> handle(HttpServletRequest request, UnauthenticatedException e) {
        logger(request, e);
        String message = e.getMessage();
        return ApiResult.fail(ApiCode.UNAUTHORIZED, message);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResult<?> handle(HttpServletRequest request, UnauthorizedException e) {
        logger(request, e);
        String message = e.getMessage();
        return ApiResult.fail(ApiCode.NOT_PERMISSION, message);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handle(HttpServletRequest request, AuthenticationException e) {
        logger(request, e);
        String message = e.getMessage();
        return ApiResult.fail(ApiCode.AUTHENTICATION_EXCEPTION, message);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleDuplicateKeyException(HttpServletRequest request,DuplicateKeyException e){
        logger(request, e);
        return ApiResult.fail(localizationManager.getLocalizationText("theRecordAlreadyExists"));
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleMaxUploadSizeExceededException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        logger(request, e);
        return ApiResult.fail("文件大小超出10MB限制, 请压缩或降低文件质量！");
    }

    private void logger(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        String contentType = request.getHeader("Content-Type");
        log.error("ApplicationExceptionHandler uri: {} content-type: {} exception: {}", request.getRequestURI(), contentType, e.toString());
    }
}
