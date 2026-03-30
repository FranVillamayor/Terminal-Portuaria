package terminalPortuaria;

import static org.mockito.Mockito.*;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import buque.Buque;
import buque.GPS;
import circuito.Circuito;
import cliente.Cliente;
import container.Container;
import criterio.Criterio;
import empresaTransportista.Camion;
import empresaTransportista.Chofer;
import empresaTransportista.EmpresaTransportista;
import factura.Factura;
import filtro.Filtro;
import naviera.Naviera;
import orden.OrdenConsignee;
import orden.OrdenShipper;
import servicio.Servicio;
import viaje.Viaje;

class TerminalPortuariaTest {

	TerminalPortuaria terminal;
	
	@BeforeEach
	public void setUp() {
		Point ubicacion = new Point(1,1);
		terminal = new TerminalPortuaria("BsAs",ubicacion);
	}
	
	// ===== TESTS DE REGISTROS =====

    @Test
    public void testRegistrarEmpresaTransportista() {
        // When
    	EmpresaTransportista empresaTransportista1 = mock(EmpresaTransportista.class);
		EmpresaTransportista empresaTransportista2 = mock(EmpresaTransportista.class);
        terminal.registrarEmpresa(empresaTransportista1);
        terminal.registrarEmpresa(empresaTransportista2);

        //La terminal se inicializa sin empresasTransportistas registradas 
        // Then - Verificar que se pueden registrar múltiples empresas
        List<EmpresaTransportista> empresas = terminal.getEmpresasTransportistas();
        assertEquals(2, empresas.size());
        assertTrue(empresas.contains(empresaTransportista1));
        assertTrue(empresas.contains(empresaTransportista2));
    }
    
    @Test
    public void testRegistrarOrdenShipper() {
        // When
    	OrdenShipper ordenShipper1 = mock(OrdenShipper.class);
		OrdenShipper ordenShipper2 = mock(OrdenShipper.class);
        terminal.registrarOrdenShipper(ordenShipper1);
        terminal.registrarOrdenShipper(ordenShipper2);

        //La terminal inicializa sin ordenes 
     // Then
        List<OrdenShipper> ordenes = terminal.getOrdenesShipper();
        assertEquals(2, ordenes.size());
        assertTrue(ordenes.contains(ordenShipper1));
        assertTrue(ordenes.contains(ordenShipper2));
    }
    
    @Test
    public void testRegistrarOrdenConsignee() {
        // When
    	OrdenConsignee ordenConsignee1 = mock(OrdenConsignee.class);
		OrdenConsignee ordenConsignee2 = mock(OrdenConsignee.class);
        terminal.registrarOrdenConsignee(ordenConsignee1);
        terminal.registrarOrdenConsignee(ordenConsignee2);

        //La terminal inicializa sin ordenes 
        /// Then
        List<OrdenConsignee> ordenes = terminal.getOrdenesConsignee();
        assertEquals(2, ordenes.size());
        assertTrue(ordenes.contains(ordenConsignee1));
        assertTrue(ordenes.contains(ordenConsignee2));
    }

    @Test
    public void testRegistrarNaviera() {
        // When
    	Naviera naviera1 = mock(Naviera.class);
	    Naviera naviera2 = mock(Naviera.class);
        terminal.registrarNaviera(naviera1);
        terminal.registrarNaviera(naviera2);

        //La terminal inicializa sin navieras 
        // Then
        List<Naviera> navieras = terminal.getNavieras();
        assertEquals(2, navieras.size());
        assertTrue(navieras.contains(naviera1));
        assertTrue(navieras.contains(naviera2));
    }

    @Test
    public void testRegistrarShipper() {
        // When
    	Cliente shipper1 = mock(Cliente.class);
	    Cliente shipper2 = mock(Cliente.class);
        terminal.registrarCliente(shipper1);
        terminal.registrarCliente(shipper2);

        //La terminal inicializa sin shippers registrados
        // Then
        List<Cliente> clientes = terminal.getClientes();
        assertEquals(2, clientes.size());
        assertTrue(clientes.contains(shipper1));
        assertTrue(clientes.contains(shipper2));
    }
    
    @Test
    public void testRegistrarConsignee() {
        // When
    	Cliente consignee1 = mock(Cliente.class);
	    Cliente consignee2 = mock(Cliente.class);
        terminal.registrarCliente(consignee1);
        terminal.registrarCliente(consignee2);

        //La terminal inicializa sin consignees registrados
        // Then
        List<Cliente> clientes = terminal.getClientes();
        assertEquals(2, clientes.size());
        assertTrue(clientes.contains(consignee1));
        assertTrue(clientes.contains(consignee2));
    }
    
    @Test
    public void testMejorCircuito() {
        // Given
    	TerminalPortuaria destino = mock(TerminalPortuaria.class);
    	Naviera naviera1 = mock(Naviera.class);
	    Naviera naviera2 = mock(Naviera.class);
    	Circuito circuito1 = mock(Circuito.class);
    	Criterio criterio = mock(Criterio.class);
        terminal.setCriterio(criterio);
        List<Naviera> navieras = Arrays.asList(naviera1, naviera2);
        
        when(criterio.mejorCircuito(navieras, terminal, destino))
            .thenReturn(circuito1);

        // When
        Circuito resultado = terminal.mejorCircuito(destino, navieras);

        // Then
        assertEquals(circuito1, resultado);
        verify(criterio, times(1)).mejorCircuito(navieras, terminal, destino);
    }
    
    @Test 
    public void testBuscarRutasConViajes() {
    	Naviera naviera1 = mock(Naviera.class);
    	Naviera naviera2 = mock(Naviera.class);
    	Filtro filtro = mock(Filtro.class);
    	Viaje viaje1 = mock(Viaje.class);
    	Viaje viaje2 = mock(Viaje.class);
    	Viaje viaje3 = mock(Viaje.class);
    	
    	terminal.registrarNaviera(naviera1);
    	terminal.registrarNaviera(naviera2);
    	
    	List<Viaje> viajesNaviera1 = Arrays.asList(viaje1, viaje2);
        List<Viaje> viajesNaviera2 = Arrays.asList(viaje3);
        List<Viaje> viajesFiltrados = Arrays.asList(viaje1, viaje3);
    	
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(viajesNaviera1);
        when(naviera2.getViajesDeTerminal(terminal)).thenReturn(viajesNaviera2);
        when(filtro.filtrar(anyList())).thenReturn(viajesFiltrados);

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertNotNull("La lista de viajes no debería ser nula", resultado);
        assertEquals("Debería retornar 2 viajes filtrados", 2, resultado.size());
        assertTrue("Debería contener viaje1", resultado.contains(viaje1));
        assertTrue("Debería contener viaje3", resultado.contains(viaje3));
        assertFalse("No debería contener viaje2 (filtrado)", resultado.contains(viaje2));
        
        verify(naviera1, times(1)).getViajesDeTerminal(terminal);
        verify(naviera2, times(1)).getViajesDeTerminal(terminal);
        verify(filtro, times(1)).filtrar(anyList());
    }
    
    @Test
    public void testBuscarRutasConFiltroQueRetornaTodos() {
        // Given
    	Viaje viaje1 = mock(Viaje.class);
	    Viaje viaje2 = mock(Viaje.class);
    	Naviera naviera1 = mock(Naviera.class);
    	Filtro filtro = mock(Filtro.class);
        terminal.registrarNaviera(naviera1);
        List<Viaje> viajesNaviera1 = Arrays.asList(viaje1, viaje2);
        List<Viaje> viajesFiltrados = Arrays.asList(viaje1, viaje2);
        
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(viajesNaviera1);
        when(filtro.filtrar(viajesNaviera1)).thenReturn(viajesFiltrados);

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertEquals("Debería retornar todos los viajes", 2, resultado.size());
        verify(filtro, times(1)).filtrar(viajesNaviera1);
    }
    
    @Test
    public void testBuscarRutasSinNavieras() {
        // Given
    	Filtro filtro = mock(Filtro.class);
        List<Viaje> viajesFiltrados = new ArrayList<>();
        when(filtro.filtrar(anyList())).thenReturn(viajesFiltrados);

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertNotNull("La lista no debería ser nula", resultado);
        assertTrue("La lista debería estar vacía", resultado.isEmpty());
        verify(filtro, times(1)).filtrar(anyList());
    }

    
    @Test
    public void testSetCriterio() {
        // Given
        Criterio nuevoCriterio = mock(Criterio.class);

        // When
        terminal.setCriterio(nuevoCriterio);

        // Then - No hay getter, pero verifica que no lanza excepción
        assertTrue("El método setCriterio debería ejecutarse correctamente", true);
    }
    
    // ===== TESTS DE COMPORTAMIENTO CON LISTAS VACÍAS =====
    @Test
    public void testBuscarRutasConNavierasSinViajes() {
        // Given
    	Naviera naviera1 = mock(Naviera.class);
    	Naviera naviera2 = mock(Naviera.class);
    	Filtro filtro = mock(Filtro.class);
        terminal.registrarNaviera(naviera1);
        terminal.registrarNaviera(naviera2);
        
        
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(new ArrayList<>());
        when(naviera2.getViajesDeTerminal(terminal)).thenReturn(new ArrayList<>());
        when(filtro.filtrar(anyList())).thenReturn(new ArrayList<>());

        // When
        List<Viaje> resultado = terminal.buscarRutas(filtro);

        // Then
        assertNotNull("La lista no debería ser nula", resultado);
        assertTrue("La lista debería estar vacía", resultado.isEmpty());
    }
    
    // ===== TESTS DE ENTREGA DE CARGA POR CAMIONES =====

    @Test
    public void testEntregaDeCamionAprobado() {
        // Given
    	Camion camion = mock(Camion.class);
    	Chofer chofer = mock(Chofer.class);
    	OrdenShipper ordenShipper1 = mock(OrdenShipper.class);
    	EmpresaTransportista empresaTransportista1 = mock(EmpresaTransportista.class);
    	terminal.registrarEmpresa(empresaTransportista1);
        
        when(ordenShipper1.getTurno()).thenReturn(LocalDateTime.now().plusHours(2));
        when(ordenShipper1.getCamionAsignado()).thenReturn(camion);
        when(ordenShipper1.choferAsignado()).thenReturn(chofer);
        when(empresaTransportista1.estaRegistradoElCamion(camion)).thenReturn(true);
        when(empresaTransportista1.estaRegistradoElChofer(chofer)).thenReturn(true);

        // When
        String resultado = terminal.verificarEntradaDeCamion(ordenShipper1);

        // Then
        assertEquals("Aprobado", resultado);
    }
    
    @Test
    public void testEntregaDeCamionRechazadoPorHorario() {
        // Given
    	Camion camion = mock(Camion.class);
    	Chofer chofer = mock(Chofer.class);
    	OrdenShipper ordenShipper1 = mock(OrdenShipper.class);
    	EmpresaTransportista empresaTransportista1 = mock(EmpresaTransportista.class);
        terminal.registrarEmpresa(empresaTransportista1);
        
        when(ordenShipper1.getTurno()).thenReturn(LocalDateTime.now().plusHours(4)); // Más de 3 horas
        when(ordenShipper1.getCamionAsignado()).thenReturn(camion);
        when(ordenShipper1.choferAsignado()).thenReturn(chofer);
        when(empresaTransportista1.estaRegistradoElCamion(camion)).thenReturn(true);
        when(empresaTransportista1.estaRegistradoElChofer(chofer)).thenReturn(true);

        // When
        String resultado = terminal.verificarEntradaDeCamion(ordenShipper1);

        // Then
        assertEquals("Rechazado", resultado);
    }
    
    @Test
    public void testEntregaDeCamionRechazadoPorCamion() {
        // Given
    	Camion camion = mock(Camion.class);
    	Chofer chofer = mock(Chofer.class);
    	OrdenShipper ordenShipper1 = mock(OrdenShipper.class);
    	EmpresaTransportista empresaTransportista1 = mock(EmpresaTransportista.class);
        terminal.registrarEmpresa(empresaTransportista1);
        
        when(ordenShipper1.getTurno()).thenReturn(LocalDateTime.now().plusHours(2));
        when(ordenShipper1.getCamionAsignado()).thenReturn(camion);
        when(ordenShipper1.choferAsignado()).thenReturn(chofer);
        when(empresaTransportista1.estaRegistradoElCamion(camion)).thenReturn(false); // Camion no registrado
        when(empresaTransportista1.estaRegistradoElChofer(chofer)).thenReturn(true);

        // When
        String resultado = terminal.verificarEntradaDeCamion(ordenShipper1);

        // Then
        assertEquals("Rechazado", resultado);
    }
    
    // ===== TESTS DE VERIFICACIONES =====
    
    @Test
    void testEstaRegistrado_ClienteRegistrado() {
        // Given
    	Cliente shipperA = mock(Cliente.class);
        terminal.registrarCliente(shipperA); // 
        
        // When
        boolean resultado = terminal.estaRegistrado(shipperA);

        // Then
        assertTrue(resultado);
    }
    
    @Test
    void testEstaRegistrado_ClienteNoRegistrado() {
        // Given 
    	Cliente shipperA = mock(Cliente.class);
        
        // When
        boolean resultado = terminal.estaRegistrado(shipperA);

        // Then
        assertFalse(resultado);
    }

    @Test
    void testEstaRegistradoCamion_CamionRegistrado() {
        // Given
    	EmpresaTransportista empT = mock(EmpresaTransportista.class);
    	Camion camion = mock(Camion.class);
        terminal.registrarEmpresa(empT);
        when(empT.estaRegistradoElCamion(camion)).thenReturn(true);

        // When
        boolean resultado = terminal.estaRegistradoCamion(camion);

        // Then
        assertTrue(resultado);
        verify(empT).estaRegistradoElCamion(camion);
    }
    
    @Test
    void testExisteViaje_ViajeExiste() {
        // Given
    	Naviera naviera = mock(Naviera.class);
    	Viaje viaje1 = mock(Viaje.class);
    	Viaje viaje2 = mock(Viaje.class);
        terminal.registrarNaviera(naviera);
        when(naviera.getViajesDeTerminal(terminal)).thenReturn(Arrays.asList(viaje1, viaje2));
        

        // When
        boolean resultado = terminal.existeViaje(viaje1);

        // Then
        assertTrue(resultado);
    }
    
    // ===== TESTS DE EXPORTACION E IMPORTACION =====

    @Test
    public void testExportacion() {
        // Given
    	Servicio servicio1 = mock(Servicio.class);
    	Servicio servicio2 = mock(Servicio.class);
    	Camion camion = mock(Camion.class);
    	Viaje viaje1 = mock(Viaje.class);
    	Container container = mock(Container.class);
    	Cliente shipper1 = mock(Cliente.class);
    	LocalDateTime turno = mock(LocalDateTime.class);
        ArrayList<Servicio> servicios = new ArrayList<>(Arrays.asList(servicio1, servicio2));
        EmpresaTransportista empT = mock(EmpresaTransportista.class);
        Naviera naviera = mock(Naviera.class);
        List<Viaje> viajes = new ArrayList<>();
        
        // When
        viajes.add(viaje1);
        terminal.registrarNaviera(naviera);
        terminal.registrarCliente(shipper1);
        empT.registrarCamion(camion);
        terminal.registrarEmpresa(empT);
        when(empT.estaRegistradoElCamion(camion)).thenReturn(true);
        when(naviera.getViajesDeTerminal(terminal)).thenReturn(viajes);
        terminal.exportacion(shipper1,camion,viaje1,container,servicios,turno);

        // Then
        List<OrdenShipper> ordenes = terminal.getOrdenesShipper();
        assertEquals(1, ordenes.size());
    }
    
    @Test
    public void testImportacion() {
        // Given
    	Servicio servicio1 = mock(Servicio.class);
    	Servicio servicio2 = mock(Servicio.class);
    	Camion camion = mock(Camion.class);
    	Viaje viaje1 = mock(Viaje.class);
    	Container container = mock(Container.class);
    	Cliente consignee1 = mock(Cliente.class);
        ArrayList<Servicio> servicios = new ArrayList<>(Arrays.asList(servicio1, servicio2));
        EmpresaTransportista empT = mock(EmpresaTransportista.class);
        Naviera naviera = mock(Naviera.class);
        List<Viaje> viajes = new ArrayList<>();
        
        // When
        viajes.add(viaje1);
        terminal.registrarNaviera(naviera);
        terminal.registrarCliente(consignee1);
        empT.registrarCamion(camion);
        terminal.registrarEmpresa(empT);
        when(empT.estaRegistradoElCamion(camion)).thenReturn(true);
        when(naviera.getViajesDeTerminal(terminal)).thenReturn(viajes);
        terminal.importacion(consignee1, camion, viaje1, container, servicios);

        // Then
        List<OrdenConsignee> ordenes = terminal.getOrdenesConsignee();
        assertEquals(1, ordenes.size());
    }
    
    // ===== TESTS DE NOTIFICACIONES =====
    
    @Test
    public void testNotificarConsignees() {
        // Given
    	OrdenConsignee ordenConsignee1 = mock(OrdenConsignee.class);
    	Cliente consignee = mock(Cliente.class);
    	terminal.registrarOrdenConsignee(ordenConsignee1);
    	String email = "Asunto: Buque ingresando a terminal";
        
        // Crear Buque 
        Buque buque = new Buque("Buque Test", terminal, (GPS) mock(GPS.class));
        
        when(ordenConsignee1.getNombreBuque()).thenReturn("Buque Test");
        when(ordenConsignee1.getCliente()).thenReturn(consignee);
        
        // When
        terminal.notificarConsignees(buque);

        // Then
        verify(consignee).recibirNotificacion(email);
    }


    @Test
    public void testNotificarShippers() {
        // Given
    	OrdenShipper ordenShipper1 = mock(OrdenShipper.class);
    	OrdenShipper ordenShipper2 = mock(OrdenShipper.class);
    	Cliente shipper1 = mock(Cliente.class);
    	Cliente shipper2 = mock(Cliente.class);
        terminal.registrarOrdenShipper(ordenShipper1);
        terminal.registrarOrdenShipper(ordenShipper2);
        String email = "Asunto: Buque saliendo de la terminal";
        
        // Crear Buque 
        Buque buque = new Buque("Buque Test", terminal, (GPS) mock(GPS.class));
        
        when(ordenShipper1.getNombreBuque()).thenReturn("Buque Test");
        when(ordenShipper2.getNombreBuque()).thenReturn("Otro Buque"); // No debe notificar
        when(ordenShipper1.getCliente()).thenReturn(shipper1);

        // When
        terminal.notificarShippers(buque);

        // Then
        verify(shipper1).recibirNotificacion(email);
        verify(shipper2, never()).recibirNotificacion(email);
    }
    
    // ===== TESTS ENVIO DE FACTURAS =====
    

    @Test
    public void testEnviarFacturas() {
        // Given
    	OrdenShipper ordenShipper = mock(OrdenShipper.class);
    	OrdenConsignee ordenConsignee = mock(OrdenConsignee.class);
    	Buque buque = mock(Buque.class);
    	Factura factura = mock(Factura.class);
        terminal.registrarOrdenShipper(ordenShipper);
        terminal.registrarOrdenConsignee(ordenConsignee);
        Cliente shipper1 = mock(Cliente.class);
    	Cliente consignee1 = mock(Cliente.class);
        
        when(buque.getNombre()).thenReturn("Buque Test");
        when(ordenShipper.getNombreBuque()).thenReturn("Buque Test");
        when(ordenConsignee.getNombreBuque()).thenReturn("Buque Test");
        when(ordenShipper.getCliente()).thenReturn(shipper1);
        when(ordenConsignee.getCliente()).thenReturn(consignee1);
        when(ordenShipper.facturar()).thenReturn(factura);
        when(ordenConsignee.facturar()).thenReturn(factura);

        // When
        terminal.enviarFacturas(buque);

        // Then
        verify(shipper1).recibirFactura(factura);
        verify(consignee1).recibirFactura(factura);
    }
    
    
    // ===== TESTS DE REGISTRO DE CIRCUITOS =====

    @Test
    public void testRegistrarCircuitos() {
        // Given
    	Viaje viaje1 = mock(Viaje.class);
    	Viaje viaje2 = mock(Viaje.class);
    	Naviera naviera1 = mock(Naviera.class);
    	Circuito circuito1 = mock(Circuito.class);
    	Circuito circuito2 = mock(Circuito.class);
        terminal.registrarNaviera(naviera1);
        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);
        
        when(naviera1.getViajesDeTerminal(terminal)).thenReturn(viajes);
        when(viaje1.getCircuito()).thenReturn(circuito1);
        when(viaje2.getCircuito()).thenReturn(circuito2);

        // When
        terminal.registrarCircuitos();

     // Then - Verificar que se registraron los circuitos correctamente
        Set<Circuito> circuitosRegistrados = terminal.getCircuitos();
        
        assertNotNull("La lista de circuitos no debería ser nula", circuitosRegistrados);
        assertEquals("Debería tener 2 circuitos registrados", 2, circuitosRegistrados.size());
        assertTrue("Debería contener el circuito", circuitosRegistrados.contains(circuito1));
        assertTrue("Debería contener el circuito", circuitosRegistrados.contains(circuito2));
        
        // Verificar que no hay duplicados si se llama múltiples veces
        terminal.registrarCircuitos();
        assertEquals("No debería duplicar circuitos existentes", 2, terminal.getCircuitos().size());
    }
    
    // ===== TESTS DE TIEMPO HASTA =====

    @Test
    public void testTiempoHasta() {
        // Given
    	Naviera naviera1 = mock(Naviera.class);
    	TerminalPortuaria destino = mock(TerminalPortuaria.class);
        int tiempo1 = 5;
        int tiempo2 = 6;
        List<Integer> tiemposEsperados = Arrays.asList(tiempo1, tiempo2);
        when(naviera1.tiempoDesdeHasta(terminal, destino)).thenReturn(tiemposEsperados);

        // When
        List<Integer> resultado = terminal.tiempoHasta(naviera1, destino);

        // Then
        assertEquals(tiemposEsperados, resultado);
        verify(naviera1).tiempoDesdeHasta(terminal, destino);
    }
    
    // ===== TESTS DE PRÓXIMA FECHA HACIA =====

    @Test
    public void testProximaFechaHacia() {
        // Given
    	Naviera naviera1 = mock(Naviera.class);
    	Naviera naviera2 = mock(Naviera.class);
    	TerminalPortuaria destino = mock(TerminalPortuaria.class);
        terminal.registrarNaviera(naviera1);
        terminal.registrarNaviera(naviera2);
        
        LocalDateTime fecha1 = LocalDateTime.now().plusDays(2);
        LocalDateTime fecha2 = LocalDateTime.now().plusDays(1); // Más próxima
        
        when(naviera1.proximaFechaHacia(destino)).thenReturn(Optional.of(fecha1));
        when(naviera2.proximaFechaHacia(destino)).thenReturn(Optional.of(fecha2));

        // When
        Optional<LocalDateTime> resultado = terminal.proximaFechaHacia(destino);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals(fecha2, resultado.get());
    }
    
    @Test
    public void testProximaFechaHaciaSinFechas() {
        // Given
    	Naviera naviera1 = mock(Naviera.class);
    	TerminalPortuaria destino = mock(TerminalPortuaria.class);
        terminal.registrarNaviera(naviera1);
        when(naviera1.proximaFechaHacia(destino)).thenReturn(Optional.empty());

        // When
        Optional<LocalDateTime> resultado = terminal.proximaFechaHacia(destino);

        // Then
        assertFalse(resultado.isPresent());
    }
    
    // ===== TESTS DE TRABAJOS DE BUQUE =====
    @Test
    public void testIniciarTrabajos() {
        // Given - Crear Buque REAL
        GPS gps = mock(GPS.class);
        Buque buque = new Buque("Buque Test", terminal, gps);
        
        // When
        terminal.iniciarTrabajos(buque);
        
        // Then - Solo verificar que no hay excepciones
        assertTrue("El método iniciarTrabajos debería ejecutarse sin errores", true);
    }

    @Test
    public void testFinalizarTrabajos() {
        // Given - Crear Buque REAL
        GPS gps = mock(GPS.class);
        Buque buque = new Buque("Buque Test", terminal, gps);
        
        // When
        terminal.finalizarTrabajos(buque);
        
        // Then - Solo verificar que no hay excepciones
        assertTrue("El método finalizarTrabajos debería ejecutarse sin errores", true);
    }

    
 
}
