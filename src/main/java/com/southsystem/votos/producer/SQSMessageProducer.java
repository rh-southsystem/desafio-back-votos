package com.southsystem.votos.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SQSMessageProducer {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endPoint;

    public <T> void send(T message, Map<String, Object> headers) {
        if (message == null) {
            log.warn("SQS Producer cant produce 'null' value");
            return;
        }

        log.debug(" Message {} " + message);
        log.debug(" Queue name {} " + endPoint);
        queueMessagingTemplate.convertAndSend(endPoint, message);
    }

    public <T> void send(T message) {
        send(message, null);
    }

}
