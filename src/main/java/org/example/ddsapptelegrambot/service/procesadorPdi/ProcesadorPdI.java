package org.example.ddsapptelegrambot.service.procesadorPdi;

import org.example.ddsapptelegrambot.dtos.PdIBusquedaDocument;
import org.example.ddsapptelegrambot.dtos.PdIDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ProcesadorPdI {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://dds-app-procesador.onrender.com/pdis";

    public PdIDTO obtenerPdiPorId(Long id) {
        try {
            return restTemplate.getForObject(BASE_URL +"/"+ id, PdIDTO.class);
        } catch (HttpClientErrorException e) {
            System.out.println("Error al consultar el PdI: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return null;
        }
    }

    public List<PdIDTO> obtenerPdisPorHecho(String hechoId) {
        try {
            ResponseEntity<List<PdIDTO>> response = restTemplate.exchange(
                    BASE_URL + "?hecho=" + hechoId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PdIDTO>>() {}
            );
            return response.getBody();
        }catch (HttpClientErrorException e) {
            System.out.println("Error al consultar el Hecho: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return null;
        }
    }

    public PdIDTO postearPdi(PdIDTO pdi) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PdIDTO> request = new HttpEntity<>(pdi, headers);
            return restTemplate.postForEntity(BASE_URL ,request, PdIDTO.class).getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al consultar el PdI: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return null;
        }
    }

    public List<PdIBusquedaDocument> buscarPdi(String texto, String tag) {
        try {
            String url = BASE_URL + "/buscar?texto=" + URLEncoder.encode(texto, StandardCharsets.UTF_8);
            if (tag != null && !tag.isBlank()) {
                url += "&tag=" + URLEncoder.encode(tag, StandardCharsets.UTF_8);
            }

            System.out.println(url);

            ResponseEntity<List<PdIBusquedaDocument>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PdIBusquedaDocument>>() {}
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error en la búsqueda de PDIs: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado en búsqueda: " + e.getMessage());
            return null;
        }
    }

}
