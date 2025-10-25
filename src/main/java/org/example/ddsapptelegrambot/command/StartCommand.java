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
        sb.append("   Consulta un Punto de Interés (PdI).\n");
        sb.append("   _Ej: /pdi 1\n\n");
        
        sb.append("➕ */CrearSolicitud \"<id>\" \"<estado>\" \"<hechoId>\" \"<descripcion>\"*\n");
        sb.append("   Crea una nueva solicitud de borrado.\n");
        sb.append("   _Ej: /CrearSolicitud \"10\" \"CREADA\" \"hecho1\" \"Solicitud de prueba\"_\n\n");

        sb.append("✏️ */actualizar_solicitud \"<id>\" \"<nuevo_estado>\" \"<nueva_descripcion>\"*\n");
        sb.append("   Modifica una solicitud existente.\n");
        sb.append("   _Ej: /actualizar_solicitud \"10\" \"VALIDADA\" \"Descripción actualizada\"_\n\n");

        sb.append("➕ */agregarPdi json con el pdi\n");
        sb.append("   Crea una nuevo PDI.\n");
        sb.append("   _Ej: /agregarPdi {\n \"contenido\": \"Imagen para test\",\n\"descripcion\": \"Buenos Aires\",\n\"hecho_id\": \"hecho1\",\n" +
                "\"lugar\": \"2025-09-23\",\n" +
                "\"momento\": \"PDI de prueba desde fuente en render\",\n" +
                "\"url_imagen\": \"\"}\n\n");

        sb.append("➕ */postearHechoAFuente ID de la fuente + json con el hecho\n");
        sb.append("   Crea un nuevo Hecho en la fuente solicitada.\n");
        sb.append("   _Ej: /postearHechoAFuente 17aff72f-7cc2-49e6-b7d4-2d659f6f923d {\n" +
                "    \"id\":\"hecho30\",\n" +
                "    \"nombre_coleccion\":\"coleccion2\",\n" +
                "    \"titulo\":\"hecho30\"\n" +
                "}\n\n");

        sb.append("🔍 */hecho <id>*\n");
        sb.append("   Consulta un Hecho en particular.\n");
        sb.append("   _Ej: /hecho hecho1\n\n");

        sb.append("🔍 */hechosPorColeccion <idColeccion>*\n");
        sb.append("   Consulta los hechos pertenecientes a una coleccion.\n");
        sb.append("   _Ej: /hechosPorColeccion coleccion1\n\n");

        // Enviamos el mensaje formateado
        messenger.sendMessage(chatId, sb.toString());
    }
}
