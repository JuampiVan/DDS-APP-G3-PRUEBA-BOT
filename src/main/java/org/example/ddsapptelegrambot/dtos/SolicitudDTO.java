package org.example.ddsapptelegrambot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty; 
public record SolicitudDTO(
    String id,
    String descripcion, 
    EstadoSolicitudBorradoEnum estado, 
    
    @JsonProperty("hecho_id") 
    String hechoId
) {}
