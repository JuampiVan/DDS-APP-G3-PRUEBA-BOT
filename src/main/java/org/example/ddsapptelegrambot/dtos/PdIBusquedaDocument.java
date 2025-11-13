package org.example.ddsapptelegrambot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdIBusquedaDocument {
    private String id;
    private String hechoId;
    private String descripcion;
    private String lugar;
    private String momento;
    private String contenido;
    private String urlImagen;
    private String ocrResultado;
    private List<String> etiquetas;
}

