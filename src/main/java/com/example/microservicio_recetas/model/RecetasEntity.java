package com.example.microservicio_recetas.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recetas")
public class RecetasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta")
    private int idReceta;

    @Column(name = "nombre_generico_medicamento_preescrito")
    private String nombreGenericoMedicamentoPreescrito;

    @Column(name = "via_cuidado_especiales_administracion")
    private String viaCuidadoEspecialesAdministracion;

    @Column(name = "concentracion_dosificacion")
    private String concentracionDosificacion;

    @Column(name = "frecuencia_administracion_24hrs")
    private String frecuenciaAdministracion24hrs;

    @Column(name = "duracion_tratamiento")
    private String duracionTratamiento;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "precauciones_especiales")
    private String precaucionesEspeciales;

    @Column(name = "indicaciones_especiales")
    private String indicacionesEspeciales;

    @Column(name = "id_medico")
    private int idMedico;

    @Column(name = "id_historia_clinica")
    private int idHistoriaClinica;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public void markAsDeleted() {
        deletedAt = new Date();
    }
}
