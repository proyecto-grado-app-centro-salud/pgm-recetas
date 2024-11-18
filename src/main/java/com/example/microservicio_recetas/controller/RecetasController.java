package com.example.microservicio_recetas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.services.ContainerMetadataService;
import com.example.microservicio_recetas.services.RecetasService;
import com.example.microservicio_recetas.model.dto.RecetaDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/recetas")
public class RecetasController {
    @Autowired
    private RecetasService recetasService;
    @Autowired
	private ContainerMetadataService containerMetadataService;

    @PostMapping
    public ResponseEntity<RecetaDto> registrarReceta(@RequestBody RecetaDto recetaDto) {
        try {
            RecetaDto nuevaReceta = recetasService.registrarReceta(recetaDto);
            return new ResponseEntity<>(nuevaReceta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<RecetaDto>> obtenerTodasRecetas() {
        try {
            List<RecetaDto> recetas = recetasService.obtenerTodasRecetas();
            return new ResponseEntity<>(recetas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDto> obtenerRecetaPorId(@PathVariable Integer id) {
        try {
            RecetaDto receta = recetasService.obtenerRecetaPorId(id);
            return new ResponseEntity<>(receta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDto> actualizarReceta(@PathVariable Integer id, @RequestBody RecetaDto recetaDto) {
        try {
            RecetaDto recetaActualizada = recetasService.actualizarReceta(id, recetaDto);
            return new ResponseEntity<>(recetaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

 
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<RecetaDto>> obtenerRecetasPaciente(@PathVariable int idPaciente) {
        try {
            List<RecetaDto> recetas = recetasService.obtenerTodasRecetasDePaciente(idPaciente);
            return new ResponseEntity<>(recetas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
   

}
