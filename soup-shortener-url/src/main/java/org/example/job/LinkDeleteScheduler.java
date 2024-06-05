package org.example.job;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.kafka.LinkDeleteProducer;
import org.example.service.LinkService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinkDeleteScheduler {
    private static final String LINK_DELETE_LOCK_NAME = "link-delete-lock";

    private final LinkDeleteProducer linkDeleteProducer;
    private final LinkService linkService;

    public LinkDeleteScheduler(LinkDeleteProducer linkDeleteProducer, LinkService linkService) {
        this.linkDeleteProducer = linkDeleteProducer;
        this.linkService = linkService;
    }

    @SchedulerLock(name = LINK_DELETE_LOCK_NAME, lockAtMostFor = "PT20H", lockAtLeastFor = "PT20H")
    @Scheduled(cron = "0 0 3 * * *", zone = "Europe/Moscow")
    public void deleteLinks() {
        try {
            List<Long> ids = linkService.getAllNotUpdatedForAMonth();
            linkDeleteProducer.sendMessages(ids);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
