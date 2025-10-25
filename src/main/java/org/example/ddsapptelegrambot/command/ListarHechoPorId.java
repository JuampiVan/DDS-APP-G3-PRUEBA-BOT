package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.fuente.FuenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListarHechoPorId implements BotCommand {

    private final BotMessenger messenger;
    private final FuenteService fuenteService;

    @Autowired
    public ListarHechoPorId(BotMessenger messenger, FuenteService fuenteService) {
        this.messenger = messenger;
        this.fuenteService = fuenteService;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/hecho");
    }

    @Override
    public void handle(String message, Long chatId) {
        String[] parts = message.split(" ");


        try {
            String info = fuenteService.obtenerHecho(parts[1]);
            messenger.sendMessage(chatId, "📍 Hecho encontrado: " + info);
        } catch (Exception e) {
            messenger.sendMessage(chatId, "⚠️ Error al obtener el Hecho: " + e.getMessage());
        }
    }
}