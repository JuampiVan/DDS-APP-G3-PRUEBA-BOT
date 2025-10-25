package org.example.ddsapptelegrambot.service.solicitud;

import org.example.ddsapptelegrambot.dtos.EstadoSolicitudBorradoEnum;
import org.example.ddsapptelegrambot.dtos.SolicitudDTO;
import org.example.ddsapptelegrambot.dtos.SolicitudUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.net.URLDecoder; 
import java.nio.charset.StandardCharsets; 

@Service
public class ProcesadorSolicitudService {

    @Autowired
    private ProcesadorSolicitud solicitudClient;

    // --- Lógica del POST (la que ya tenías) ---
    public String procesarSolicitud(String id, String descripcion, String hechoId, String estadoStr) {
        EstadoSolicitudBorradoEnum estado = parsearEstado(estadoStr);
        if (estado == null) {
            return "⚠️ Estado inválido. Usá uno de: " + Arrays.toString(EstadoSolicitudBorradoEnum.values());
        }

        SolicitudDTO dto = new SolicitudDTO(id, descripcion, estado, hechoId);
        SolicitudDTO creada = solicitudClient.crearSolicitud(dto);

        if (creada == null) {
            return "❌ No se pudo crear la solicitud (POST).";
        }
        return formatarRespuesta(creada, "creada con éxito");
    }

    public String actualizarSolicitud(String id, String estadoStr, String descripcion) {
        EstadoSolicitudBorradoEnum estado = parsearEstado(estadoStr);
        if (estado == null) {
            return "⚠️ Estado inválido. Usá uno de: " + Arrays.toString(EstadoSolicitudBorradoEnum.values());
        }

        SolicitudUpdateDTO updateDto = new SolicitudUpdateDTO(estado, descripcion);
        SolicitudDTO actualizada = solicitudClient.actualizarSolicitud(id, updateDto);

        if (actualizada == null) {
            return "❌ No se pudo actualizar la solicitud (ID: " + id + "). Verifica el ID.";
        }
        return formatarRespuesta(actualizada, "actualizada con éxito");
    }


    private EstadoSolicitudBorradoEnum parsearEstado(String estadoStr) {
        try {
            return EstadoSolicitudBorradoEnum.valueOf(estadoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String formatarRespuesta(SolicitudDTO dto, String accion) {
        
        String descripcionLimpia = dto.descripcion(); 
        try {
           
            descripcionLimpia = URLDecoder.decode(dto.descripcion(), StandardCharsets.UTF_8);
        } catch (Exception e) {
         
            System.err.println("Error al decodificar la descripción: " + e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("✅ *¡Solicitud ").append(accion).append("!*\n")
          .append("*ID:* ").append(dto.id()).append("\n")
          .append("*Descripción:* ").append(descripcionLimpia).append("\n") 
          .append("*Estado:* ").append(dto.estado()).append("\n")
          .append("*Hecho ID:* ").append(dto.hechoId());
        return sb.toString();
    }
}

