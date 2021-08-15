package com.white.notifyservice.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class NotificationMessage {

    LocalDateTime callTime;

    String methodName;

    String description;

    String exceptionName;
}
