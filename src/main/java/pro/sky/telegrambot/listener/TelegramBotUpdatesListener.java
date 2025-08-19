package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.MessageSender;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final MessageSender messageSender;

    public TelegramBotUpdatesListener(MessageSender messageSender, TelegramBot telegramBot) {
        this.messageSender = messageSender;
        this.telegramBot = telegramBot;
    }

    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            long chatId = update.message().chat().id();
            String message = update.message().text();

            if (message.equals("/start")) {
                messageSender.send(chatId, "test message in bot");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
