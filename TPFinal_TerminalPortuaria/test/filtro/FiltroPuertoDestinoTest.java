package filtro;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

class FiltroPuertoDestinoTest {

	@Test
    void testFiltrarUnViajeQueCoincideConPuertoDestino() {
        // setUp - Construccion de clases con Mock 
        TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viajeCoincidente = mock(Viaje.class);
        Viaje viajeNoCoincidente = mock(Viaje.class);
        
        //Configuro el comportamiento de los mocks 
        when(viajeCoincidente.tienePuertoDestino(puertoDestino)).thenReturn(true);
        when(viajeNoCoincidente.tienePuertoDestino(puertoDestino)).thenReturn(false);
        
        //Armo lista de viaje a filtrar y construyo el filtro con la fechaFiltro
        List<Viaje> viajes = Arrays.asList(viajeCoincidente, viajeNoCoincidente);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify - Verificacion de los resultados esperados 
        assertEquals(1, resultado.size());
        assertEquals(viajeCoincidente, resultado.get(0));
        verify(viajeCoincidente).tienePuertoDestino(puertoDestino);
        verify(viajeNoCoincidente).tienePuertoDestino(puertoDestino);
    }
	
	@Test
    void testFiltrarListaVacia() {
        // SetUp - Armo el escenario a probar 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        List<Viaje> viajesVacias = new ArrayList<>();
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajesVacias);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
	
	@Test
    void testFiltrarVariosViajesQueCoinciden() {
        // setUp - construccion de viajes con mock 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        Viaje viaje3 = mock(Viaje.class);
        
        // Configuracion de comportamiento de los mocks 
        when(viaje1.tienePuertoDestino(puertoDestino)).thenReturn(true);
        when(viaje2.tienePuertoDestino(puertoDestino)).thenReturn(true);
        when(viaje3.tienePuertoDestino(puertoDestino)).thenReturn(false);
        
        
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2, viaje3);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise 
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify - verificar los resultados esperados 
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(viaje1));
        assertTrue(resultado.contains(viaje2));
        assertFalse(resultado.contains(viaje3));
    }
	
	@Test
    void testFiltrarNingunViajeCoincide() {
        // setUp - construccion de viajes con mock 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        
        //Configuracion de comportamiento de los mocks
        when(viaje1.tienePuertoDestino(puertoDestino)).thenReturn(false);
        when(viaje2.tienePuertoDestino(puertoDestino)).thenReturn(false);
        
        //Construccion de lista de viajes y el filtro 
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // Exercise 
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
	
	@Test
    void testFiltrar_TodosLosViajesCoinciden() {
        // Given
    	// setUp - construccion de viajes con mock 
		TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        when(viaje1.tienePuertoDestino(puertoDestino)).thenReturn(true);
        when(viaje2.tienePuertoDestino(puertoDestino)).thenReturn(true);

        // When
        List<Viaje> resultado = filtro.filtrar(viajes);

        // Then
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(viaje1));
        assertTrue(resultado.contains(viaje2));
    }
    
    // ===== TESTS DE MÉTODOS DE COMPOSITE (agregar y eliminar) =====

    @Test
    void testAgregarFiltro_NoHaceNada() {
        // Given - El método agregar está vacío en la implementación
    	TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        FiltroFechaLlegada otroFiltro = mock(FiltroFechaLlegada.class);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // When - No debería lanzar excepción
        filtro.agregar(otroFiltro);
        
        // Then - El método se ejecuta sin problemas
        assertTrue(true);
    }

    @Test
    void testEliminarFiltro_NoHaceNada() {
        // Given - El método eliminar está vacío en la implementación
    	TerminalPortuaria puertoDestino = mock(TerminalPortuaria.class);
        FiltroFechaLlegada otroFiltro = mock(FiltroFechaLlegada.class);
        FiltroPuertoDestino filtro = new FiltroPuertoDestino(puertoDestino);
        
        // When - No debería lanzar excepción
        filtro.eliminar(otroFiltro);
        
        // Then - El método se ejecuta sin problemas
        assertTrue(true);
    }
}
