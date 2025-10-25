package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements BotCommand {

    private final BotMessenger messenger;

    @Autowired
    public StartCommand(BotMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/start");
    }

    @Override
    public void handle(String message, Long chatId) {
        String response = "👋 ¡Hola! Soy el bot del Grupo 3. Probá el comando /pdi <id> para consultar un PdI.";
            messenger.sendMessage(chatId, response);
    }
}
