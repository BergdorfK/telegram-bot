package pro.sky.telegrambot.scheduler.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.scheduler.SchedulerNotification;
import pro.sky.telegrambot.service.MessageSender;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SchedulerNotificationImpl implements SchedulerNotification {

    private final MessageSender messageSender;
    private final NotificationTaskRepository repository;

    public SchedulerNotificationImpl(MessageSender messageSender, NotificationTaskRepository repository) {
        this.messageSender = messageSender;
        this.repository = repository;
    }

    @Override
    @Scheduled(cron = "*/1 * * * * *")
    public void sendNotifications() {
        List<NotificationTask> tasks = repository.findNotifications(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (NotificationTask task : tasks) {
            messageSender.send(task.getChatId(), task.getMessage());
        }
    }
}
