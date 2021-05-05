package com.canknow.cbp.base.exceptions;

import com.canknow.cbp.base.application.dto.ApiCode;

public class BusinessException extends CbpException {
	private static final long serialVersionUID = -2303357122330162359L;

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ApiCode apiCode, String message) {
        super(apiCode, message);
    }

    public BusinessException(ApiCode apiCode) {
        super(apiCode);
    }
}
