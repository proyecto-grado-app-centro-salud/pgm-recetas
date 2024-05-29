package com.example.microservicio_recetas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_recetas.model.RecetasEntity;

public interface RecetasRepository extends JpaRepository<RecetasEntity, Integer> {
}