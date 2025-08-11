package com.tp_5.ADR.alumno.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.tp_5.ADR.alumno.dto.AlumnoRequest;
import com.tp_5.ADR.alumno.dto.AlumnoResponse;
import com.tp_5.ADR.alumno.model.Alumno;
import com.tp_5.ADR.alumno.repository.AlumnoRepository;
import com.tp_5.ADR.common.error.ConflictException;
import com.tp_5.ADR.common.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repo;

    private AlumnoResponse toResponse(Alumno a) {
        return AlumnoResponse.builder()
                .id(a.getId()).nombre(a.getNombre()).apellido(a.getApellido())
                .email(a.getEmail()).fechaNacimiento(a.getFechaNacimiento())
                .telefono(a.getTelefono()).direccion(a.getDireccion())
                .build();
    }

    private void validarEmailUnico(String email, Long excluirId) {
        var existente = repo.findByEmailIgnoreCase(email).orElse(null);

        if (existente != null && (excluirId == null || !existente.getId().equals(excluirId))) {
            throw new ConflictException("El email ya está registrado");
        }
    }

    @Override @Transactional
    public AlumnoResponse crear(AlumnoRequest req) {
        validarEmailUnico(req.getEmail(), null);
        var a = Alumno.builder()
                .nombre(req.getNombre()).apellido(req.getApellido())
                .email(req.getEmail()).fechaNacimiento(req.getFechaNacimiento())
                .telefono(req.getTelefono()).direccion(req.getDireccion())
                .build();
        return toResponse(repo.save(a));
    }

    @Override @Transactional
    public AlumnoResponse editar(Long id, AlumnoRequest req) {
        var a = repo.findById(id).orElseThrow(() -> new NotFoundException("Alumno no encontrado")); 
        validarEmailUnico(req.getEmail(), id);
        a.setNombre(req.getNombre());
        a.setApellido(req.getApellido());
        a.setEmail(req.getEmail());
        a.setFechaNacimiento(req.getFechaNacimiento());
        a.setTelefono(req.getTelefono());
        a.setDireccion(req.getDireccion());
        return toResponse(repo.save(a));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Alumno no encontrado");
    }

    @Override @Transactional(readOnly = true)
    public AlumnoResponse buscar(Long id) {
        return repo.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
    }

    @Override @Transactional(readOnly = true)
    public Page<AlumnoResponse> listar(String search, Pageable pageable) {

        return repo.search(search, pageable).map(this::toResponse);
    }



}
