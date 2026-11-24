package com.topaz.simulacro.app.serviceTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.topaz.simulacro.app.entity.EstadoTransaccion;
import com.topaz.simulacro.app.entity.Transaccion;
import com.topaz.simulacro.app.exception.EstadoInvalidoException;
import com.topaz.simulacro.app.exception.PlazoReversionException;
import com.topaz.simulacro.app.repository.TransaccionRepository;
import com.topaz.simulacro.app.service.TransaccionService;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceTest {

	@Mock
	private TransaccionRepository transaccionRepository;
	
	@InjectMocks
	private TransaccionService transaccionService;
	
	@Test
	void deberiaRechazarReversionPlazo24Horas() {
		Transaccion transaccion = new Transaccion();
		transaccion.setEstado(EstadoTransaccion.APROBADA);
		transaccion.setFechaCreacion(LocalDateTime.now().minusHours(25));
		when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));
		
		assertThrows(PlazoReversionException.class, ()-> transaccionService.reversar(1L));
	}
	
	@Test
	void deberiaRechazarReversionPorEstadoInvalido() {
		Transaccion transaccion = new Transaccion();
		transaccion.setEstado(EstadoTransaccion.PENDIENTE);
		when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));
		
		assertThrows(EstadoInvalidoException.class, ()-> transaccionService.reversar(1L));
	}
	
}
