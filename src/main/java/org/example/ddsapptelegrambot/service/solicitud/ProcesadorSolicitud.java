package org.example.ddsapptelegrambot.service.solicitud;

import org.example.ddsapptelegrambot.dtos.SolicitudDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException; // <-- Importamos la clase padre
import org.springframework.web.client.RestTemplate;

@Component
public class ProcesadorSolicitud {

    private static final Logger log = LoggerFactory.getLogger(ProcesadorSolicitud.class);
    private final RestTemplate restTemplate;

    @Autowired
    public ProcesadorSolicitud(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO dto) {
        String url = "https://dds-app-solicitud.onrender.com/api/solicitudes";

        try {
            return restTemplate.postForObject(url, dto, SolicitudDTO.class);
        
        } catch (HttpStatusCodeException e) { // <-- ESTE CATCH CAPTURA 4xx y 5xx
            // Ahora sí, el log saldrá correctamente:
            log.error("Error HTTP desde la API: {} - Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null; 
        
        } catch (Exception e) {
            log.error("Error inesperado (no-http, ej: red) al crear solicitud", e);
            return null;
        }
    }
}

