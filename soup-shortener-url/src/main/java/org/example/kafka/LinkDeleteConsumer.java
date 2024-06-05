package org.example.kafka;

import lombok.SneakyThrows;
import org.example.kafka.dto.LinkDeleteKafkaMessage;
import org.example.service.LinkService;
import org.example.utils.BaseConversion;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LinkDeleteConsumer {
    private final LinkService linkService;

    public LinkDeleteConsumer(LinkService linkService) {
        this.linkService = linkService;
    }

    @SneakyThrows
    @KafkaListener(topics = "link-delete-topic")
    public void listen(LinkDeleteKafkaMessage linkDeleteKafkaMessage) {
        if (linkDeleteKafkaMessage.id() != null) {
            String shortLink = BaseConversion.toBase(linkDeleteKafkaMessage.id());
            linkService.deleteLink(shortLink);
        }
    }
}
