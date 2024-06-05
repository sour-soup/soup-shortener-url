package org.example.kafka;

import org.example.kafka.dto.LinkDeleteKafkaMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinkDeleteProducer {

    private final KafkaTemplate<String, LinkDeleteKafkaMessage> kafkaLinkDeleteTemplate;

    public LinkDeleteProducer(KafkaTemplate<String, LinkDeleteKafkaMessage> kafkaLinkDeleteTemplate) {
        this.kafkaLinkDeleteTemplate = kafkaLinkDeleteTemplate;
    }

    public void sendMessages(List<Long> ids) {
        for (Long id : ids) {
            kafkaLinkDeleteTemplate.send("link-delete-topic", new LinkDeleteKafkaMessage(id));
        }
    }
}
