package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuscarPdiCommand implements BotCommand {
    private final BotMessenger messenger;
    private final ProcesadorPdIService pdiService;

    @Autowired
    public BuscarPdiCommand(BotMessenger messenger, ProcesadorPdIService pdiService) {
        this.messenger = messenger;
        this.pdiService = pdiService;
    }

    @Override
    public boolean canHandle(String message) {
        // Ejemplo de uso:
        // /buscarPdi incendio
        // /buscarPdi incendio tag:CABA
        return message.startsWith("/buscarPdi");
    }

    @Override
    public void handle(String message, Long chatId) {
        try {
            // Divide el mensaje en partes
            String[] parts = message.split(" ", 2);
            if (parts.length < 2) {
                messenger.sendMessage(chatId, "⚠️ Debes ingresar una palabra clave. Ej: `/buscarPdi incendio`");
                return;
            }

            // Extraer palabra y tag (si existe)
            String[] args = parts[1].split("tag:", 2);
            String texto = args[0].trim();
            System.out.println(texto);
            String tag = args.length > 1 ? args[1].trim() : null;
            System.out.println(tag);

            // Llamar al servicio de búsqueda
            String resultados = pdiService.buscarPdi(texto, tag);

            if (resultados == null || resultados.isBlank()) {
                messenger.sendMessage(chatId, "No se encontraron PDIs para la búsqueda.");
            } else {
                messenger.sendMessage(chatId, "*Resultados de la búsqueda:*\n\n" + resultados);
            }
        } catch (Exception e) {
            messenger.sendMessage(chatId, "Error al realizar la búsqueda: " + e.getMessage());
        }
    }
}

