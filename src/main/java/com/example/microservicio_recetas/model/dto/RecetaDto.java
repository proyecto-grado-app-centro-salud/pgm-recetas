package com.example.microservicio_recetas.model.dto;

import java.util.Date;

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
    private int idMedico;
    private int idHistoriaClinica;
    private Date createdAt;
    private Date updatedAt;
    private String ciPropietario;
    private String pacientePropietario;
    private Date deletedAt;
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
 }
