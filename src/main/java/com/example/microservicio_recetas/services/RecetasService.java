package com.example.microservicio_recetas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microservicio_recetas.model.HistoriaClinicaEntity;
import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.model.UsuarioEntity;
import com.example.microservicio_recetas.model.dto.RecetaDto;
import com.example.microservicio_recetas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.repository.UsuariosRepositoryJPA;
import com.example.microservicio_recetas.util.RecetasSpecification;

@Service
public class RecetasService {
      @Autowired
    private RecetasRepository recetasRepository;

    @Autowired
    private UsuariosRepositoryJPA usuariosRepositoryJPA;

    @Autowired
    private HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @Autowired
    PDFService pdfService;

    @Autowired
    private ConvertirTiposDatosService convertirTiposDatosService;
    public RecetaDto registrarReceta(RecetaDto recetasDto) {
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(recetasDto.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(recetasDto.getIdHistoriaClinica())
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

        RecetasEntity recetaEntity = new RecetasEntity();
        recetaEntity.setNombreGenericoMedicamentoPreescrito(recetasDto.getNombreGenericoMedicamentoPreescrito());
        recetaEntity.setViaCuidadoEspecialesAdministracion(recetasDto.getViaCuidadoEspecialesAdministracion());
        recetaEntity.setConcentracionDosificacion(recetasDto.getConcentracionDosificacion());
        recetaEntity.setFrecuenciaAdministracion24hrs(recetasDto.getFrecuenciaAdministracion24hrs());
        recetaEntity.setDuracionTratamiento(recetasDto.getDuracionTratamiento());
        recetaEntity.setFechaVencimiento(recetasDto.getFechaVencimiento());
        recetaEntity.setPrecaucionesEspeciales(recetasDto.getPrecaucionesEspeciales());
        recetaEntity.setIndicacionesEspeciales(recetasDto.getIndicacionesEspeciales());
        recetaEntity.setMedico(medicoEntity);
        recetaEntity.setHistoriaClinica(historiaClinicaEntity);
        
        recetaEntity = recetasRepository.save(recetaEntity);

        return new RecetaDto().convertirRecetasEntityARecetasDto(recetaEntity);
    }

    public List<RecetaDto> obtenerTodasRecetas(String fechaInicio, String fechaFin, String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        List<RecetasEntity> recetasEntities = new ArrayList<>();
        Specification<RecetasEntity> spec = Specification.where(RecetasSpecification.obtenerRecetasPorParametros(convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<RecetasEntity> recetasEntitiesPage=recetasRepository.findAll(spec,pageable);
            recetasEntities=recetasEntitiesPage.getContent();
        }else{
            recetasEntities=recetasRepository.findAll(spec);
        }  
        return recetasEntities.stream()
                    .map(receta -> new RecetaDto().convertirRecetasEntityARecetasDto(receta))
                    .toList();
    }

    public RecetaDto obtenerRecetaPorId(Integer id) {
        RecetasEntity recetaEntity = recetasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        return new RecetaDto().convertirRecetasEntityARecetasDto(recetaEntity);
    }

    public RecetaDto actualizarReceta(Integer idReceta, RecetaDto recetaDto) {
        RecetasEntity recetaEntity = recetasRepository.findById(idReceta)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(recetaDto.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(recetaDto.getIdHistoriaClinica())
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

        recetaEntity.setNombreGenericoMedicamentoPreescrito(recetaDto.getNombreGenericoMedicamentoPreescrito());
        recetaEntity.setViaCuidadoEspecialesAdministracion(recetaDto.getViaCuidadoEspecialesAdministracion());
        recetaEntity.setConcentracionDosificacion(recetaDto.getConcentracionDosificacion());
        recetaEntity.setFrecuenciaAdministracion24hrs(recetaDto.getFrecuenciaAdministracion24hrs());
        recetaEntity.setDuracionTratamiento(recetaDto.getDuracionTratamiento());
        recetaEntity.setFechaVencimiento(recetaDto.getFechaVencimiento());
        recetaEntity.setPrecaucionesEspeciales(recetaDto.getPrecaucionesEspeciales());
        recetaEntity.setIndicacionesEspeciales(recetaDto.getIndicacionesEspeciales());
        recetaEntity.setMedico(medicoEntity);
        recetaEntity.setHistoriaClinica(historiaClinicaEntity);

        recetaEntity = recetasRepository.save(recetaEntity);

        return new RecetaDto().convertirRecetasEntityARecetasDto(recetaEntity);
    }

    public List<RecetaDto> obtenerTodasRecetasDePaciente(int idPaciente, String fechaInicio, String fechaFin, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        List<RecetasEntity> recetasEntities = new ArrayList<>();
        Specification<RecetasEntity> spec = Specification.where(RecetasSpecification.obtenerRecetasDePacientePorParametros(idPaciente,convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<RecetasEntity> recetasEntitiesPage=recetasRepository.findAll(spec,pageable);
            recetasEntities=recetasEntitiesPage.getContent();
        }else{
            recetasEntities=recetasRepository.findAll(spec);
        }  
        return recetasEntities.stream()
                    .map(receta -> new RecetaDto().convertirRecetasEntityARecetasDto(receta))
                    .toList();
    }

    public byte[] obtenerPDFReceta(RecetaDto recetaDto) {
        try 
        {
                return pdfService.generarPdfReporteReceta(recetaDto);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al generar el PDF.", e);
            }
    }
}
