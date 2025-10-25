package org.example.ddsapptelegrambot.service.agregador;

import org.example.ddsapptelegrambot.dtos.HechoDTO;
import org.example.ddsapptelegrambot.dtos.PdIDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AgregadorClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://dds-app-agregador.onrender.com";

    public List<HechoDTO> obtenerHechosPorColeccion(String coleccion) {
        try {
            ResponseEntity<List<HechoDTO>> response = restTemplate.exchange(
                    BASE_URL + "/coleccion/" + coleccion + "/hechos",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<HechoDTO>>() {}
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

    public String postearHechoAFuente(String idFuente, String hechoDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(hechoDTO, headers);
            return restTemplate.postForEntity(BASE_URL +"/fuentes/"+ idFuente + "/hecho" ,request, String.class).getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al consultar el PdI: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return null;
        }
    }
}
