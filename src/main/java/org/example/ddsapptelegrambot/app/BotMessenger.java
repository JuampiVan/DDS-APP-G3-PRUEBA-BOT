package org.example.ddsapptelegrambot.app;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface BotMessenger {
    void sendMessage(Long chatId, String text);
    void sendMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboard);
}

