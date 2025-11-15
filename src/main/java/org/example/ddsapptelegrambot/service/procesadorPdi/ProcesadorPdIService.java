package org.example.ddsapptelegrambot.service.procesadorPdi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ddsapptelegrambot.dtos.PdIBusquedaDocument;
import org.example.ddsapptelegrambot.dtos.PdIBusquedaResponse;
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

    public String postearPdi(String pdi){
        ObjectMapper mapper = new ObjectMapper();
        PdIDTO pdIDTO = null;
        try {
            pdIDTO = mapper.readValue(pdi, PdIDTO.class);
        } catch (JsonProcessingException e) {
            return "Error al parsear datos";
        }
        if (pdi == null) {
            return "No se pudo postear el Pdi enviado";
        }

        PdIDTO pdidto1 = pdiClient.postearPdi(pdIDTO);

        if (pdidto1 == null) {
            return "No se pudo postear el Pdi enviado";
        }

        return "Se posteo correctamente el Pdi enviado";
    }

    public PdIBusquedaResponse buscarPdiRaw(String texto, String tag, int page) {
        System.out.println("buscarPdiRaw LO USO");
        return pdiClient.buscarPdi(texto, tag, page);
    }



    public String formatearResultados(PdIBusquedaResponse response) {
        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return "No se encontraron PDIs para la búsqueda.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("🔎 *Resultados de la búsqueda*\n\n");

        for (PdIBusquedaDocument doc : response.getItems()) {
            sb.append("🆔 *Hecho:* ").append(doc.getHecho_id()).append("\n")
                    .append("📝 *Descripción:* ").append(doc.getDescripcion()).append("\n")
                    .append("📍 *Lugar:* ").append(doc.getLugar()).append("\n");

            if (doc.getOcr_resultado() != null && !doc.getOcr_resultado().isEmpty()) {
                sb.append("🔠 *OCR:* ").append(doc.getOcr_resultado()).append("\n");
            }

            if (doc.getEtiquetas() != null && !doc.getEtiquetas().isEmpty()) {
                sb.append("🏷️ *Tags:* ").append(String.join(", ", doc.getEtiquetas())).append("\n");
            }

            sb.append("\n------------------------------\n");
        }

        sb.append("\n📄 *Página ")
                .append(response.getCurrentPage() + 1)
                .append(" de ")
                .append(response.getTotalPages())
                .append("*\n");

        sb.append("📊 *Total ítems encontrados:* ")
                .append(response.getTotalItems())
                .append("\n");

        return sb.toString();
    }


}
