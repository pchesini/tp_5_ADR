package com.tp_5.ADR.common.error;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
     record ErrorResponse(OffsetDateTime timestamp, int status, String error, String path, List<Map<String,String>> messages) {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          org.springframework.web.context.request.ServletWebRequest req) {
        List<Map<String,String>> fields = ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> Map.of("field", fe.getField(), "message", Optional.ofNullable(fe.getDefaultMessage()).orElse("invalid")))
            .toList();
        var body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Bad Request", req.getRequest().getRequestURI(), fields);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex,
                                                        org.springframework.web.context.request.ServletWebRequest req) {
        var body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "Not Found", req.getRequest().getRequestURI(), List.of(Map.of("message", ex.getMessage())));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex,
                                                        org.springframework.web.context.request.ServletWebRequest req) {
        var body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.CONFLICT.value(),
                "Conflict", req.getRequest().getRequestURI(), List.of(Map.of("message", ex.getMessage())));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(DataIntegrityViolationException ex,
                                                          org.springframework.web.context.request.ServletWebRequest req) {
        var body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.CONFLICT.value(),
                "Conflict", req.getRequest().getRequestURI(), List.of(Map.of("message", "Violación de integridad de datos")));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex,
                                                     org.springframework.web.context.request.ServletWebRequest req) {
        var body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", req.getRequest().getRequestURI(), List.of(Map.of("message", "Error inesperado")));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
