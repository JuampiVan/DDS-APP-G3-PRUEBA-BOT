package org.example.ddsapptelegrambot.dtos;

// Este DTO representa el CUERPO (Body) de la petición PATCH.
// Solo contiene los campos que queremos actualizar.
public record SolicitudUpdateDTO(
    EstadoSolicitudBorradoEnum estado,
    String descripcion
) {}
