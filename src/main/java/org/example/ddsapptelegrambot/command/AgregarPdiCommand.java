package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgregarPdiCommand implements BotCommand {
    private final BotMessenger messenger;
    private final ProcesadorPdIService pdiService;

    @Autowired
    public AgregarPdiCommand(BotMessenger messenger, ProcesadorPdIService pdiService) {
        this.messenger = messenger;
        this.pdiService = pdiService;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/agregarPdi");
    }

    @Override
    public void handle(String message, Long chatId) {
        String[] parts = message.split(" ", 2);

        if (parts.length < 2) {
            messenger.sendMessage(chatId, "⚠️ Debes enviar el JSON después del comando.");
            return;
        }

        String json = parts[1];

        try {
            String info = pdiService.postearPdi(json);
            messenger.sendMessage(chatId, info);
        } catch (Exception e) {
            messenger.sendMessage(chatId, "⚠️ Error al postear el PDI: " + e.getMessage());
        }
    }
}
