package com.ondot.ondot_back.domain.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponse {
    private String accessToken;
    private String refreshToken;
}
