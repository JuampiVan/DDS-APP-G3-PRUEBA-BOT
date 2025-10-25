package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.agregador.AgregadorService;
import org.example.ddsapptelegrambot.service.fuente.FuenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HechosPorColeccion implements BotCommand {
    private final BotMessenger messenger;
    private final AgregadorService agregadorService;

    @Autowired
    public HechosPorColeccion(BotMessenger messenger, AgregadorService agregadorService) {
        this.messenger = messenger;
        this.agregadorService = agregadorService;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/hechosPorColeccion");
    }

    @Override
    public void handle(String message, Long chatId) {
        String[] parts = message.split(" ");


        try {
            String info = agregadorService.obtenerHechosPorColeccion(parts[1]);
            messenger.sendMessage(chatId, "📍 Hechos encontrados: \n" + info);
        } catch (Exception e) {
            messenger.sendMessage(chatId, "⚠️ Error al obtener los Hechos: " + e.getMessage());
        }
    }
}
