package com.example.microservicio_recetas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.service.ContainerMetadataService;


import java.util.Date;
import java.util.List;

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

    @GetMapping("/{idReceta}")
    public @ResponseBody RecetasEntity obtenerDetalleReceta(@PathVariable int idReceta) {
        return recetasRepository.findById(idReceta)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la peticion"));
    }
    @GetMapping()
    public @ResponseBody List<RecetasEntity> obtenerTodasRecetas() {
        return recetasRepository.findAll();
    }
    @GetMapping("/paciente/{idPaciente}")
    public @ResponseBody List<RecetasEntity> obtenerRecetasPaciente(@PathVariable int idReceta) {
        //return recetasRepository.findByIdPaciente(idReceta);
        return null;
    }
    @PostMapping()
    public @ResponseBody String registrarReceta(@RequestBody RecetasEntity nuevo){
        recetasRepository.save(nuevo);
        return "Ok";
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @PutMapping("/{id}")
    public @ResponseBody String actualizarReceta(@PathVariable Integer id, @RequestBody RecetasEntity actualizada) {
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
                    return "Historia clínica actualizada con éxito";
                })
                .orElseGet(() -> {
                    return "Error en la actualizacion";
                });
    }

}
