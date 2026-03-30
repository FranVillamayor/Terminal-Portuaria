package criterio;


import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import circuito.Circuito;
import naviera.Naviera;
import terminalPortuaria.TerminalPortuaria;

class CriterioMenorPrecioTest {

	@Test
	void circuitoConMenorPrecioTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
		
		//Navieras
		Naviera n1 = mock(Naviera.class);
		Naviera n2 = mock(Naviera.class);
		Naviera n3 = mock(Naviera.class);
		Naviera n4 = mock(Naviera.class);
				
		//Circuitos
		Circuito c1 = mock(Circuito.class);
		Circuito c2 = mock(Circuito.class); 
		Circuito c3 = mock(Circuito.class); 
		Circuito c4 = mock(Circuito.class); 
		Circuito c5 = mock(Circuito.class);
		Circuito c6 = mock(Circuito.class);
		Circuito c7 = mock(Circuito.class); 
		Circuito c8 = mock(Circuito.class); 
		Circuito c9 = mock(Circuito.class); 
		Circuito c10 = mock(Circuito.class);
				
				
		//Cargo las navieras con sus circuitos 
		
		n1.registrarCircuito(c1);
		n1.registrarCircuito(c2);
		n1.registrarCircuito(c3);
				
		n2.registrarCircuito(c4);
		n2.registrarCircuito(c5);
		n2.registrarCircuito(c6);
		
		n3.registrarCircuito(c7);
		
		n4.registrarCircuito(c8);
		n4.registrarCircuito(c9);
		n4.registrarCircuito(c10);
		
		
		List<Naviera> navieras = Arrays.asList(n1, n2, n3, n4);
		CriterioMenorPrecio criterio = new CriterioMenorPrecio();
		
		//Configuro los comportamientos de los mocks
		when(n1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		when(n2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		when(n3.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(n4.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
		
		//El filtro descarta la naviera 3 por lo tanto el c7 no esta
		when(c1.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(150.00);
		when(c2.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(120.00);
		when(c3.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(160.00);
		when(c4.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(110.00);
		when(c5.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100.00);
		when(c6.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(80.00);
		when(c8.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(130.00);
		when(c9.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100.00);
		when(c10.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(106.00);
		
		//Filtrado de mejor circuito por cada naviera
		when(n1.circuitoMenorPrecioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(c2);
		when(n2.circuitoMenorPrecioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(c6);
		when(n4.circuitoMenorPrecioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(c9);
		
		//Exercise
		Circuito resultado = criterio.mejorCircuito(navieras,terminalGestionada,puertoDestino);
		
		// Verify - verificar los resultados esperados 
		assertEquals(resultado, c6);
	}
	
	@Test
	void listaNavieraVaciaTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
		List<Naviera> navieras = new ArrayList<>();
		CriterioMenorPrecio criterio = new CriterioMenorPrecio();
		
		//Lista vacia debe lanzar la exception.
		assertThrows(IllegalArgumentException.class, 
				() -> criterio.mejorCircuito(navieras, terminalGestionada, puertoDestino));
	}
	
	@Test
	void listaNavierasSinCoincidirConLosPuertosTest() {
		//setUp - inicializo las clases con mock 
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
			
		Naviera n1 = mock(Naviera.class);
		Naviera n2 = mock(Naviera.class);
		Naviera n3 = mock(Naviera.class);
		Naviera n4 = mock(Naviera.class);
				
		List<Naviera> navieras = Arrays.asList(n1, n2, n3, n4);
		CriterioMenorPrecio criterio = new CriterioMenorPrecio();
			
		//Configuro los comportamientos de los mocks
		when(n1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(n2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(n3.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		when(n4.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false);
		
		//Lista vacia luego del filtrado por coincidencia de puertos, debe lanzar la exception.
		assertThrows(IllegalArgumentException.class, 
				() -> criterio.mejorCircuito(navieras, terminalGestionada, puertoDestino));
	}
	
	@Test
	void testConPreciosIguales() {
		TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
	    TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
	    
	    //Navieras
	    Naviera n1 = mock(Naviera.class);
	  	Naviera n2 = mock(Naviera.class);
	    
	    Circuito c1 = mock(Circuito.class);
	    Circuito c2 = mock(Circuito.class);
	    
	    //Cargo las navieras con sus circuitos 
	  	n1.registrarCircuito(c1);
	  	n2.registrarCircuito(c2);
	    
	    List<Naviera> navieras = Arrays.asList(n1, n2);
	    CriterioMenorPrecio criterio = new CriterioMenorPrecio();
	    
	    when(n1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    when(n2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    
	    when(c1.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100.00);
	    when(c2.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(100.00);
	   
	    when(n1.circuitoMenorPrecioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(c1);
		when(n2.circuitoMenorPrecioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(c2);
	    
	    
	    
	    Circuito resultado = criterio.mejorCircuito(navieras, terminalGestionada, puertoDestino);
	    
	    // Cuando hay mismo precio, Stream.min() devuelve el primero que encuentra
	    assertNotNull(resultado);
	    //verificar que al menos es uno de los dos
	    assertTrue(resultado == c1 || resultado == c2);
	}
	
	@Test
	void testConUnSoloCircuitoValido() {
	    TerminalPortuaria terminalGestionada = mock(TerminalPortuaria.class);
	    TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
	    
	    //Navieras
	    Naviera n1 = mock(Naviera.class);
	  	Naviera n2 = mock(Naviera.class);
	    
	    Circuito c1 = mock(Circuito.class);
	    Circuito c2 = mock(Circuito.class);
	    
	    //Cargo las navieras con sus circuitos 
	  	n1.registrarCircuito(c1);
	  	n2.registrarCircuito(c2);
	    
	    List<Naviera> navieras = Arrays.asList(n1, n2);
	    CriterioMenorPrecio criterio = new CriterioMenorPrecio();
	    
	    when(n1.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(true);
	    when(n2.recorreDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(false); // No válido
	    
	    when(c1.precioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(200.00);
	    
	    when(n1.circuitoMenorPrecioDesdeHasta(terminalGestionada, puertoDestino)).thenReturn(c1);
	    
	    Circuito resultado = criterio.mejorCircuito(navieras, terminalGestionada, puertoDestino);
	    
	    assertEquals(c1, resultado);
	}
}
