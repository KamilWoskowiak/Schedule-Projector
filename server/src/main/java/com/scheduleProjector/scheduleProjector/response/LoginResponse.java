package com.scheduleProjector.scheduleProjector.response;


public class LoginResponse {
    private String jwtToken;
    private long expiresIn;

    public LoginResponse(String jwtToken, long expiresIn) {
        this.jwtToken = jwtToken;
        this.expiresIn = expiresIn;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
