package org.example.ddsapptelegrambot.service.agregador;

import org.example.ddsapptelegrambot.dtos.HechoDTO;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgregadorService {

    private final AgregadorClient agregadorClient;

    @Autowired
    public AgregadorService(AgregadorClient agregadorClient) {
        this.agregadorClient = agregadorClient;
    }


    public String obtenerHechosPorColeccion(String coleccion){
        List<HechoDTO> hechos = agregadorClient.obtenerHechosPorColeccion(coleccion);

        if (hechos==null) {
            return "No se pudo obtener los hechos por coleccion";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Hechos de la coleccion ").append(coleccion).append(":\n").append("[\n");
        for (HechoDTO hechoDTO : hechos) {
            sb.append(hechoDTO.toString()).append(",\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
