package com.example.microservicio_recetas.model.dto;

import java.util.Date;

import com.example.microservicio_recetas.model.RecetasEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecetaDto {
    private Integer idReceta;
    private String nombreGenericoMedicamentoPreescrito;
    private String viaCuidadoEspecialesAdministracion;
    private String concentracionDosificacion;
    private String frecuenciaAdministracion24hrs;
    private String duracionTratamiento;
    private Date fechaVencimiento;
    private String precaucionesEspeciales;
    private String indicacionesEspeciales;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Integer idHistoriaClinica;
    private String diagnosticoPresuntivo;
    private Integer idEspecialidad;
    private String nombreEspecialidad;
    private Integer idMedico;
    private String nombreMedico;
    private Integer idPaciente;
    private String pacientePropietario;
    private String ciPropietario;
    public RecetaDto(Integer idReceta, String nombreGenericoMedicamentoPreescrito,
            String viaCuidadoEspecialesAdministracion, String concentracionDosificacion,
            String frecuenciaAdministracion24hrs, String duracionTratamiento, Date fechaVencimiento,
            String precaucionesEspeciales, String indicacionesEspeciales, int idMedico, int idHistoriaClinica,
            Date createdAt, Date updatedAt, String ciPropietario, String pacientePropietario, Date deletedAt) {
        this.idReceta = idReceta;
        this.nombreGenericoMedicamentoPreescrito = nombreGenericoMedicamentoPreescrito;
        this.viaCuidadoEspecialesAdministracion = viaCuidadoEspecialesAdministracion;
        this.concentracionDosificacion = concentracionDosificacion;
        this.frecuenciaAdministracion24hrs = frecuenciaAdministracion24hrs;
        this.duracionTratamiento = duracionTratamiento;
        this.fechaVencimiento = fechaVencimiento;
        this.precaucionesEspeciales = precaucionesEspeciales;
        this.indicacionesEspeciales = indicacionesEspeciales;
        this.idMedico = idMedico;
        this.idHistoriaClinica = idHistoriaClinica;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ciPropietario = ciPropietario;
        this.pacientePropietario = pacientePropietario;
        this.deletedAt = deletedAt;
    }

    public RecetaDto convertirRecetasEntityARecetasDto(RecetasEntity entity) {
        RecetaDto dto = new RecetaDto();
        dto.setIdReceta(entity.getIdReceta());
        dto.setNombreGenericoMedicamentoPreescrito(entity.getNombreGenericoMedicamentoPreescrito());
        dto.setViaCuidadoEspecialesAdministracion(entity.getViaCuidadoEspecialesAdministracion());
        dto.setConcentracionDosificacion(entity.getConcentracionDosificacion());
        dto.setFrecuenciaAdministracion24hrs(entity.getFrecuenciaAdministracion24hrs());
        dto.setDuracionTratamiento(entity.getDuracionTratamiento());
        dto.setFechaVencimiento(entity.getFechaVencimiento());
        dto.setPrecaucionesEspeciales(entity.getPrecaucionesEspeciales());
        dto.setIndicacionesEspeciales(entity.getIndicacionesEspeciales());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setIdHistoriaClinica(entity.getHistoriaClinica().getIdHistoriaClinica()); 
        dto.setDiagnosticoPresuntivo(entity.getHistoriaClinica().getDiagnosticoPresuntivo());
        dto.setIdEspecialidad(entity.getHistoriaClinica().getEspecialidad().getIdEspecialidad());
        dto.setNombreEspecialidad(entity.getHistoriaClinica().getEspecialidad().getNombre());
        dto.setIdPaciente(entity.getHistoriaClinica().getPaciente().getIdUsuario());
        dto.setPacientePropietario(entity.getHistoriaClinica().getPaciente().getNombres()+" "+entity.getHistoriaClinica().getPaciente().getApellidoPaterno()+" "+entity.getHistoriaClinica().getPaciente().getApellidoMaterno());
        dto.setCiPropietario(entity.getHistoriaClinica().getPaciente().getCi());
        dto.setIdMedico(entity.getMedico().getIdUsuario()); 
        dto.setNombreMedico(entity.getMedico().getNombres()+" "+entity.getMedico().getApellidoPaterno()+" "+entity.getMedico().getApellidoMaterno());
        return dto;
    }
 }
