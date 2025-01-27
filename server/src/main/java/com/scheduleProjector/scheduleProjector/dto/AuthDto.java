package com.scheduleProjector.scheduleProjector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthDto {
    private String accessToken;
    private String refreshToken;
}
