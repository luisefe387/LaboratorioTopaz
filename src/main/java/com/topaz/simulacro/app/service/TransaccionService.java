package com.topaz.simulacro.app.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.topaz.simulacro.app.entity.EstadoTransaccion;
import com.topaz.simulacro.app.entity.Transaccion;
import com.topaz.simulacro.app.exception.EstadoInvalidoException;
import com.topaz.simulacro.app.exception.PlazoReversionException;
import com.topaz.simulacro.app.repository.TransaccionRepository;

@Service
public class TransaccionService {

	private final TransaccionRepository transaccionRepository;

	public TransaccionService(TransaccionRepository transaccionRepository) {
		this.transaccionRepository = transaccionRepository;
	}

	public void reversar(Long id) {
		Transaccion transaccion = transaccionRepository.findById(id).get();
		//Validar estado actual
		if(transaccion.getEstado() != EstadoTransaccion.APROBADA) {
			throw new EstadoInvalidoException("Solo se reversan transacciones aprobadas");
		}
		//Validar plazo 24 horas
		if(LocalDateTime.now().isAfter(transaccion.getFechaCreacion().plusHours(24))) {
			throw new PlazoReversionException("La transaccion supero el plazo de 24 horas");
		}
		
		transaccion.setEstado(EstadoTransaccion.REVERSADA);
		transaccionRepository.saveAndFlush(transaccion);
	}
}
