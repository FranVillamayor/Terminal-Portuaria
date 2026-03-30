package naviera;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buque.Buque;
import buque.GPS;
import circuito.Circuito;
import circuito.Tramo;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

class NavieraTest {
	Naviera naviera;
	Buque b1;
	Buque b2;
	Buque b3;
	GPS gps1;
	GPS gps2;
	GPS gps3;
	Circuito circuito1;
	Circuito circuito2;
	Circuito circuito3;
	Tramo argToBra;
	Tramo braToEsp;
	Tramo espToArg;
	Tramo argToUsa;
	Tramo braToArg;
	Tramo usaToBra;
	TerminalPortuaria Argentina;
	TerminalPortuaria Brasil;
	TerminalPortuaria España;
	TerminalPortuaria EstadosUnidos;

	
	@BeforeEach
	public void setUp() {
		gps1 = mock(GPS.class);
	    gps2 = mock(GPS.class);
	    gps3 = mock(GPS.class);
		
		Argentina = mock(TerminalPortuaria.class);
		Brasil = mock(TerminalPortuaria.class);
		España = mock(TerminalPortuaria.class);
		EstadosUnidos = mock(TerminalPortuaria.class);
		
		b1 = new Buque("Buque1", Argentina, gps1);
		b2 = new Buque("Buque2", Argentina, gps2);
		b3 = new Buque("Buque3", Argentina, gps3);
		
		circuito1 = spy(new Circuito());
		circuito2 = spy(new Circuito());
		circuito3 = spy(new Circuito());
		
		argToBra = new Tramo(Argentina, Brasil, 5, 500);
	    braToEsp = new Tramo(Brasil, España, 10, 1000);
	    espToArg = new Tramo(España, Argentina, 15, 1500);
	    argToUsa = new Tramo(Argentina, EstadosUnidos, 20, 2000);
	    braToArg = new Tramo(Brasil, Argentina, 5, 500);
	    usaToBra = new Tramo(EstadosUnidos, Brasil, 18, 1700);
	    
	    circuito1.agregarTramo(argToBra);
	    circuito1.agregarTramo(braToEsp);
	    circuito1.agregarTramo(espToArg);
	    
	    circuito2.agregarTramo(braToArg);
	    circuito2.agregarTramo(argToUsa);
	    circuito2.agregarTramo(usaToBra);
	    
	    circuito3.agregarTramo(espToArg);
	    circuito3.agregarTramo(argToBra);
	    circuito3.agregarTramo(braToEsp);
		
		naviera = new Naviera();
		
		naviera.registrarCircuito(circuito1);
		naviera.registrarCircuito(circuito2);
		naviera.registrarCircuito(circuito3);
		
		naviera.registrarBuque(b1);
		naviera.registrarBuque(b2);
		naviera.registrarBuque(b3);
		
		naviera.programarNuevoViaje(b1, circuito1, LocalDateTime.of(2026, 1, 24, 6, 30), Argentina);
		naviera.programarNuevoViaje(b2, circuito2, LocalDateTime.of(2026, 2, 16, 18, 30), Brasil);
		naviera.programarNuevoViaje(b3, circuito3, LocalDateTime.of(2026, 11, 5, 10, 15), España);
	}
	
	@Test
	public void proximaFechaHaciaTest() {
		when(circuito1.contieneTerminal(España)).thenReturn(true);
		when(circuito2.contieneTerminal(España)).thenReturn(false);
		when(circuito3.contieneTerminal(España)).thenReturn(true);

		Optional<LocalDateTime> fecha = naviera.proximaFechaHacia(España);
		
		assertTrue(fecha.isPresent());
		assertEquals(LocalDateTime.of(2026, 11, 05, 10, 15), fecha.get()); 
	}

	@Test
	public void recorreDesdeHastaTest() {
		when(circuito1.recorreDesdeHasta(Argentina, España)).thenReturn(true);
		when(circuito2.recorreDesdeHasta(Argentina, España)).thenReturn(false);
		when(circuito3.recorreDesdeHasta(Argentina, España)).thenReturn(true);
		when(circuito1.recorreDesdeHasta(EstadosUnidos, España)).thenReturn(false);
		when(circuito2.recorreDesdeHasta(EstadosUnidos, España)).thenReturn(false);
		when(circuito3.recorreDesdeHasta(EstadosUnidos, España)).thenReturn(false);

		boolean resultado = naviera.recorreDesdeHasta(Argentina, España);
		boolean resultado4 = naviera.recorreDesdeHasta(EstadosUnidos, España);
		
		assertTrue(resultado);
		assertFalse(resultado4);
	}
	
	@Test
	public void escalasDesdeHastaTest() {
		doReturn(true).when(circuito1).recorreDesdeHasta(Argentina, España);
		doReturn(true).when(circuito2).recorreDesdeHasta(Argentina, España);
		doReturn(true).when(circuito3).recorreDesdeHasta(Argentina, España);

		doReturn(1).when(circuito1).escalasDesdeHasta(Argentina, España);
		doReturn(2).when(circuito2).escalasDesdeHasta(Argentina, España);
		doReturn(1).when(circuito3).escalasDesdeHasta(Argentina, España);

		List<Integer> escalas = naviera.escalasDesdeHasta(Argentina, España);
		
		assertEquals(List.of(1, 2, 1), escalas);
		
		verify(circuito1).escalasDesdeHasta(Argentina, España);
		verify(circuito2).escalasDesdeHasta(Argentina, España);
		verify(circuito3).escalasDesdeHasta(Argentina, España);
	}
	
	@Test
	public void escalasDesdeHastaLanzaExcepcionTest() {
		when(circuito1.recorreDesdeHasta(España, EstadosUnidos)).thenReturn(false);
		when(circuito2.recorreDesdeHasta(España, EstadosUnidos)).thenReturn(false);
		when(circuito3.recorreDesdeHasta(España, EstadosUnidos)).thenReturn(false);
		
		assertThrows(IllegalArgumentException.class, () -> {
			naviera.escalasDesdeHasta(España, EstadosUnidos);
		});
	}
	
	@Test
	public void precioDesdeHastaTest() {
		when(circuito1.recorreDesdeHasta(Brasil, Argentina)).thenReturn(true);
		when(circuito2.recorreDesdeHasta(Brasil, Argentina)).thenReturn(true);
		when(circuito3.recorreDesdeHasta(Brasil, Argentina)).thenReturn(false);
		when(circuito1.precioDesdeHasta(Brasil, Argentina)).thenReturn(2500.0);
		when(circuito2.precioDesdeHasta(Brasil, Argentina)).thenReturn(500.0);
		when(circuito3.precioDesdeHasta(Brasil, Argentina)).thenReturn(-1.0);

		List<Double> precios = naviera.preciosDesdeHasta(Brasil, Argentina);
		
		when(circuito1.recorreDesdeHasta(Brasil, Argentina)).thenReturn(true);
		when(circuito2.recorreDesdeHasta(Brasil, Argentina)).thenReturn(true);
		when(circuito3.recorreDesdeHasta(Brasil, Argentina)).thenReturn(true);
		when(circuito1.precioDesdeHasta(Brasil, Argentina)).thenReturn(2500.0);
		when(circuito2.precioDesdeHasta(Brasil, Argentina)).thenReturn(500.0);
		when(circuito3.precioDesdeHasta(Brasil, Argentina)).thenReturn(-1.0);
		
		precios = naviera.preciosDesdeHasta(Brasil, Argentina);
		
		assertEquals(List.of(2500.0, 500.0), precios);
	}
	
	@Test
	public void precioDesdeHastaLanzaExcepcionTest() {
		doReturn(false).when(circuito1).recorreDesdeHasta(España, EstadosUnidos);
		doReturn(false).when(circuito2).recorreDesdeHasta(España, EstadosUnidos);
		doReturn(false).when(circuito3).recorreDesdeHasta(España, EstadosUnidos);
		
		assertThrows(IllegalArgumentException.class, () -> {
			naviera.preciosDesdeHasta(España, EstadosUnidos);
		});
	}
	
	@Test
	public void programarNuevoViajeTest() {
		Buque b4 = new Buque("Buque4", Argentina, gps1);
	    
	    assertThrows(IllegalArgumentException.class, () -> {
	        naviera.programarNuevoViaje(b4, circuito1, LocalDateTime.now(), Argentina);
	    });
	}

	@Test
	public void circuitoMenorPrecioDesdeHastaTest() {
	    doReturn(true).when(circuito1).recorreDesdeHasta(Brasil, Argentina);
	    doReturn(true).when(circuito2).recorreDesdeHasta(Brasil, Argentina);
	    doReturn(false).when(circuito3).recorreDesdeHasta(Brasil, Argentina);
	    
	    doReturn(2500.0).when(circuito1).precioDesdeHasta(Brasil, Argentina);
	    doReturn(500.0).when(circuito2).precioDesdeHasta(Brasil, Argentina);

	    Circuito masBarato = naviera.circuitoMenorPrecioDesdeHasta(Brasil, Argentina);

	    assertEquals(circuito2, masBarato);
	    
	    verify(circuito1).recorreDesdeHasta(Brasil, Argentina);
	    verify(circuito2).recorreDesdeHasta(Brasil, Argentina);
	    verify(circuito3).recorreDesdeHasta(Brasil, Argentina);
	    
	    verify(circuito1).precioDesdeHasta(Brasil, Argentina);
	    verify(circuito2).precioDesdeHasta(Brasil, Argentina);
	    verify(circuito3, never()).precioDesdeHasta(Brasil, Argentina);
	}
	
	@Test
	public void circuitoMenorTiempoDesdeHastaTest() {
	    doReturn(true).when(circuito1).recorreDesdeHasta(Brasil, Argentina);
	    doReturn(true).when(circuito2).recorreDesdeHasta(Brasil, Argentina);
	    doReturn(true).when(circuito3).recorreDesdeHasta(Brasil, Argentina);
	    
	    doReturn(25).when(circuito1).tiempoDesdeHasta(Brasil, Argentina);
	    doReturn(5).when(circuito2).tiempoDesdeHasta(Brasil, Argentina);
	    doReturn(30).when(circuito3).tiempoDesdeHasta(Brasil, Argentina);

	    Circuito masRapido = naviera.circuitoMenorTiempoDesdeHasta(Brasil, Argentina);
	    
	    assertEquals(circuito2, masRapido);
	    
	    verify(circuito1, atLeastOnce()).tiempoDesdeHasta(Brasil, Argentina);
	    verify(circuito2, atLeastOnce()).tiempoDesdeHasta(Brasil, Argentina);
	    verify(circuito3, atLeastOnce()).tiempoDesdeHasta(Brasil, Argentina);
	}

	@Test
	public void circuitoMenorEscalasDesdeHastaTest() {
		doReturn(true).when(circuito1).recorreDesdeHasta(Brasil, Argentina);
	    doReturn(true).when(circuito2).recorreDesdeHasta(Brasil, Argentina);
	    doReturn(false).when(circuito3).recorreDesdeHasta(Brasil, Argentina);
	    
	    doReturn(1).when(circuito1).escalasDesdeHasta(Brasil, Argentina);
	    doReturn(0).when(circuito2).escalasDesdeHasta(Brasil, Argentina);

		Circuito menosEscalas = naviera.circuitoMenorEscalasDesdeHasta(Brasil, Argentina);
		
		assertEquals(circuito2, menosEscalas);
		
		verify(circuito1).escalasDesdeHasta(Brasil, Argentina);
		verify(circuito2).escalasDesdeHasta(Brasil, Argentina);
		verify(circuito3, never()).escalasDesdeHasta(Brasil, Argentina);
	}
	
	@Test
    public void getViajesDeTerminalFiltraCorrectamenteTest() {
        when(circuito1.contieneTerminal(España)).thenReturn(true);
        when(circuito2.contieneTerminal(España)).thenReturn(false);
        when(circuito3.contieneTerminal(España)).thenReturn(true);

        List<Viaje> viajesConEspaña = naviera.getViajesDeTerminal(España);
        
        assertEquals(2, viajesConEspaña.size());
    }

    @Test
    public void tiempoDesdeHastaDevuelveTodosLosTiemposTest() {
        doReturn(15).when(circuito1).tiempoDesdeHasta(Argentina, España);
        doReturn(20).when(circuito2).tiempoDesdeHasta(Argentina, España);
        doReturn(16).when(circuito3).tiempoDesdeHasta(Argentina, España);

        List<Integer> tiempos = naviera.tiempoDesdeHasta(Argentina, España);
        
        assertEquals(List.of(15, 20, 16), tiempos);
        
        verify(circuito1).tiempoDesdeHasta(Argentina, España);
        verify(circuito2).tiempoDesdeHasta(Argentina, España);
        verify(circuito3).tiempoDesdeHasta(Argentina, España);
    }
}