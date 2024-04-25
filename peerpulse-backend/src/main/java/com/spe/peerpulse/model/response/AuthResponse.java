package com.spe.peerpulse.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long id;
    private String token;
    private String username;
    private String role;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String message;
}

