package org.example.ddsapptelegrambot.service.procesadorPdi;

import org.example.ddsapptelegrambot.dtos.PdIDTO;
import org.example.ddsapptelegrambot.dtos.PdIBusquedaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class ProcesadorPdI {

    @Autowired
    RestTemplate restTemplate;

    //private final RestTemplate restTemplate = new RestTemplate();
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

    public PdIBusquedaResponse buscarPdi(String texto, String tag, int page) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(BASE_URL + "/buscar")
                    .queryParam("texto", texto)
                    .queryParam("page", page)
                    .queryParam("size", 5);

            if (tag != null && !tag.isBlank()) {
                builder.queryParam("tag", tag);
            }

            System.out.println("PARSER TEXTO: " + texto);
            System.out.println("PARSER TAG: " + tag);

            String url = builder.toUriString();
            System.out.println("Consultando Procesador: " + url);

            ResponseEntity<PdIBusquedaResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PdIBusquedaResponse>() {}
            );
            System.out.println("LOG BODY:  " + response.getBody());
            return response.getBody();

        } catch (HttpClientErrorException e) {
            System.out.println("Error en búsqueda PDI: " + e.getStatusCode());
            return null;

        } catch (Exception e) {
            System.out.println("Error inesperado en búsqueda PDI: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


}
