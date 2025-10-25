package org.example.ddsapptelegrambot.service.fuente;

import org.example.ddsapptelegrambot.dtos.HechoDTO;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdI;
import org.example.ddsapptelegrambot.service.procesadorPdi.ProcesadorPdIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuenteService {
    private final FuenteClient fuenteClient;
    private final ProcesadorPdIService procesadorPdi;

    @Autowired
    public FuenteService(FuenteClient fuenteClient, ProcesadorPdIService procesadorPdi) {
        this.fuenteClient = fuenteClient;
        this.procesadorPdi = procesadorPdi;
    }

    public String obtenerHecho(String id){
        HechoDTO hechoDTO = fuenteClient.obtenerHechoPorId(id);

        if (hechoDTO==null){
            return "No se pudo obtener el Hecho con ID " + id;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(hechoDTO.toString()).append("\n")
                .append(procesadorPdi.obtenerPdiPorHecho(hechoDTO.getId()));

        return sb.toString();
    }

}
