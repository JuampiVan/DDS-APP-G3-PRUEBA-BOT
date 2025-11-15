package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.app.BusquedaEstado;
import org.example.ddsapptelegrambot.dtos.PdIBusquedaResponse;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BuscarPdiCommand implements BotCommand {
    private final BotMessenger messenger;
    private final ProcesadorPdIService pdiService;
    private final Map<Long, BusquedaEstado> estados = new HashMap<>();

    @Autowired
    public BuscarPdiCommand(BotMessenger messenger, ProcesadorPdIService pdiService) {
        this.messenger = messenger;
        this.pdiService = pdiService;
        System.out.println("🔥 ProcesadorPdIService INYECTADO: " + pdiService.getClass());
    }

    @Override
    public boolean canHandle(String message) {
        // Ejemplo de uso:
        // /buscarPdi incendio
        // /buscarPdi incendio tag:CABA
        return message.startsWith("/buscarPdi");
    }

//    @Override
//    public void handle(String message, Long chatId) {
//        try {
//            // 1. Quitar el comando
//            String[] raw = message.split(" ", 2);
//            if (raw.length < 2) {
//                messenger.sendMessage(chatId, "⚠️ Debes ingresar una palabra clave. Ej: `/buscarPdi incendio`");
//                return;
//            }
//
//            String resto = raw[1].trim(); // todo lo que viene después de /buscarPdi
//
//            // 2. Buscar tag: si existe
//            String texto;
//            String tag = null;
//
//            int indexTag = resto.indexOf("tag:");
//
//            if (indexTag == -1) {
//                // No hay tag: → todo es texto
//                texto = resto.trim();
//            } else {
//                // Hay tag:
//                texto = resto.substring(0, indexTag).trim();
//                tag = resto.substring(indexTag + 4).trim(); // después de "tag:"
//            }
//
//            // 3. Validaciones
//            if (texto.isEmpty()) {
//                messenger.sendMessage(chatId,
//                        "⚠️ Formato incorrecto.\n\nDebes usar:\n`/buscarPdi <texto> tag:<tag>`");
//                return;
//            }
//
//            if (texto.length() < 3) {
//                messenger.sendMessage(chatId, "⚠️ El texto debe tener al menos *3 caracteres*.");
//                return;
//            }
//
//            if (tag != null && !tag.isEmpty() && tag.length() < 3) {
//                messenger.sendMessage(chatId, "⚠️ El tag debe tener al menos *3 caracteres*.");
//                return;
//            }
//
//            // 4. Ejecutar búsqueda
//            String resultados = pdiService.buscarPdi(texto, tag);
//
//            if (resultados == null || resultados.isBlank()) {
//                messenger.sendMessage(chatId, "No se encontraron PDIs para la búsqueda.");
//            } else {
//                messenger.sendMessage(chatId, "*Resultados de la búsqueda:*\n\n" + resultados);
//            }
//
//        } catch (Exception e) {
//            messenger.sendMessage(chatId, "Error al realizar la búsqueda: " + e.getMessage());
//        }
//    }

    @Override
    public void handle(String message, Long chatId) {
        try {
            // 1. Quitar el comando
            String[] raw = message.split(" ", 2);
            if (raw.length < 2) {
                messenger.sendMessage(chatId,
                        "⚠️ Debes ingresar una palabra clave. Ej: `/buscarPdi incendio`");
                return;
            }

            String resto = raw[1].trim();

            // 2. Obtener texto y tag
            String texto;
            String tag = null;
            int indexTag = resto.indexOf("tag:");

            if (indexTag == -1) {
                texto = resto;
            } else {
                texto = resto.substring(0, indexTag).trim();
                tag = resto.substring(indexTag + 4).trim();
                System.out.println("PRUEBA: " + texto + " " + tag);
            }

            // Validaciones
            if (texto.isEmpty()) {
                messenger.sendMessage(chatId,
                        "⚠️ Formato incorrecto.\n\nDebes usar:\n`/buscarPdi <texto> tag:<tag>`");
                return;
            }

            if (texto.length() < 3) {
                messenger.sendMessage(chatId,
                        "⚠️ El texto debe tener al menos *3 caracteres*.");
                return;
            }

            if (indexTag != -1 && (tag == null || tag.isEmpty())) {
                messenger.sendMessage(chatId,
                        "⚠️ Debes ingresar un tag luego de `tag:`.\nEj: `/buscarPdi incendio tag:Incendio`");
                return;
            }

            if (tag != null && tag.length() < 3) {
                messenger.sendMessage(chatId,
                        "⚠️ El tag debe tener al menos *3 caracteres*.");
                return;
            }

            // PAGINACIÓN -> arrancamos en página 0
            int page = 0;

            // 🔥 AHORA sí pedimos el OBJETO
            PdIBusquedaResponse res = pdiService.buscarPdiRaw(texto, tag, page);
            System.out.println("PdIBusquedaResponse: " + res);
            System.out.println("ITEMS: " + res.getItems());

            if (res == null || res.getItems() == null || res.getItems().isEmpty()) {
                messenger.sendMessage(chatId, "No se encontraron PDIs para la búsqueda.");
                return;
            }

            // guardamos estado
            estados.put(chatId, new BusquedaEstado(texto, tag, page));

            // 🔥 Formateamos texto
            String msg = pdiService.formatearResultados(res);
            System.out.println(msg);

            // 🔥 Enviamos con teclado
            messenger.sendMessageWithKeyboard(chatId, msg, buildPaginationKeyboard(res));

        } catch (Exception e) {
            messenger.sendMessage(chatId,
                    "Error al realizar la búsqueda: " + e.getMessage());
        }
    }


    private InlineKeyboardMarkup buildPaginationKeyboard(PdIBusquedaResponse res) {

        int current = res.getCurrentPage();
        int last = res.getTotalPages() - 1;

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();

        if (current > 0) {
            InlineKeyboardButton prev = new InlineKeyboardButton();
            prev.setText("⬅️ Anterior");
            prev.setCallbackData("pdi_prev");
            row.add(prev);
        }

        if (current < last) {
            InlineKeyboardButton next = new InlineKeyboardButton();
            next.setText("Siguiente ➡️");
            next.setCallbackData("pdi_next");
            row.add(next);
        }

        if (!row.isEmpty()) {
            rows.add(row);
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }



}

