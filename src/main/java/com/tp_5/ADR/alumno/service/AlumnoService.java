package com.tp_5.ADR.alumno.service;

import com.tp_5.ADR.alumno.dto.AlumnoRequest;
import com.tp_5.ADR.alumno.dto.AlumnoResponse;
import org.springframework.data.domain.*;

public interface AlumnoService {
    AlumnoResponse crear(AlumnoRequest req);
    AlumnoResponse editar(Long id, AlumnoRequest req);
    void eliminar(Long id);
    AlumnoResponse buscar(Long id);
    Page<AlumnoResponse> listar(String search, Pageable pageable);
}
