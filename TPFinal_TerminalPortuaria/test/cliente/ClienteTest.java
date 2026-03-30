package cliente;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import factura.Factura;

class ClienteTest {

	private Cliente cliente;
	
	@BeforeEach
	public void setUp() {
	 cliente = new Cliente("Mariano", "Moreno", 35265456,"marianomoreno@gmail.com");
	}
	
	@Test
	void verificarDatosCargados() {
		assertEquals(cliente.getNombre(), "Mariano");
		assertEquals(cliente.getApellido(), "Moreno");
		assertEquals(cliente.getDni(), 35265456);
		assertEquals(cliente.getEmail(), "marianomoreno@gmail.com");
	}
	
	@Test
	void verificarCambioDeEmail() {
		assertEquals(cliente.getEmail(), "marianomoreno@gmail.com");
		cliente.setEmail("nuevoEmail@gmail.com");
		assertEquals(cliente.getEmail(), "nuevoEmail@gmail.com");
	}
	
	@Test
    void testGetFacturas_ListaVacia() {
        // Given - Cliente recién creado
        
        // When
        List<Factura> facturas = cliente.getFacturas();
        
        // Then
        assertNotNull(facturas);
        assertTrue(facturas.isEmpty());
    }
	
	@Test
    void testGetFacturas_UnaFactura() {
        // Given
		Factura factura = mock(Factura.class);
        cliente.recibirFactura(factura);
        
        // When
        List<Factura> facturas = cliente.getFacturas();
        
        // Then
        assertEquals(1, facturas.size());
        assertEquals(factura, facturas.get(0));
    }
	
	@Test
    void testFlujoCompletoCliente() {
        // Given
        String notificacion1 = "Buque llegando";
        String notificacion2 = "Buque saliendo";
        Factura factura1 = mock(Factura.class);
        Factura factura2 = mock(Factura.class);
        
        // When - Flujo completo de interacción con el cliente
        cliente.recibirNotificacion(notificacion1);
        cliente.recibirFactura(factura1);
        cliente.recibirNotificacion(notificacion2);
        cliente.recibirFactura(factura2);
        
        List<Factura> facturas = cliente.getFacturas();
        
        // Then - Verificar estado final
        assertEquals(2, facturas.size());
        assertTrue(facturas.contains(factura1));
        assertTrue(facturas.contains(factura2));
        
        // También verificar datos básicos del cliente
        assertEquals("Mariano", cliente.getNombre());
        assertEquals("Moreno", cliente.getApellido());
        assertEquals(35265456, cliente.getDni());
    }

}
