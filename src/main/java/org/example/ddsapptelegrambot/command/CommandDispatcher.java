package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandDispatcher {

    private final List<BotCommand> commands;
    private final BotMessenger messenger;

    @Autowired
    public CommandDispatcher(BotMessenger messenger, List<BotCommand> commands) {
        this.messenger = messenger;
        this.commands = commands;
    }

    public void handle(String message, Long chatId) {
        for (BotCommand command : commands) {
            if (command.canHandle(message)) {
                command.handle(message, chatId);
                return;
            }
        }

        // Si no se encuentra comando válido
        messenger.sendMessage(chatId, "🤔 No entendí. Probá con /start o /pdi <id>.");
    }
}
