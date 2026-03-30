package viaje;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buque.Buque;
import buque.GPS;
import circuito.Circuito;
import circuito.Tramo;
import terminalPortuaria.TerminalPortuaria;

public class ViajeTest {
	Circuito spyCircuito;
	Buque buque;
	GPS gps;
	Viaje viaje; 
	Tramo spyTramo1;
	Tramo spyTramo2;
	Tramo spyTramo3;
	TerminalPortuaria China;
	TerminalPortuaria Argentina;
	TerminalPortuaria Brasil;
	TerminalPortuaria EstadosUnidos;
	
	@BeforeEach
	public void setUp() {
		spyCircuito = spy(new Circuito());
		China = mock(TerminalPortuaria.class);
		Argentina = mock(TerminalPortuaria.class);
		Brasil = mock(TerminalPortuaria.class);
		EstadosUnidos = mock(TerminalPortuaria.class);
		gps = mock(GPS.class);
		buque = new Buque("El Perla", Argentina, gps);
		
		spyTramo1 = spy(new Tramo(Argentina, Brasil, 5, 1500.00));
		spyTramo2 = spy(new Tramo(Brasil, China, 20, 10960.00));
		spyTramo3 = spy(new Tramo(China, Argentina, 25, 13260.00));
		
		spyCircuito.agregarTramo(spyTramo1);
		spyCircuito.agregarTramo(spyTramo2);
		spyCircuito.agregarTramo(spyTramo3);
		
		viaje = new Viaje(buque, spyCircuito, LocalDateTime.of(2025, 12, 23, 15, 00), Argentina); // 23/12/2025 a las 15hs.
	}
	
	@Test
	public void proximaFechaHaciatest() {
		LocalDateTime proximaFecha = viaje.proximaFechaHacia(Brasil);
		LocalDateTime proximaFecha2 = viaje.proximaFechaHacia(EstadosUnidos);
		LocalDateTime proximaFecha3 = viaje.proximaFechaHacia(China);
		
		assertEquals(LocalDateTime.of(2025, 12, 23, 15, 00), proximaFecha);
		assertNotEquals(LocalDateTime.of(2025, 12, 23, 15, 00), proximaFecha2);
		assertEquals(LocalDateTime.of(2025, 12, 23, 15, 00), proximaFecha3);
		
		verify(spyCircuito).contieneTerminal(Brasil);
		verify(spyCircuito).contieneTerminal(EstadosUnidos);
		verify(spyCircuito).contieneTerminal(China);
	}
	
	@Test
	public void tieneProximaFechaDeLlegadaTest() {
		boolean resultado = viaje.tieneProximaFechaDeLlegada(LocalDateTime.of(2025, 12, 23, 20, 00));
		boolean resultado2 = viaje.tieneProximaFechaDeLlegada(LocalDateTime.of(2025, 12, 24, 16, 00));
		boolean resultado3 = viaje.tieneProximaFechaDeLlegada(LocalDateTime.of(2025, 11, 20, 17, 00));
		
		assertTrue(resultado);
		assertTrue(resultado2);
		assertFalse(resultado3);
	}
	
	@Test
	public void tieneFechaSalidaTest() {
		boolean resultado = viaje.tieneFechaSalida(LocalDateTime.of(2025, 12, 23, 15, 00));
		boolean resultado2 = viaje.tieneFechaSalida(LocalDateTime.of(2025, 12, 23, 20, 00));
		
		assertTrue(resultado);
		assertFalse(resultado2);
	}
	
	@Test
	public void tienePuertoDestinoTest() {
		boolean resultado = viaje.tienePuertoDestino(Brasil);
		boolean resultado2 = viaje.tienePuertoDestino(China);
		boolean resultado3 = viaje.tienePuertoDestino(EstadosUnidos);
		
		assertFalse(resultado);
		assertFalse(resultado2);
		assertFalse(resultado3);
	}
	
	@Test
	public void fechaLlegadaTerminalGestionadaTest() {
		LocalDateTime fechaOrigen = viaje.fechaLlegadaTerminalGestionada();
		assertEquals(LocalDateTime.of(2025, 12, 23, 15, 00), fechaOrigen);

		Viaje viaje2 = new Viaje(buque, spyCircuito, LocalDateTime.of(2025, 12, 23, 15, 00), Brasil);
		LocalDateTime fechaDestino = viaje2.fechaLlegadaTerminalGestionada();
		
		assertEquals(buque, viaje2.getBuque());
		assertEquals(LocalDateTime.of(2025, 12, 23, 20, 00), fechaDestino); 
	}
	
	@Test
	public void precioFinalTest() {
		when(spyCircuito.precioFinal()).thenReturn(25720.00);
		
		assertEquals(25720.00, viaje.precioFinal(), 0.01); 
		
		verify(spyCircuito).precioFinal();
	}
	
	@Test
	public void getCircuitoTest() {
		Circuito circuitoDelViaje = viaje.getCircuito();
		
		assertEquals(spyCircuito, circuitoDelViaje);
	}
	
	@Test
	public void contieneTerminalTest() {
		boolean resultadoPositivo = viaje.contieneTerminal(Brasil);
		boolean resultadoNegativo = viaje.contieneTerminal(EstadosUnidos);
		
		assertTrue(resultadoPositivo);
		assertFalse(resultadoNegativo);
		
		verify(spyCircuito).contieneTerminal(Brasil);
		verify(spyCircuito).contieneTerminal(EstadosUnidos);
	}
}
