package org.example.ddsapptelegrambot.service.fuente;

import org.example.ddsapptelegrambot.dtos.HechoDTO;
import org.example.ddsapptelegrambot.dtos.PdIDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class FuenteClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://dds-app-fuente.onrender.com/hecho/";

    public HechoDTO obtenerHechoPorId(String id) {
        try {
            return restTemplate.getForObject(BASE_URL + id, HechoDTO.class);
        } catch (HttpClientErrorException e) {
            System.out.println("Error al consultar el PdI: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return null;
        }
    }
}
