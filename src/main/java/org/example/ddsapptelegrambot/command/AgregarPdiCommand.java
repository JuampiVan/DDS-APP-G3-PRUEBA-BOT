package org.example.ddsapptelegrambot.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ddsapptelegrambot.app.BotMessenger;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AgregarPdiCommand implements BotCommand {

    private final BotMessenger messenger;
    private final ProcesadorPdIService pdiService;

    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"([^\"]*)\"", Pattern.DOTALL);


    @Autowired
    public AgregarPdiCommand(BotMessenger messenger, ProcesadorPdIService pdiService) {
        this.messenger = messenger;
        this.pdiService = pdiService;
    }

    @Override
    public boolean canHandle(String message) {
        return message.startsWith("/agregarPdi");
    }

    @Override
    public void handle(String message, Long chatId) {
        try {
            System.out.println("DEBUG /agregarPdi recibí: [" + message + "]");

            // extraer todos los valores entre comillas
            Matcher m = QUOTE_PATTERN.matcher(message);
            List<String> parts = new ArrayList<>();
            while (m.find()) {
                parts.add(m.group(1));
            }

            // Si no obtuvimos 6 campos, informamos uso correcto
            if (parts.size() < 6) {
                messenger.sendMessage(chatId,
                        "⚙️ Uso correcto:\n" +
                                "/agregarPdi \"<contenido>\" \"<descripcion>\" \"<hecho_id>\" \"<lugar>\" \"<momento>\" \"<url_imagen>\"\n\n" +
                                "Ejemplo:\n" +
                                "/agregarPdi \"Imagen para test\" \"Buenos Aires\" \"abc123\" \"2025-09-23\" \"PDI de prueba\" \"https://...jpg\"\n\n" +
                                "He detectado " + parts.size() + " campos entre comillas. Asegurate de usar comillas \" normales."
                );
                System.out.println("DEBUG campos detectados: " + parts);
                return;
            }

            // tomamos los seis primeros (si viene más, los ignoramos)
            String contenido = parts.get(0);
            String descripcion = parts.get(1);
            String hechoId = parts.get(2);
            String lugar = parts.get(3);
            String momento = parts.get(4);
            String urlImagen = parts.get(5);

            System.out.println("DEBUG parsed: contenido=" + contenido + ", descripcion=" + descripcion + ", hechoId=" + hechoId + ", lugar=" + lugar + ", momento=" + momento + ", urlImagen=" + urlImagen);

            // construir JSON (escapando valores por si acaso)
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("contenido", contenido);
            map.put("descripcion", descripcion);
            map.put("hecho_id", hechoId);
            map.put("lugar", lugar);
            map.put("momento", momento);
            map.put("url_imagen", urlImagen);

            // convertir a JSON simple (usamos ObjectMapper del service si prefieres)
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(map);

            // llamar al servicio que ya tenés
            String respuesta = pdiService.postearPdi(json);
            messenger.sendMessage(chatId, respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            messenger.sendMessage(chatId, "⚠️ Error al agregar el PDI: " + e.getMessage());
        }
    }
}
