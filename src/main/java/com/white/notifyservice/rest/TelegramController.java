package com.white.notifyservice.rest;

import com.white.notifyservice.entity.DailySummaryMessage;
import com.white.notifyservice.entity.NotificationMessage;
import com.white.notifyservice.service.Notification;
import com.white.notifyservice.service.TelegramBotNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TelegramController {

    private final Notification telegramBotService;


    @PostMapping(value = "/message/action")
    public void interceptRequest(@RequestBody @Valid NotificationMessage notification) {
        telegramBotService.sendNotification(notification);
    }

    @PostMapping(value = "/message/summary")
    public void interceptRequest(@RequestBody @Valid DailySummaryMessage notification) {
        telegramBotService.sendDaily(notification);
    }
}
