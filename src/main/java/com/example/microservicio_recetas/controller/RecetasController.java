package com.example.microservicio_recetas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.services.ContainerMetadataService;
import com.example.microservicio_recetas.services.RecetasService;
import com.example.microservicio_recetas.model.dto.RecetaDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
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
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<RecetaDto>> obtenerTodasRecetas(@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String ciPaciente,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<RecetaDto> recetas = recetasService.obtenerTodasRecetas(fechaInicio,fechaFin,ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(recetas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDto> obtenerRecetaPorId(@PathVariable Integer id) {
        try {
            RecetaDto receta = recetasService.obtenerRecetaPorId(id);
            return new ResponseEntity<>(receta, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDto> actualizarReceta(@PathVariable Integer id, @RequestBody RecetaDto recetaDto) {
        try {
            RecetaDto recetaActualizada = recetasService.actualizarReceta(id, recetaDto);
            return new ResponseEntity<>(recetaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> obtenerPDFDeReceta(@RequestBody RecetaDto recetaDto) {
        try {
            byte[] pdfBytes = recetasService.obtenerPDFReceta(recetaDto);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=Receta.pdf");
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Length", "" + pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    

 
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Page<RecetaDto>> obtenerRecetasPaciente(@PathVariable int idPaciente,@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<RecetaDto> recetas = recetasService.obtenerTodasRecetasDePaciente(idPaciente,fechaInicio,fechaFin,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(recetas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
   
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try{
            recetasService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
