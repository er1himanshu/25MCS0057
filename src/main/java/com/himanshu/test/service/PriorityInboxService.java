package com.himanshu.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.himanshu.test.auth.AuthService;
import com.himanshu.test.dto.ExternalNotification;
import com.himanshu.test.dto.NotificationApiResponse;
import com.himanshu.test.dto.PriorityNotification;
import com.himanshu.test.utils.LoggingService;

@Service
public class PriorityInboxService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private AuthService authService;

    private static final String API_URL =
            "http://4.224.186.213/evaluation-service/notifications";

    public List<ExternalNotification> getTopNotifications(int topN) {

        loggingService.log(
                "info",
                "service",
                "Fetching notifications from external API"
        );

        String token = authService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<NotificationApiResponse> response =
                restTemplate.exchange(
                        API_URL,
                        HttpMethod.GET,
                        entity,
                        NotificationApiResponse.class
                );

        if (response.getBody() == null ||
                response.getBody().getNotifications() == null) {
            return new ArrayList<>();
        }

        PriorityQueue<PriorityNotification> heap =
                new PriorityQueue<>(
                        Comparator.comparingInt(
                                PriorityNotification::getScore
                        )
                );

        for (ExternalNotification n : response.getBody().getNotifications()) {

            int score = calculateScore(n);

            if (heap.size() < topN) {
                heap.offer(new PriorityNotification(n, score));
            } else if (!heap.isEmpty() && score > heap.peek().getScore()) {
                heap.poll();
                heap.offer(new PriorityNotification(n, score));
            }
        }

        List<ExternalNotification> result = new ArrayList<>();

        while (!heap.isEmpty()) {
            result.add(heap.poll().getNotification());
        }

        Collections.reverse(result);

        loggingService.log(
                "info",
                "service",
                "Priority notifications prepared successfully"
        );

        return result;
    }

    private int calculateScore(ExternalNotification n) {

        int score = 0;

        if (n == null) {
            return 0;
        }

        String type = n.getType();

        if (type != null) {
            switch (type.toLowerCase()) {
                case "placement":
                    score += 300;
                    break;

                case "result":
                    score += 200;
                    break;

                case "event":
                    score += 100;
                    break;

                default:
                    score += 50;
            }
        } else {
            score += 50;
        }

        score += 10;

        return score;
    }
}