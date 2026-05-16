package com.himanshu.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.test.dto.ExternalNotification;
import com.himanshu.test.service.PriorityInboxService;
import com.himanshu.test.utils.LoggingService;

@RestController
@RequestMapping("/api")
public class PriorityInboxController {

    @Autowired
    private PriorityInboxService priorityInboxService;

    @Autowired
    private LoggingService loggingService;

    @GetMapping("/priority")
    public List<ExternalNotification> getPriorityInbox(
            @RequestParam(defaultValue = "5") int topN
    ) {

        loggingService.log(
                "info",
                "controller",
                "Priority inbox endpoint called"
        );

        return priorityInboxService.getTopNotifications(topN);
    }
}