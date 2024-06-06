package org.example.kafka;

import org.example.kafka.dto.VisitLinkMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class VisitLinkSender {
    @Autowired
    private KafkaTemplate<String, VisitLinkMessage> visitLinkMessageKafkaTemplate;

    public void sendMessage(VisitLinkMessage visitLinkMessage) {
        visitLinkMessageKafkaTemplate.send("visit-link", visitLinkMessage);
    }
}
