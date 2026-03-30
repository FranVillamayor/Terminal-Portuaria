package filtro;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import viaje.Viaje;

class FiltroFechaLlegadaTest {

    @Test
    void testFiltrarUnViajeQueCoincideConFecha() {
        // setUp - Construccion de clases con Mock 
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 15, 30);
        Viaje viajeCoincidente = mock(Viaje.class);
        Viaje viajeNoCoincidente = mock(Viaje.class);
        
        //Configuro el comportamiento de los mocks 
        when(viajeCoincidente.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(true);
        when(viajeNoCoincidente.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(false);
        
        //Armo lista de viaje a filtrar y construyo el filtro con la fechaFiltro
        List<Viaje> viajes = Arrays.asList(viajeCoincidente, viajeNoCoincidente);
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify - Verificacion de los resultados esperados 
        assertEquals(1, resultado.size());
        assertEquals(viajeCoincidente, resultado.get(0));
        verify(viajeCoincidente).tieneProximaFechaDeLlegada(fechaFiltro);
        verify(viajeNoCoincidente).tieneProximaFechaDeLlegada(fechaFiltro);
    }
    
    @Test
    void testFiltrarListaVacia() {
        // SetUp - Armo el escenario a probar 
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 0, 0);
        List<Viaje> viajesVacias = new ArrayList<>();
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
        // Exercise
        List<Viaje> resultado = filtro.filtrar(viajesVacias);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
    
    @Test
    void testFiltrarVariosViajesQueCoinciden() {
        // setUp - construccion de viajes con mock 
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 10, 00);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        Viaje viaje3 = mock(Viaje.class);
        
        // Configuracion de comportamiento de los mocks 
        when(viaje1.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(true);
        when(viaje2.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(true);
        when(viaje3.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(false);
        
        
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2, viaje3);
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
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
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 3, 00);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        
        //Configuracion de comportamiento de los mocks
        when(viaje1.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(false);
        when(viaje2.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(false);
        
        //Construccion de lista de viajes y el filtro 
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
        // Exercise 
        List<Viaje> resultado = filtro.filtrar(viajes);
        
        // Verify
        assertTrue(resultado.isEmpty());
    }
    
    @Test
    void testFiltrar_TodosLosViajesCoinciden() {
        // Given
    	// setUp - construccion de viajes con mock 
        LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 3, 00);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
        when(viaje1.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(true);
        when(viaje2.tieneProximaFechaDeLlegada(fechaFiltro)).thenReturn(true);

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
    	LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 3, 00);
        FiltroFechaSalida otroFiltro = mock(FiltroFechaSalida.class);
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
        // When - No debería lanzar excepción
        filtro.agregar(otroFiltro);
        
        // Then - El método se ejecuta sin problemas
        assertTrue(true);
    }

    @Test
    void testEliminarFiltro_NoHaceNada() {
        // Given - El método eliminar está vacío en la implementación
    	LocalDateTime fechaFiltro = LocalDateTime.of(2026, 1, 1, 3, 00);
    	FiltroFechaSalida otroFiltro = mock(FiltroFechaSalida.class);
        FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
        
        // When - No debería lanzar excepción
        filtro.eliminar(otroFiltro);
        
        // Then - El método se ejecuta sin problemas
        assertTrue(true);
    }

}
