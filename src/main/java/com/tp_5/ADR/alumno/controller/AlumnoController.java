package com.tp_5.ADR.alumno.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import com.tp_5.ADR.alumno.dto.AlumnoRequest;
import com.tp_5.ADR.alumno.dto.AlumnoResponse;
import com.tp_5.ADR.alumno.service.AlumnoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/alumnos")
@RequiredArgsConstructor
public class AlumnoController {
     private final AlumnoService service;

    @PostMapping
    public ResponseEntity<AlumnoResponse> crear(@RequestBody @Valid AlumnoRequest req) {
        var res = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{id}")
    public AlumnoResponse editar(@PathVariable Long id, @RequestBody @Valid AlumnoRequest req) {
        return service.editar(id, req);
    }

    @GetMapping("/{id}")
    public AlumnoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping
    public Page<AlumnoResponse> listar(
            @RequestParam(required = false, name = "search") String search,
            @PageableDefault(size = 20, sort = "apellido", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.listar(search, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
