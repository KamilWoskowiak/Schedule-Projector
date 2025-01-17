package com.scheduleProjector.scheduleProjector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
