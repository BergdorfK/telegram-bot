package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String message;
    private LocalDateTime notificationDataTime;

    public NotificationTask(){
    }

    public NotificationTask(Long chatId, String message, LocalDateTime notificationDataTime) {
        this.chatId = chatId;
        this.message = message;
        this.notificationDataTime = notificationDataTime;
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getNotificationDataTime() {
        return notificationDataTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(message, that.message) && Objects.equals(notificationDataTime, that.notificationDataTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, message, notificationDataTime);
    }
}
