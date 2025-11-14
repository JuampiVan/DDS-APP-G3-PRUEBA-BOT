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
            // 1. Quitar el comando
            String[] raw = message.split(" ", 2);
            if (raw.length < 2) {
                messenger.sendMessage(chatId, "⚠️ Debes ingresar una palabra clave. Ej: `/buscarPdi incendio`");
                return;
            }

            String resto = raw[1].trim(); // todo lo que viene después de /buscarPdi

            // 2. Buscar tag: si existe
            String texto;
            String tag = null;

            int indexTag = resto.indexOf("tag:");

            if (indexTag == -1) {
                // No hay tag: → todo es texto
                texto = resto.trim();
            } else {
                // Hay tag:
                texto = resto.substring(0, indexTag).trim();
                tag = resto.substring(indexTag + 4).trim(); // después de "tag:"
            }

            // 3. Validaciones
            if (texto.isEmpty()) {
                messenger.sendMessage(chatId,
                        "⚠️ Formato incorrecto.\n\nDebes usar:\n`/buscarPdi <texto> tag:<tag>`");
                return;
            }

            if (texto.length() < 3) {
                messenger.sendMessage(chatId, "⚠️ El texto debe tener al menos *3 caracteres*.");
                return;
            }

            if (tag != null && !tag.isEmpty() && tag.length() < 3) {
                messenger.sendMessage(chatId, "⚠️ El tag debe tener al menos *3 caracteres*.");
                return;
            }

            // 4. Ejecutar búsqueda
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

