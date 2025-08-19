package pro.sky.telegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.service.MessageSender;

@Service
public class MessageSenderImpl implements MessageSender {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot bot;

    public MessageSenderImpl(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void send(Long chatId, String messageText) {
        logger.info("Trying to send message '{}' to chatId {}", messageText, chatId);

        SendMessage sendMessage = new SendMessage(chatId, messageText);
        SendResponse response = bot.execute(sendMessage);

        if (response.isOk()) {
            logger.info("Message successfully sent to chatId {}", chatId);
        } else {
            logger.error("Failed to send message to chatId {}. Error: {}", chatId, response.description());
        }
    }

}
