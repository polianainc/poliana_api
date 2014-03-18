package com.poliana.web.error;

/**
 * @author David Gilmore
 * @date 3/16/14
 */
public final class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Sorry, we couldn't find that one.");
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }
}
