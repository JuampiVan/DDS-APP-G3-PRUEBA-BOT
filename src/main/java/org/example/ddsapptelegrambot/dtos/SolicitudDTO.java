package org.example.ddsapptelegrambot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty; // <-- 1. Importar la anotación

public record SolicitudDTO(
    String id,
    String descripcion, 
    EstadoSolicitudBorradoEnum estado, 
    
    @JsonProperty("hecho_id") // <-- 2. Anotar el campo
    String hechoId
) {}
