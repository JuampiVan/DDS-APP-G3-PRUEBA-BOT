package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.agregador.AgregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostearHechoAFuente implements BotCommand{
    private final BotMessenger messenger;
    private final AgregadorService agregadorService;

    @Autowired
    public PostearHechoAFuente(BotMessenger messenger, AgregadorService agregadorService) {
        this.messenger = messenger;
        this.agregadorService = agregadorService;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/postearHechoAFuente");
    }

    @Override
    public void handle(String message, Long chatId) {
        String[] parts = message.split(" ", 3);


        try {
            String id = parts[1];
            String hecho = parts[2];
            String info = agregadorService.postearHechoAFuente(id, hecho);
            messenger.sendMessage(chatId, info);
        } catch (Exception e) {
            messenger.sendMessage(chatId, "⚠️ Error al obtener los Hechos: " + e.getMessage());
        }
    }
}
