package com.canknow.cbp.base.exceptions;

import com.canknow.cbp.base.application.dto.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CbpException extends RuntimeException{

    private static final long serialVersionUID = -2470461654663264392L;

    private ApiCode apiCode;
    private String message;

    public CbpException(String message) {
        super(message);
        apiCode = ApiCode.FAIL;
        this.message = message;
    }

    public CbpException(ApiCode apiCode, String message) {
        super(message);
        this.apiCode = apiCode;
        this.message = message;
    }

    public CbpException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.apiCode = apiCode;
        this.message = apiCode.getMessage();
    }
}
