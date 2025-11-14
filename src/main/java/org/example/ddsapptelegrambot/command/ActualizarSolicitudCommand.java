package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.solicitud.ProcesadorSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ActualizarSolicitudCommand implements BotCommand {

    private final ProcesadorSolicitudService procesadorSolicitudService;
    private final BotMessenger messenger;

    // Regex para el nuevo comando. Ejemplo: /actualizar_solicitud "8" "ACEPTADA" "Revisado ok"
    private static final Pattern pattern = Pattern.compile(
            "/actualizar_solicitud\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+\"([^\"]+)\""
    );
    
    @Autowired
    public ActualizarSolicitudCommand(ProcesadorSolicitudService procesadorSolicitudService, BotMessenger messenger) {
        this.procesadorSolicitudService = procesadorSolicitudService;
        this.messenger = messenger;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/actualizar_solicitud");
    }

    @Override
    public void handle(String message, Long chatId) {
        Matcher matcher = pattern.matcher(message);
        String respuesta;

        if (!matcher.find()) {
            respuesta = "⚙️ Uso correcto: /actualizar_solicitud \"<id_solicitud>\" \"<nuevo_estado>\" \"<descripcion>\"";
        } else {
            // Extraemos los datos
            String id = matcher.group(1);
            String estado = matcher.group(2).toUpperCase();
            String descripcion = matcher.group(3);

            // Llamamos al nuevo metodo del servicio
            respuesta = procesadorSolicitudService.actualizarSolicitud(id, estado, descripcion);
        }

        messenger.sendMessage(chatId, respuesta);
    }
}
