package org.example.ddsapptelegrambot.app;

public class BusquedaEstado {
    public String texto;
    public String tag;
    public int page;

    public BusquedaEstado(String texto, String tag, int page) {
        this.texto = texto;
        this.tag = tag;
        this.page = page;
    }
}

