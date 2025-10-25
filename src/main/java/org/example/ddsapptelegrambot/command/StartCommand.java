package org.example.ddsapptelegrambot.command;

import org.example.ddsapptelegrambot.app.BotMessenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements BotCommand {

    private final BotMessenger messenger;

    @Autowired
    public StartCommand(BotMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public boolean canHandle(String message) {
        // Este comando responde a /start o /ayuda
        return message.startsWith("/start") || message.startsWith("/ayuda");
    }

    @Override
    public void handle(String message, Long chatId) {
        // --- AQUÍ ESTÁ EL MENÚ ACTUALIZADO ---
        
        // Usamos StringBuilder para construir el mensaje
        StringBuilder sb = new StringBuilder();
        sb.append("👋 ¡Hola! Soy el bot del Grupo 3!\n\n");
        sb.append("Estos son los comandos que entiendo:\n\n");
        
        sb.append("🔍 */pdi <id>*\n");
        sb.append("   Consulta un Punto de Interés (PdI).\n\n");
        
        sb.append("➕ */CrearSolicitud \"<id>\" \"<estado>\" \"<hechoId>\" \"<descripcion>\"*\n");
        sb.append("   Crea una nueva solicitud de borrado.\n");
        sb.append("   _Ej: /CrearSolicitud \"10\" \"CREADA\" \"hecho1\" \"Solicitud de prueba\"_\n\n");

        sb.append("✏️ */actualizar_solicitud \"<id>\" \"<nuevo_estado>\" \"<nueva_descripcion>\"*\n");
        sb.append("   Modifica una solicitud existente.\n");
        sb.append("   _Ej: /actualizar_solicitud \"10\" \"VALIDADA\" \"Descripción actualizada\"_");

        // Enviamos el mensaje formateado
        messenger.sendMessage(chatId, sb.toString());
    }
}
