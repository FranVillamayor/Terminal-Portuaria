package factura;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import container.Container;
import orden.Orden;
import servicio.Lavado;
import servicio.Pesado;
import servicio.Servicio;
import viaje.Viaje;

class FacturaTest {

    private Orden orden;
    private Viaje viaje;
    private Lavado servicioLavado;
    private Pesado servicioPesado;
    private Factura factura;
    private ArrayList<Servicio> servicios;

    @BeforeEach
    public void setUp() {
        // Mock de orden y viaje
        orden = mock(Orden.class);
        viaje = mock(Viaje.class);

        // Crear servicios reales
        servicioLavado = new Lavado("Lavado", LocalDate.of(2025, 11, 5), 1500, 0, 0);
        servicioPesado = new Pesado("Pesado", LocalDate.of(2025, 11, 5), 800);

        // Configurar comportamiento de viaje y orden
        when(viaje.precioFinal()).thenReturn(10000.0);
        when(orden.montoTotalServicios()).thenReturn(2300.0);
        when(orden.getViaje()).thenReturn(viaje);

        // Crear lista de servicios
        servicios = new ArrayList<>();
        servicios.add(servicioLavado);
        servicios.add(servicioPesado);

        when(orden.getServicios()).thenReturn(servicios);

        // Mock de container
        Container container = mock(Container.class);
        when(container.volumen()).thenReturn(50);
        when(orden.getContainer()).thenReturn(container);

        // Crear factura pasando servicios + orden
        factura = new Factura(servicios, orden);
    }

    @Test
    public void testFacturaContieneTodosLosConceptos() {
        Set<ConceptoServicio> conceptos = factura.getConceptos();

        // 2 servicios + subtotal + viaje + total = 5 conceptos
        assertEquals(5, conceptos.size(),
                "La factura debería tener 5 conceptos (2 servicios + subtotal + viaje + total)");
    }

    @Test
    public void testMontoTotalCalculadoCorrectamente() {
        double totalEsperado = 2300.0 + 10000.0; // servicios + viaje

        ConceptoServicio totalConcepto = factura.getConceptos()
                .stream()
                .filter(c -> c.getNombreServicio().equals("TOTAL A PAGAR"))
                .findFirst()
                .orElse(null);

        assertNotNull(totalConcepto, "Debe existir un concepto 'TOTAL A PAGAR'");
        assertEquals(totalEsperado, totalConcepto.getMonto(), 0.01,
                "El total facturado debe ser la suma de los servicios y el viaje");
    }

    @Test
    public void testFacturaSinViajeSoloServicios() {
        when(orden.getViaje()).thenReturn(null);

        Factura facturaSoloServicios = new Factura(servicios, orden);

        // 2 servicios + subtotal + total = 4 conceptos
        assertEquals(4, facturaSoloServicios.getConceptos().size(),
                "Factura sin viaje debería tener 4 conceptos");
    }

    @Test
    public void testImpresionDesgloseNoEsVacia() {
        String salida = factura.desgloseDeServicios();
        assertNotNull(salida);
        assertTrue(salida.contains("FACTURA"));
        assertTrue(salida.contains("TOTAL A PAGAR"));
        assertTrue(salida.contains("Lavado"));
        assertTrue(salida.contains("Pesado"));
    }

    @Test
    public void testFacturaConsultaMetodosDeOrden() {
        verify(orden, atLeastOnce()).getServicios();
        verify(orden, atLeastOnce()).getViaje();
        verify(orden, atLeastOnce()).montoTotalServicios();
    }
}
