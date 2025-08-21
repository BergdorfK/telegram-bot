package pro.sky.telegrambot.service;

public interface MessageSender {
    void send(Long chatId, String messageText);
}
