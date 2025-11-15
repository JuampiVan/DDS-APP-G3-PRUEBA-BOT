package org.example.ddsapptelegrambot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PdIBusquedaResponse {

    @JsonProperty("resultados")
    private List<PdIBusquedaDocument> items;

    @JsonProperty("page")
    private int currentPage;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private long totalItems;

    public List<PdIBusquedaDocument> getItems() {
        return items;
    }

    public void setItems(List<PdIBusquedaDocument> items) {
        this.items = items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}