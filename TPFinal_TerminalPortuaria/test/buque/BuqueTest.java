package buque;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import container.Container;

import java.awt.Point;
import java.util.List;

import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;
import visitor.ReportVisitor;

public class BuqueTest {
    Buque buque;
    GPS gps;
    TerminalPortuaria terminalGestionada;
    Viaje viaje;
    Container container;
    ReportVisitor visitor;
    
    @BeforeEach
    public void setUp() {
        terminalGestionada = mock(TerminalPortuaria.class);
        gps = mock(GPS.class);
        viaje = mock(Viaje.class); 
        container = mock(Container.class);
        visitor = mock(ReportVisitor.class);

        buque = new Buque("El Perla", terminalGestionada, gps);
        
        when(terminalGestionada.getPosicion()).thenReturn(new Point(0, 0));
        when(gps.enviarPosicion()).thenReturn(new Point(0, 0));
        when(gps.hayProblemasClimaticos()).thenReturn(false);
        
        buque.setViaje(viaje);
    }

    @Test
    public void buqueLejosDeTerminalTest() {
        when(gps.enviarPosicion()).thenReturn(new Point(60, 0));
        
        buque.evaluarPosicion();
        
        // No hacen nada porque no está en la terminal
        buque.iniciarTrabajos();
        buque.finalizarTrabajos();
        buque.depart();
        
        assertTrue(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueLlegandoATerminalTest() {
    	when(gps.enviarPosicion()).thenReturn(new Point(40, 0));
    	
        buque.evaluarPosicion();
        
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }

    @Test
    public void buqueArribaEnTerminal() {
        buque.evaluarPosicion();
        buque.evaluarPosicion();
        
        verify(terminalGestionada).notificarConsignees(buque);
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }

    @Test
    public void buqueNoArribaPorProblemasClimaticosTest() {
    	when(gps.hayProblemasClimaticos()).thenReturn(true);
    	
    	buque.evaluarPosicion();
    	buque.evaluarPosicion();
        
        verify(terminalGestionada, times(0)).notificarConsignees(buque);  
        assertTrue(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }

    @Test
    public void buqueRealizaTrabajosTest() {	
    	buque.evaluarPosicion();
        buque.evaluarPosicion();
        
        buque.iniciarTrabajos();
        
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueTerminaTrabajosTest() {   	
    	buque.evaluarPosicion();
        buque.evaluarPosicion();
        
        buque.iniciarTrabajos();
        
        // No hace nada porque ya está en la terminal.
        buque.evaluarPosicion();
        
        buque.finalizarTrabajos();

        verify(terminalGestionada).finalizarTrabajos(buque);
        assertFalse(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueSeRetiraDeTerminalTest() {
    	buque.evaluarPosicion();
        buque.evaluarPosicion();
        
        buque.iniciarTrabajos();
        buque.finalizarTrabajos();
    	
        when(gps.enviarPosicion()).thenReturn(new Point(2, 0));
        
        buque.depart();
        
        assertTrue(buque.puedePagarServicios());
        assertFalse(buque.puedeRegistrar());
        
        buque.evaluarPosicion();

        verify(terminalGestionada).notificarShippers(buque);
        assertTrue(buque.puedeRegistrar());
        assertFalse(buque.puedePagarServicios());
    }
    
    @Test
    public void buqueDatosVisitorTest() {
    	buque.agregarContainer(container);
    	buque.accept(visitor);
    	
    	verify(visitor).visit(buque);
    	assertEquals("El Perla", buque.getNombre());
    	assertEquals(List.of(container), buque.getContainers());
    	assertEquals(viaje, buque.getViaje());
    	assertEquals(1, buque.getContenedoresOperados());
    }
    
    @Test
    public void agregarYRemoverContainersTest() {
    	when(container.getId()).thenReturn("ABC123");
    	buque.agregarContainer(container);
    	
    	assertEquals(List.of(container.getId()), buque.getIdentificadoresCargados());
    	assertEquals(List.of(), buque.getIdentificadoresDescargados());
    	
    	buque.removerContainer(container);
    	assertEquals(List.of(), buque.getIdentificadoresCargados());
    	assertEquals(List.of(container.getId()), buque.getIdentificadoresDescargados());
    }
}