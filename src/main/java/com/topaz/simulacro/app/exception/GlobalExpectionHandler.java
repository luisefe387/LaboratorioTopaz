package com.topaz.simulacro.app.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.OptimisticLockException;

@RestControllerAdvice
public class GlobalExpectionHandler {

	@ExceptionHandler(EstadoInvalidoException.class)
	public ResponseEntity<Map<String, Object>> manejarEstado(EstadoInvalidoException exception){
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(Map.of("mensaje",exception.getMessage()));
	}
	
	@ExceptionHandler(PlazoReversionException.class)
	public ResponseEntity<Map<String, Object>> manejarPlazo(PlazoReversionException exception){
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(Map.of("mensaje",exception.getMessage()));
	}
	
	@ExceptionHandler(OptimisticLockException.class)
	public ResponseEntity<Map<String, Object>> manejarConcurrencia(OptimisticLockException exception){
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(Map.of("mensaje","La transaccion fue modificada concurrentemente"));
	}
}
