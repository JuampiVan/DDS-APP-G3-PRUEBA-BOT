package org.example.ddsapptelegrambot.service.procesadorPdi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ddsapptelegrambot.dtos.PdIDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcesadorPdIService {

    private final ProcesadorPdI pdiClient;

    @Autowired
    public ProcesadorPdIService(ProcesadorPdI pdiClient) {
        this.pdiClient = pdiClient;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public String obtenerPdi(Long id) {
        PdIDTO pdi = pdiClient.obtenerPdiPorId(id);

        if (pdi == null) {
            return "No se pudo obtener el PdI con ID " + id;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("*PDI #").append(pdi.getId()).append("*\n")
                .append("*Hecho ID:* ").append(pdi.getHecho_id()).append("\n")
                .append("*Descripción:* ").append(pdi.getDescripcion()).append("\n")
                .append("*Lugar:* ").append(pdi.getLugar()).append("\n")
                .append("*Momento:* ").append(pdi.getMomento()).append("\n")
                .append("*Contenido:* ").append(pdi.getContenido()).append("\n");

        if (pdi.getUrl_imagen() != null && !pdi.getUrl_imagen().isEmpty()) {
            sb.append("\n*Imagen:* ").append(pdi.getUrl_imagen());
        }

        if (pdi.getEtiquetas() != null && !pdi.getEtiquetas().isEmpty()) {
            sb.append("\n*Etiquetas:* ").append(String.join(", ", pdi.getEtiquetas()));
        }

        return sb.toString();
    }

    public String obtenerImagenPdI(Long id) {

        PdIDTO pdi = pdiClient.obtenerPdiPorId(id);

        if (pdi == null) {
            return null;
        }

        String url = pdi.getUrl_imagen();
        if (url == null || url.isEmpty()) {
            return null;
        }

        return url;

    }

    public String obtenerPdiPorHecho(String hechoId) {
        List<PdIDTO> pdis = pdiClient.obtenerPdisPorHecho(hechoId);

        if (pdis == null) {
            return "No se pudo obtener pdis para el Hecho " + hechoId;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("PDIs:").append("\n").append("[");
        for (PdIDTO pdi : pdis) {
            sb.append("{").append("\n")
                    .append("*PDI #").append(pdi.getId()).append("*\n")
                    .append("*Hecho ID:* ").append(pdi.getHecho_id()).append("\n")
                    .append("*Descripción:* ").append(pdi.getDescripcion()).append("\n")
                    .append("*Lugar:* ").append(pdi.getLugar()).append("\n")
                    .append("*Momento:* ").append(pdi.getMomento()).append("\n")
                    .append("*Contenido:* ").append(pdi.getContenido()).append("\n");

            if (pdi.getUrl_imagen() != null && !pdi.getUrl_imagen().isEmpty()) {
                sb.append("\n*Imagen:* ").append(pdi.getUrl_imagen());
            }

            if (pdi.getEtiquetas() != null && !pdi.getEtiquetas().isEmpty()) {
                sb.append("\n*Etiquetas:* ").append(String.join(", ", pdi.getEtiquetas()));
            }
            sb.append("},");
        }
        sb.append("]");
        return sb.toString();
    }

}
