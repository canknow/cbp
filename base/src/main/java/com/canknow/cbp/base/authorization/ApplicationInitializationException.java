package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;

public class ApplicationInitializationException extends ApplicationException {
    private static final long serialVersionUID = -5625508504719875602L;

    public ApplicationInitializationException(String message) {
        super(message);
    }
}
