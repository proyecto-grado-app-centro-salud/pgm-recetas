package com.example.microservicio_recetas.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import com.example.microservicio_recetas.model.RecetasEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RecetasRepository extends JpaRepository<RecetasEntity, Integer>,JpaSpecificationExecutor<RecetasEntity>{
//     @Query(value="SELECT id_receta, nombre_generico_medicamento_preescrito, via_cuidado_especiales_administracion, concentracion_dosificacion, frecuencia_administracion_24hrs, duracion_tratamiento, fecha_vencimiento, precauciones_especiales, indicaciones_especiales, r.id_medico, hc.id_historia_clinica, r.created_at, r.updated_at,p.ci,CONCAT(p.nombres,p.apellido_paterno,' ',p.apellido_materno),r.deleted_at "+
//     "FROM recetas r  "+
//     "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = r.id_historia_clinica "+
//     "INNER JOIN usuarios p ON hc.id_paciente = p.id_usuario",nativeQuery=true)
//     List<Object[]> obtenerRecetas();
//     @Query(value="SELECT id_receta, nombre_generico_medicamento_preescrito, via_cuidado_especiales_administracion, concentracion_dosificacion, frecuencia_administracion_24hrs, duracion_tratamiento, fecha_vencimiento, precauciones_especiales, indicaciones_especiales, r.id_medico, hc.id_historia_clinica, r.created_at, r.updated_at,p.ci,CONCAT(p.nombres,p.apellido_paterno,' ',p.apellido_materno),r.deleted_at "+
//     "FROM recetas r  "+
//     "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = r.id_historia_clinica "+
//     "INNER JOIN usuarios p ON hc.id_paciente = p.id_usuario "+
//     "WHERE p.id_usuario=?1",nativeQuery=true)
//     List<Object[]> obtenerRecetasPaciente(int idPaciente);
//     @Query(value="SELECT id_receta, nombre_generico_medicamento_preescrito, via_cuidado_especiales_administracion, concentracion_dosificacion, frecuencia_administracion_24hrs, duracion_tratamiento, fecha_vencimiento, precauciones_especiales, indicaciones_especiales, r.id_medico, hc.id_historia_clinica, r.created_at, r.updated_at,p.ci,CONCAT(p.nombres,p.apellido_paterno,' ',p.apellido_materno),r.deleted_at "+
//     "FROM recetas r  "+
//     "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = r.id_historia_clinica "+
//     "INNER JOIN usuarios p ON hc.id_paciente = p.id_usuario "+
//     "WHERE r.id_receta=?1",nativeQuery=true)
//     List<Object[]> obtenerRecetaPorId(int id);
    @Query("SELECT re FROM RecetasEntity re "
    + "JOIN re.historiaClinica hc "
    + "JOIN hc.paciente p "
    + "WHERE p.idUsuario = :idPaciente")
    List<RecetasEntity> obtenerNotasEvolucionPaciente(@Param("idPaciente") int idPaciente);
    Optional<RecetasEntity> findByIdRecetaAndDeletedAtIsNull(int idReceta);

    @Modifying
    @Query(value = "UPDATE recetas SET deleted_at = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void markAsDeletedAllRecetasFromHistoriaClinica(int idHistoriaClinica, Date date);

}