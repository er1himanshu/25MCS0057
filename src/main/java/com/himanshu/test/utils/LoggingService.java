package com.himanshu.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.himanshu.test.auth.AuthService;
import com.himanshu.test.dto.LogRequest;

@Service
public class LoggingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    private static final String LOG_URL =
            "http://4.224.186.213/evaluation-service/logs";

    public void log(String level, String packageName, String message) {
        try {

            String token = authService.getToken();

            LogRequest request = new LogRequest(
                    "backend",
                    level,
                    packageName,
                    message
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<LogRequest> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            LOG_URL,
                            entity,
                            String.class
                    );

            System.out.println("Log sent: " + response.getBody());

        } catch (Exception e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}