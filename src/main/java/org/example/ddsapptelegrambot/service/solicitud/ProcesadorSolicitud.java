package org.example.ddsapptelegrambot.service.solicitud;

import org.example.ddsapptelegrambot.dtos.SolicitudDTO;
import org.example.ddsapptelegrambot.dtos.SolicitudUpdateDTO; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity; 
import org.springframework.http.HttpHeaders; 
import org.springframework.http.HttpMethod; 
import org.springframework.http.MediaType; // <-- Importante
import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder; // <-- Importante para query params

@Component
public class ProcesadorSolicitud {

    private static final Logger log = LoggerFactory.getLogger(ProcesadorSolicitud.class);
    private final RestTemplate restTemplate;
    private final String solicitudApiUrl = "https://dds-app-solicitud.onrender.com/api/solicitudes";

    @Autowired
    public ProcesadorSolicitud(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // --- Tu método POST (funcional) ---
    public SolicitudDTO crearSolicitud(SolicitudDTO dto) {
        try {
            return restTemplate.postForObject(solicitudApiUrl, dto, SolicitudDTO.class);
        } catch (HttpStatusCodeException e) { 
            log.error("Error HTTP al CREAR (POST): {} - Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null; 
        } catch (Exception e) {
            log.error("Error inesperado (no-http) al CREAR solicitud", e);
            return null;
        }
    }

    // --- El método PATCH (Corregido para tu API) ---
    public SolicitudDTO actualizarSolicitud(String id, SolicitudUpdateDTO updateDTO) {
        
        // 1. Construimos la URL con los Query Params (estado y descripcion)
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(solicitudApiUrl)
                .queryParam("estado", updateDTO.estado())
                .queryParam("descripcion", updateDTO.descripcion());

        // 2. Preparamos los Headers. El body es TEXTO PLANO.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN); // <-- No es JSON
        
        // 3. Creamos el body: El ID como un String (que es lo que espera @RequestBody String)
        HttpEntity<String> requestEntity = new HttpEntity<>(id, headers);

        try {
            // Usamos .exchange() para poder especificar el PATCH
            ResponseEntity<SolicitudDTO> response = restTemplate.exchange(
                builder.toUriString(), // La URL con los ?estado=...&descripcion=...
                HttpMethod.PATCH,
                requestEntity,      // El body (el ID "8")
                SolicitudDTO.class
            );
            return response.getBody();

        } catch (HttpStatusCodeException e) {
            log.error("Error HTTP al ACTUALIZAR (PATCH): {} - Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null; 
        
        } catch (Exception e) {
            log.error("Error inesperado (no-http) al ACTUALIZAR solicitud", e);
            return null;
        }
    }
}

