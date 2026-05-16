package com.himanshu.test.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.himanshu.test.dto.AuthRequest;
import com.himanshu.test.dto.AuthResponse;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_URL =
            "http://4.224.186.213/evaluation-service/auth";

    public String getToken() {

        AuthRequest request = new AuthRequest(
                "himanshu.soni2025@vitstudent.ac.in",
                "himanshu soni",
                "25mcs0057",
                "SfFuWg",
                "314f1534-32e7-4267-afa7-1a41c6949cb2",
                "aHJdfQbgjEKpgxKZ"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<AuthResponse> response =
                restTemplate.postForEntity(
                        AUTH_URL,
                        entity,
                        AuthResponse.class
                );

        return response.getBody().getAccess_token();
    }
}