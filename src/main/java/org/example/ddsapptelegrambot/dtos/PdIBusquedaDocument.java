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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHecho_id() {
        return hecho_id;
    }

    public void setHecho_id(String hecho_id) {
        this.hecho_id = hecho_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getOcr_resultado() {
        return ocr_resultado;
    }

    public void setOcr_resultado(String ocr_resultado) {
        this.ocr_resultado = ocr_resultado;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}

