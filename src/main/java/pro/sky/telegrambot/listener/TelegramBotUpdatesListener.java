package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageSender;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final MessageSender messageSender;
    private TelegramBot telegramBot;
    private NotificationTaskRepository notificationTaskRepository;
    private static final Pattern NOTIFICATION_PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");

    public TelegramBotUpdatesListener(MessageSender messageSender, TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.messageSender = messageSender;
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message() != null && update.message().text() != null) {
                long chatId = update.message().chat().id();
                String message = update.message().text();

                if (message.equals("/start")) {
                    messageSender.send(chatId, "test message in bot");
                }

                Matcher matcher = NOTIFICATION_PATTERN.matcher(message);
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String notificationMessage = matcher.group(3);
                    LocalDateTime notificationDate = LocalDateTime.parse(
                            date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    );

                    notificationTaskRepository.save(
                            new NotificationTask(chatId, notificationMessage, notificationDate)
                    );

                    messageSender.send(chatId, "SUCCESSFULLY saved to DB");
                }
            } else {
                logger.warn("Skipping update: message or text is null. Update: {}", update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
