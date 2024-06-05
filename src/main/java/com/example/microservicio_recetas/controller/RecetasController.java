package com.example.microservicio_recetas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.service.ContainerMetadataService;
import com.example.microservicio_recetas.model.dto.RecetaDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("recetas")
public class RecetasController {
    @Autowired
    RecetasRepository recetasRepository;

    @Autowired
	private ContainerMetadataService containerMetadataService;

    @GetMapping("/{idRecetaSolicitada}")
    public @ResponseBody RecetaDto obtenerDetalleReceta(@PathVariable int idRecetaSolicitada) {
        Object[] elemento=recetasRepository.obtenerRecetaPorId(idRecetaSolicitada).get(0);
        Integer idReceta = (Integer)elemento[0];
        String nombreGenericoMedicamentoPrescrito = (String) elemento[1];
        String viaCuidadoEspecialesAdministracion = (String) elemento[2];
        String concentracionDosificacion = (String) elemento[3];
        String frecuenciaAdministracion24hrs = (String) elemento[4];
        String duracionTratamiento = elemento[5]+"";
        Date fechaVencimiento = (Date)elemento[6];
        String precaucionesEspeciales = (String) elemento[7]+"";
        String indicacionesEspeciales = (String) elemento[8]+"";
        Integer idMedico = (Integer) elemento[9];
        Integer idHistoriaClinica = (Integer) elemento[10];
        Date createdAt =(Date)elemento[11];
        Date updatedAt = (Date)elemento[12];
        String ciPropietario = (String) elemento[13];
        String pacientePropietario = (String) elemento[14];
        Date deletedAt = (Date)elemento[15];
        return new RecetaDto(idReceta, nombreGenericoMedicamentoPrescrito, viaCuidadoEspecialesAdministracion,concentracionDosificacion, frecuenciaAdministracion24hrs, duracionTratamiento,fechaVencimiento, precaucionesEspeciales, indicacionesEspeciales, idMedico,idHistoriaClinica, createdAt, updatedAt, ciPropietario, pacientePropietario ,deletedAt);
        // return recetasRepository.findById(idReceta)
        // .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la peticion"));
    }
    @GetMapping()
    public @ResponseBody List<RecetaDto> obtenerTodasRecetas() {
        List<Object[]> infoRecetas=recetasRepository.obtenerRecetas();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return infoRecetas.stream().map((Object[] elemento) -> {
            Integer idReceta = (Integer) elemento[0];
            String nombreGenericoMedicamentoPrescrito = (String) elemento[1];
            String viaCuidadoEspecialesAdministracion = (String) elemento[2];
            String concentracionDosificacion = (String) elemento[3];
            String frecuenciaAdministracion24hrs = (String) elemento[4];
            String duracionTratamiento = elemento[5]+"";
            Date fechaVencimiento = (Date)elemento[6];
            String precaucionesEspeciales = (String) elemento[7]+"";
            String indicacionesEspeciales = (String) elemento[8]+"";
            Integer idMedico = (Integer) elemento[9];
            Integer idHistoriaClinica = (Integer) elemento[10];
            Date createdAt =(Date)elemento[11];
            Date updatedAt = (Date)elemento[12];
            String ciPropietario = (String) elemento[13];
            String pacientePropietario = (String) elemento[14];
            Date deletedAt = (Date)elemento[15];

            return new RecetaDto(idReceta, nombreGenericoMedicamentoPrescrito, viaCuidadoEspecialesAdministracion,concentracionDosificacion, frecuenciaAdministracion24hrs, duracionTratamiento,fechaVencimiento, precaucionesEspeciales, indicacionesEspeciales, idMedico,idHistoriaClinica, createdAt, updatedAt, ciPropietario, pacientePropietario ,deletedAt);
        }).collect(Collectors.toList());
        // return recetasRepository.obtenerRecetas();
    }
    @GetMapping("/paciente/{idPaciente}")
    public @ResponseBody List<RecetaDto> obtenerRecetasPaciente(@PathVariable int idPaciente) {
        List<Object[]> infoRecetas=recetasRepository.obtenerRecetasPaciente(idPaciente);
        return infoRecetas.stream().map((Object[] elemento) -> {
            Integer idReceta = (Integer) elemento[0];
            String nombreGenericoMedicamentoPrescrito = (String) elemento[1];
            String viaCuidadoEspecialesAdministracion = (String) elemento[2];
            String concentracionDosificacion = (String) elemento[3];
            String frecuenciaAdministracion24hrs = (String) elemento[4];
            String duracionTratamiento = elemento[5]+"";
            Date fechaVencimiento = (Date)elemento[6];
            String precaucionesEspeciales = (String) elemento[7]+"";
            String indicacionesEspeciales = (String) elemento[8]+"";
            Integer idMedico = (Integer) elemento[9];
            Integer idHistoriaClinica = (Integer) elemento[10];
            Date createdAt =(Date)elemento[11];
            Date updatedAt = (Date)elemento[12];
            String ciPropietario = (String) elemento[13];
            String pacientePropietario = (String) elemento[14];
            Date deletedAt = (Date)elemento[15];

            return new RecetaDto(idReceta, nombreGenericoMedicamentoPrescrito, viaCuidadoEspecialesAdministracion,concentracionDosificacion, frecuenciaAdministracion24hrs, duracionTratamiento,fechaVencimiento, precaucionesEspeciales, indicacionesEspeciales, idMedico,idHistoriaClinica, createdAt, updatedAt, ciPropietario, pacientePropietario ,deletedAt);
        }).collect(Collectors.toList());        //return recetasRepository.findByIdPaciente(idReceta);
        
    }
    @PostMapping()
    public @ResponseBody RecetasEntity registrarReceta(@RequestBody RecetasEntity nuevo){
        recetasRepository.save(nuevo);
        return nuevo;
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @PutMapping("/{id}")
    public @ResponseBody RecetasEntity actualizarReceta(@PathVariable Integer id, @RequestBody RecetasEntity actualizada) {
        return recetasRepository.findById(id)
                .map(receta -> {
                    receta.setNombreGenericoMedicamentoPreescrito(actualizada.getNombreGenericoMedicamentoPreescrito());
                    receta.setViaCuidadoEspecialesAdministracion(actualizada.getViaCuidadoEspecialesAdministracion());
                    receta.setConcentracionDosificacion(actualizada.getConcentracionDosificacion());
                    receta.setFrecuenciaAdministracion24hrs(actualizada.getFrecuenciaAdministracion24hrs());
                    receta.setDuracionTratamiento(actualizada.getDuracionTratamiento());
                    receta.setFechaVencimiento(actualizada.getFechaVencimiento());
                    receta.setPrecaucionesEspeciales(actualizada.getPrecaucionesEspeciales());
                    receta.setIndicacionesEspeciales(actualizada.getIndicacionesEspeciales());
                    receta.setIdMedico(actualizada.getIdMedico());
                    receta.setIdHistoriaClinica(actualizada.getIdHistoriaClinica());
                    receta.setUpdatedAt(new Date()); 
                    recetasRepository.save(receta);
                    return actualizada;
                })
                .orElseGet(() -> {
                    return actualizada;
                });
    }

}
