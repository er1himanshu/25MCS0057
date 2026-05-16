package com.himanshu.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String email;
    private String name;
    private String rollNo;
    private String accessCode;
    private String clientID;
    private String clientSecret;
}