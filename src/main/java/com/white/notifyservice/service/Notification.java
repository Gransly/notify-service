package com.white.notifyservice.service;

import com.white.notifyservice.entity.DailySummaryMessage;
import com.white.notifyservice.entity.NotificationMessage;
import org.springframework.scheduling.annotation.Async;

public interface Notification {

    @Async

    void sendNotification(NotificationMessage messageArg);

    @Async
    void sendDaily(DailySummaryMessage messageArg);
}
