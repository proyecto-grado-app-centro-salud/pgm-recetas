package com.example.microservicio_recetas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_recetas.model.HistoriaClinicaEntity;
import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.model.UsuarioEntity;
import com.example.microservicio_recetas.model.dto.RecetaDto;
import com.example.microservicio_recetas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.repository.UsuariosRepositoryJPA;

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

    public List<RecetaDto> obtenerTodasRecetas() {
        List<RecetasEntity> recetas = recetasRepository.findAll();
        return recetas.stream()
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

    public List<RecetaDto> obtenerTodasRecetasDePaciente(int idPaciente) {
        List<RecetasEntity> recetas = recetasRepository.obtenerNotasEvolucionPaciente(idPaciente);
        return recetas.stream()
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
