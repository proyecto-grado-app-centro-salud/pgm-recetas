package com.example.microservicio_recetas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_recetas.model.HistoriaClinicaEntity;
import com.example.microservicio_recetas.model.UsuarioEntity;


public interface HistoriaClinicaRepositoryJPA extends JpaRepository<HistoriaClinicaEntity, Integer> {

    List<HistoriaClinicaEntity> findByPaciente(UsuarioEntity paciente);

    Optional<HistoriaClinicaEntity> findByIdHistoriaClinicaAndDeletedAtIsNull(Integer idHistoriaClinica);
}