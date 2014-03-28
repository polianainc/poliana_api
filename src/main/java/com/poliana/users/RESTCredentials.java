package com.poliana.users;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public final class RESTCredentials {

    private String requestSalt;
    private String secureHash;

    private RESTCredentials() {
    }

    public RESTCredentials(String requestSalt, String secureHash) {
        this.requestSalt = requestSalt;
        this.secureHash = secureHash;
    }

    public String getRequestSalt() {
        return requestSalt;
    }

    public String getSecureHash() {
        return secureHash;
    }
}
