package visitor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import buque.Buque;
import container.Container;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

import java.time.LocalDateTime;

class VisitorTest {

    Buque buque;
    Viaje viajeMock;
    Container container1;
    Container container2;
    TerminalPortuaria terminalMock;

    @BeforeEach
    void setUp() {
        // Creamos mocks
        viajeMock = mock(Viaje.class);
        container1 = mock(Container.class);
        container2 = mock(Container.class);
        terminalMock = mock(TerminalPortuaria.class);

        // Configuramos el viaje
        when(viajeMock.fechaLlegadaTerminalGestionada())
                .thenReturn(LocalDateTime.of(2025, 11, 10, 8, 0));
        when(viajeMock.fechaSalidaTerminalGestionada())
                .thenReturn(LocalDateTime.of(2025, 11, 10, 18, 0));

        // Creamos el buque real pero le inyectamos el viaje mockeado
        buque = new Buque("Evergreen", terminalMock, null);
        buque.setViaje(viajeMock);

        // Configuramos los contenedores
        when(container1.getTipo()).thenReturn("Dry");
        when(container1.getId()).thenReturn("RODR1234567");

        when(container2.getTipo()).thenReturn("Reefer");
        when(container2.getId()).thenReturn("FRAN7654321");

        // Agregamos los dos containers al buque
        buque.agregarContainer(container1);
        buque.agregarContainer(container2);

    }

    @Test
    void testMuelleReportVisitorGeneraReporteCorrecto() {
        MuelleReportVisitor visitor = new MuelleReportVisitor();

        buque.accept(visitor);

        String reporte = visitor.generarReporte();

        assertTrue(reporte.contains("Buque: Evergreen"));
        assertTrue(reporte.contains("Arribo: 2025-11-10T08:00"));
        assertTrue(reporte.contains("Partida: 2025-11-10T18:00"));
        assertTrue(reporte.contains("Containers operados: 2"));
    }

    @Test
    void testAduanaReportVisitorGeneraHtmlCorrectoConDosContainers() {
        AduanaReportVisitor visitor = new AduanaReportVisitor();

        buque.accept(visitor); // solo el buque, no la orden

        String html = visitor.generarHTML();

        // 🔍 Verificaciones del HTML
        assertTrue(html.contains("<h1>Reporte Aduana</h1>"));
        assertTrue(html.contains("Buque: Evergreen"));
        assertTrue(html.contains("Arribo: 2025-11-10T08:00"));
        assertTrue(html.contains("Partida: 2025-11-10T18:00"));

        // Verificamos ambos containers
        assertTrue(html.contains("<li>Dry - RODR1234567</li>"));
        assertTrue(html.contains("<li>Reefer - FRAN7654321</li>"));

        assertTrue(html.endsWith("</ul>"));
    }

    @Test
    void testQueBuqueLlamaAlVisitorCorrecto() {
        ReportVisitor visitorMock = mock(ReportVisitor.class);

        buque.accept(visitorMock);

        verify(visitorMock, times(1)).visit(buque);
        
    }
    @Test
    void testBuqueReportVisitorGeneraXMLCorrecto() {
        // Creamos el visitor
        BuqueReportVisitor visitor = new BuqueReportVisitor();

        // Simulamos actividad del buque
        // Carga inicial (2 containers)
        assertEquals(2, buque.getContainers().size());

        // Descargamos uno (simula descarga de importación)
        buque.removerContainer(container1);

        // Visitamos el buque
        buque.accept(visitor);

        String xml = visitor.generarXML();

        // 🔍 Verificamos estructura XML
        assertTrue(xml.contains("<report>"));
        assertTrue(xml.contains("<import>"));
        assertTrue(xml.contains("<export>"));
        
        // Debe aparecer el descargado (container1)
        assertTrue(xml.contains("<item>RODR1234567</item>"));
        // Debe aparecer el cargado restante (container2)
        assertTrue(xml.contains("<item>FRAN7654321</item>"));

        assertTrue(xml.endsWith("</report>"));
    }


 
}
