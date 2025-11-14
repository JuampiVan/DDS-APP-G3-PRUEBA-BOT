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
    private String hecho_id;
    private String descripcion;
    private String lugar;
    private String momento;
    private String contenido;
    private String url_imagen;
    private String ocr_resultado;
    private List<String> etiquetas;
}

