package filtro;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import viaje.Viaje;

class OperacionORTest {

	@Test
	void orConMultiplesFiltrosTest() {
		
		//setUp - Construccin de clases con mocks 
		OperacionAND and = mock(OperacionAND.class);
		FiltroFechaLlegada fechaLlegada = mock(FiltroFechaLlegada.class);
		FiltroFechaSalida fechaSalida = mock(FiltroFechaSalida.class);
		FiltroPuertoDestino puertoDestino = mock(FiltroPuertoDestino.class);
		
		Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        Viaje viaje3 = mock(Viaje.class);
        Viaje viaje4 = mock(Viaje.class);
        Viaje viaje5 = mock(Viaje.class);
        Viaje viaje6 = mock(Viaje.class);
        
        
        List<Viaje> viajesFiltroLlegada = Arrays.asList(viaje1, viaje2);
        List<Viaje> viajesFiltroSalida = Arrays.asList(viaje3, viaje4);
        List<Viaje> viajesFiltroPuertoDestino = Arrays.asList(viaje5, viaje6);
        List<Viaje> viajesFiltroAnd = Arrays.asList(viaje3, viaje6);
        
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2, viaje3, viaje4, viaje5, viaje6);
				
		//Configuro el comportamiento de los mocks 
        when(fechaLlegada.filtrar(viajes)).thenReturn(viajesFiltroLlegada);
        when(fechaSalida.filtrar(viajes)).thenReturn(viajesFiltroSalida);
        when(puertoDestino.filtrar(viajes)).thenReturn(viajesFiltroPuertoDestino);
		when(and.filtrar(viajes)).thenReturn(viajesFiltroAnd);
        
		//Instancio el operador AND y le agrego los filtros
        OperacionOR or = new OperacionOR();
        or.agregar(fechaLlegada);
        or.agregar(fechaSalida);
        or.agregar(puertoDestino);
        or.agregar(and);
        
        //Exercise
        List<Viaje> resultado = or.filtrar(viajes);
        
        // Verify - Verificacion de los resultados esperados 
        assertEquals(6, resultado.size());
        verify(fechaLlegada).filtrar(viajes);
        verify(fechaSalida).filtrar(viajes);
        verify(puertoDestino).filtrar(viajes);
        verify(and).filtrar(viajes);
	}
	
	@Test
    void testOperacionANDConListaVacia() {
        // setUp
        FiltroFechaLlegada filtro = mock(FiltroFechaLlegada.class);
        List<Viaje> listaVacia = new ArrayList<>();
        
        when(filtro.filtrar(listaVacia)).thenReturn(new ArrayList<>());
        
        OperacionOR or = new OperacionOR();
        or.agregar(filtro);
        
        // Exercise
        List<Viaje> resultado = or.filtrar(listaVacia);
        
        // Verify
        assertTrue(resultado.isEmpty(), "El resultado debería estar vacío");
    }
	
	@Test
	void testOperacionANDSinFiltros() {
        // setUp
		FiltroFechaLlegada filtro = mock(FiltroFechaLlegada.class);
        Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        
        OperacionOR or = new OperacionOR();
        or.agregar(filtro); //Agrego el filtro
        
        
        // Exercise
        or.eliminar(filtro);//Elimino el filtro
        List<Viaje> resultado = or.filtrar(viajes);
        
        // Verify
        assertEquals(2, resultado.size(), "Sin filtros, debería devolver todos los viajes");
        assertTrue(resultado.containsAll(viajes));
    }

}
