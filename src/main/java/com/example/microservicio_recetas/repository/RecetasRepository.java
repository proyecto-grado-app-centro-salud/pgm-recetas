package com.example.microservicio_recetas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_recetas.model.RecetasEntity;

import org.springframework.data.jpa.repository.Query;


public interface RecetasRepository extends JpaRepository<RecetasEntity, Integer> {
    @Query(value="SELECT id_receta, nombre_generico_medicamento_preescrito, via_cuidado_especiales_administracion, concentracion_dosificacion, frecuencia_administracion_24hrs, duracion_tratamiento, fecha_vencimiento, precauciones_especiales, indicaciones_especiales, id_medico, hc.id_historia_clinica, r.created_at, r.updated_at,p.ci,CONCAT(p.nombres,p.apellido_paterno,' ',p.apellido_materno),r.deleted_at "+
    "FROM recetas r  "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = r.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente",nativeQuery=true)
    List<Object[]> obtenerRecetas();
    @Query(value="SELECT id_receta, nombre_generico_medicamento_preescrito, via_cuidado_especiales_administracion, concentracion_dosificacion, frecuencia_administracion_24hrs, duracion_tratamiento, fecha_vencimiento, precauciones_especiales, indicaciones_especiales, id_medico, hc.id_historia_clinica, r.created_at, r.updated_at,p.ci,CONCAT(p.nombres,p.apellido_paterno,' ',p.apellido_materno),r.deleted_at "+
    "FROM recetas r  "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = r.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "WHERE p.id_paciente=?1",nativeQuery=true)
    List<Object[]> obtenerRecetasPaciente(int idPaciente);
    @Query(value="SELECT id_receta, nombre_generico_medicamento_preescrito, via_cuidado_especiales_administracion, concentracion_dosificacion, frecuencia_administracion_24hrs, duracion_tratamiento, fecha_vencimiento, precauciones_especiales, indicaciones_especiales, id_medico, hc.id_historia_clinica, r.created_at, r.updated_at,p.ci,CONCAT(p.nombres,p.apellido_paterno,' ',p.apellido_materno),r.deleted_at "+
    "FROM recetas r  "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = r.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "WHERE r.id_receta=?1",nativeQuery=true)
    List<Object[]> obtenerRecetaPorId(int id);
}