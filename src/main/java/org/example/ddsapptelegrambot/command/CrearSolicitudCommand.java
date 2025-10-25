package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger; 
import org.example.ddsapptelegrambot.service.solicitud.ProcesadorSolicitudService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CrearSolicitudCommand implements BotCommand { // Implementamos la interfaz correcta

    private final ProcesadorSolicitudService procesadorSolicitudService;
    private final BotMessenger messenger; // Inyectamos el Messenger para responder
    
    // Tu Regex
    private static final Pattern pattern = Pattern.compile(
            "/CrearSolicitud\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+\"([^\"]+)\""
    );
    
    @Autowired
    public CrearSolicitudCommand(ProcesadorSolicitudService procesadorSolicitudService, BotMessenger messenger) {
        this.procesadorSolicitudService = procesadorSolicitudService;
        this.messenger = messenger;
    }

    @Override
    public boolean canHandle(String message) { // Método de BotCommand
        return message.startsWith("/CrearSolicitud");
    }

    @Override
    public void handle(String message, Long chatId) { // Método de BotCommand
        Matcher matcher = pattern.matcher(message);
        String respuesta;

        if (!matcher.find()) {
            respuesta = "⚙️ Uso correcto: /CrearSolicitud \"<id>\" \"<estado>\" \"<hechoId>\" \"<descripcion>\"";
        } else {
            // Extraemos los datos según el orden de tu Regex
            String id = matcher.group(1);
            String estado = matcher.group(2).toUpperCase();
            String hechoId = matcher.group(3);
            String descripcion = matcher.group(4);

            // Llamamos a TU servicio (el que acabas de pasarme)
            // Pasamos los parámetros en el orden que tu servicio espera: (id, descripcion, hechoId, estado)
            respuesta = procesadorSolicitudService.procesarSolicitud(id, descripcion, hechoId, estado);
        }

        // El comando usa el Messenger para enviar la respuesta
        messenger.sendMessage(chatId, respuesta);
    }
}