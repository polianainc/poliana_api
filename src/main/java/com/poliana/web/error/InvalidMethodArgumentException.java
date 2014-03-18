package com.poliana.web.error;

/**
 * @author David Gilmore
 * @date 3/17/14
 */
public class InvalidMethodArgumentException extends RuntimeException {

    public InvalidMethodArgumentException() {
        super("There was a problem with your input");
    }

    public InvalidMethodArgumentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidMethodArgumentException(final String message) {
        super(message);
    }

    public InvalidMethodArgumentException(final Throwable cause) {
        super(cause);
    }
}