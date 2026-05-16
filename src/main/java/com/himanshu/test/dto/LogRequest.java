package com.himanshu.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {

    private String stack;
    private String level;
    private String packageName;
    private String message;
}