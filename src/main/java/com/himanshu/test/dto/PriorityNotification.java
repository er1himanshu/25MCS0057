package com.himanshu.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriorityNotification {
    private ExternalNotification notification;
    private int score;
}