package org.example.kafka;

import org.example.kafka.dto.VisitLinkMessage;
import org.example.repository.LinkRepository;
import org.example.repository.entity.LinkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VisitLinkListener {
    @Autowired
    private LinkRepository linkRepository;

    @Transactional
    @KafkaListener(topics = "visit-link", groupId = "link-group", containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload VisitLinkMessage visitLinkMessage) {
        LinkEntity linkEntity = linkRepository.getReferenceById(visitLinkMessage.id());
        linkEntity.setVisitCount(linkEntity.getVisitCount() + 1);
        linkRepository.save(linkEntity);
    }
}
