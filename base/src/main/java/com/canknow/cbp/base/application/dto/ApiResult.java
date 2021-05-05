package com.canknow.cbp.base.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@Builder
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = -7055789241612821708L;
    private String message;
    private int code;
    private T data;
    private long timestamp = System.currentTimeMillis();

    public boolean isSuccess () {
        return code == ApiCode.SUCCESS.getCode();
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> apiResult = new ApiResult();
        apiResult.setCode(ApiCode.SUCCESS.getCode());
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> ApiResult<T> fail (String message) {
        return fail(ApiCode.FAIL, message, null);
    }

    public static <T> ApiResult<T> fail (ApiCode apiCode, String message) {
        return new ApiResult<>(apiCode, message);
    }

    public static <T> ApiResult<T> fail (String message, T data) {
        return fail(ApiCode.FAIL, message, data);
    }

    public static <T> ApiResult<T> fail (ApiCode apiCode, String message, T data) {
        ApiResult<T> apiResult = (ApiResult<T>) ApiResult.builder().build();
        apiResult.code = apiCode.getCode();
        apiResult.data = data;
        String apiMessage = apiCode.getMessage();

        if (StringUtils.isEmpty(message)){
            message = apiMessage;
        }
        apiResult.message = message;
        return apiResult;
    }

    public ApiResult(ApiCode apiCode, String message){
        this.code = apiCode.getCode();
        this.message = StringUtils.isNoneBlank(message) ? message : apiCode.getMessage();
    }

    public ApiResult(){
        this.code = ApiCode.SUCCESS.getCode();
        this.message = "请求成功";
    }

    public ApiResult(T data){
        this();
        this.data = data;
    }
}
