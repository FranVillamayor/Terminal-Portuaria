package filtro;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import viaje.Viaje;

class OperacionANDTest {

	@Test
	void andConMultiplesFiltrosTest() {
		
		//setUp - Construccin de clases con mocks 
		OperacionOR or = mock(OperacionOR.class);
		FiltroFechaLlegada fechaLlegada = mock(FiltroFechaLlegada.class);
		FiltroFechaSalida fechaSalida = mock(FiltroFechaSalida.class);
		FiltroPuertoDestino puertoDestino = mock(FiltroPuertoDestino.class);
		
		Viaje viaje1 = mock(Viaje.class);
        Viaje viaje2 = mock(Viaje.class);
        Viaje viaje3 = mock(Viaje.class);
        Viaje viaje4 = mock(Viaje.class);
        Viaje viaje5 = mock(Viaje.class);
        Viaje viaje6 = mock(Viaje.class);
        Viaje viajeCoincidente = mock(Viaje.class);
        
        List<Viaje> viajesFiltroLlegada = Arrays.asList(viaje1, viaje2, viajeCoincidente);
        List<Viaje> viajesFiltroSalida = Arrays.asList(viaje3, viaje4, viajeCoincidente);
        List<Viaje> viajesFiltroPuertoDestino = Arrays.asList(viaje5, viaje6, viajeCoincidente);
        List<Viaje> viajesFiltroOR = Arrays.asList(viaje3, viaje6, viajeCoincidente);
        
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2, viaje3, viaje4, viaje5, viaje6, viajeCoincidente);
				
		//Configuro el comportamiento de los mocks 
        when(fechaLlegada.filtrar(viajes)).thenReturn(viajesFiltroLlegada);
        when(fechaSalida.filtrar(viajes)).thenReturn(viajesFiltroSalida);
        when(puertoDestino.filtrar(viajes)).thenReturn(viajesFiltroPuertoDestino);
		when(or.filtrar(viajes)).thenReturn(viajesFiltroOR);
        
		//Instancio el operador AND y le agrego los filtros
        OperacionAND and = new OperacionAND();
        and.agregar(fechaLlegada);
        and.agregar(fechaSalida);
        and.agregar(puertoDestino);
        and.agregar(or);
        
        //Exercise
        List<Viaje> resultado = and.filtrar(viajes);
        
        // Verify - Verificacion de los resultados esperados 
        assertEquals(1, resultado.size());
        assertEquals(viajeCoincidente, resultado.get(0));
        verify(fechaLlegada).filtrar(viajes);
        verify(fechaSalida).filtrar(viajes);
        verify(puertoDestino).filtrar(viajes);
        verify(or).filtrar(viajes);
	}
	
	@Test
    void testOperacionANDConListaVacia() {
        // setUp
        FiltroFechaLlegada filtro = mock(FiltroFechaLlegada.class);
        List<Viaje> listaVacia = new ArrayList<>();
        
        when(filtro.filtrar(listaVacia)).thenReturn(new ArrayList<>());
        
        OperacionAND and = new OperacionAND();
        and.agregar(filtro);
        
        // Exercise
        List<Viaje> resultado = and.filtrar(listaVacia);
        
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
        
        OperacionAND and = new OperacionAND();
        and.agregar(filtro); //Agrego el filtro
        
        
        // Exercise
        and.eliminar(filtro);//Elimino el filtro
        List<Viaje> resultado = and.filtrar(viajes);
        
        // Verify
        assertEquals(2, resultado.size(), "Sin filtros, debería devolver todos los viajes");
        assertTrue(resultado.containsAll(viajes));
    }

}
