package com.poliana.web.error;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public class ForbiddenException extends SecurityException {
    public ForbiddenException() {
        super("Sorry, looks like you don't have access to that. Do you have an API key?");
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final Throwable cause) {
        super(cause);
    }
}
