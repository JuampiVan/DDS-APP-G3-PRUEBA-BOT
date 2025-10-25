package org.example.ddsapptelegrambot.app;

public interface BotMessenger {
    void sendMessage(Long chatId, String text);
}
