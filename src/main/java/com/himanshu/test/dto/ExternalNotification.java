package com.himanshu.test.dto;

import lombok.Data;

@Data
public class ExternalNotification {
    private String ID;
    private String Type;
    private String Message;
    private String Timestamp;
}