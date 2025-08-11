package com.tp_5.ADR.alumno.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tp_5.ADR.alumno.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    // Búsqueda por email
    Optional<Alumno> findByEmail(String email);

    // Verificar si existe un alumno con un email
    boolean existsByEmail(String email);
     Optional<Alumno> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);

    @Query("""
           SELECT a FROM Alumno a
           WHERE (:q IS NULL OR :q = '' OR
                 LOWER(a.nombre)   LIKE LOWER(CONCAT('%', :q, '%')) OR
                 LOWER(a.apellido) LIKE LOWER(CONCAT('%', :q, '%')) OR
                 LOWER(a.email)    LIKE LOWER(CONCAT('%', :q, '%')))
           """)
    Page<Alumno> search(@Param("q") String q, Pageable pageable);
} 
