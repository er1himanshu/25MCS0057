package com.himanshu.test.dto;

import java.util.List;

import lombok.Data;

@Data
public class NotificationApiResponse {
    private List<ExternalNotification> notifications;
}