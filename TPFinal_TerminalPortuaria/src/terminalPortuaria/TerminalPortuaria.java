package terminalPortuaria;

import java.awt.Point;

import buque.Buque;
import java.lang.reflect.AccessFlag.Location;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import circuito.Circuito;
import cliente.Cliente;
import container.Container;
import criterio.Criterio;
import empresaTransportista.Camion;
import empresaTransportista.EmpresaTransportista;
import factura.Factura;
import filtro.Filtro;
import naviera.Naviera;
import orden.Orden;
import orden.OrdenConsignee;
import orden.OrdenShipper;
import servicio.Servicio;
import viaje.Viaje;

public class TerminalPortuaria {

	private String nombre;
	private Point ubicacion;
	private Criterio criterio;
	private List<EmpresaTransportista> empresasTransportistas = new ArrayList<EmpresaTransportista>();
	private List<OrdenConsignee> ordenesImportacion = new ArrayList<OrdenConsignee>();
	private List<OrdenShipper> ordenesExportacion = new ArrayList<OrdenShipper>();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private List<Naviera> navieras = new ArrayList<Naviera>();
	private Set<Circuito> circuitos = new HashSet<Circuito>();
	
	public TerminalPortuaria(String nombre, Point ubicacion) {
		this.nombre = nombre;
		this.ubicacion = ubicacion;
	}
	
	
	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	
	public void registrarEmpresa(EmpresaTransportista empresa) {
		this.empresasTransportistas.add(empresa);
	}
	
	public List<EmpresaTransportista> getEmpresasTransportistas() {
		return this.empresasTransportistas;
	}
	
	public void registrarOrdenShipper(OrdenShipper orden) {
		this.ordenesExportacion.add(orden);
	}
	
	public List<OrdenShipper> getOrdenesShipper(){
		return this.ordenesExportacion;
	}
	
	public void registrarOrdenConsignee(OrdenConsignee orden) {
		this.ordenesImportacion.add(orden);
	}
	
	public List<OrdenConsignee> getOrdenesConsignee(){
		return this.ordenesImportacion;
	}
	
	public void registrarNaviera(Naviera naviera) {
		this.navieras.add(naviera);
	}
	
	public List<Naviera> getNavieras(){
		return this.navieras;
	}
	
	public void registrarCliente(Cliente shipper) {
		this.clientes.add(shipper);
	}
	
	public List<Cliente> getClientes(){
		return this.clientes;
	}
	
	//Dependendiendo del criterio retorna el mejor circuito para llegar a ese destino.
	public Circuito mejorCircuito(TerminalPortuaria this, TerminalPortuaria destino, List<Naviera> navieras) {
		return this.criterio.mejorCircuito(navieras, this, destino);
	}
	
	public List<Viaje> buscarRutas(Filtro filtro){
		List<Viaje> viajes = this.getViajes();
		return filtro.filtrar(viajes);
	}

	List<Viaje> getViajes() {
		return this.navieras.stream()
				.flatMap(n->n.getViajesDeTerminal(this).stream())
				.collect(Collectors.toList());
	}
	
	public String verificarEntradaDeCamion(OrdenShipper orden) { //Entrega Terrestre de Carga
		if(this.verificarHorario(orden) && this.verificarCamion(orden) && this.verificarChofer(orden)) {
			return "Aprobado";
		}
		else {
			return "Rechazado";
		}
	}
	
	private boolean verificarChofer(OrdenShipper orden) {
		return empresasTransportistas.stream().anyMatch(e -> e.estaRegistradoElChofer(orden.choferAsignado()));
	}

	private boolean verificarCamion(Orden orden) {
		return this.estaRegistradoCamion(orden.getCamionAsignado());
	}

	private boolean verificarHorario(OrdenShipper orden) {
		LocalDateTime horaActual = LocalDateTime.now();
		return orden.getTurno().isBefore(horaActual.plusHours(3)); //Devuelve true cuando no se paso de las 3 horas
	}
	
	public void exportacion(Cliente shipper, Camion camion, Viaje viaje,
			Container container, ArrayList<Servicio> servicios, LocalDateTime turno) {
		
		if(this.verificarRegistros(shipper,camion,viaje)) {
			OrdenShipper orden = new OrdenShipper(container,camion,viaje,shipper,turno,servicios);
			this.registrarOrdenShipper(orden);
		}
	}
	
	public void importacion(Cliente consignee, Camion camion, Viaje viaje,
			Container container, ArrayList<Servicio> servicios) {
		
		if(this.verificarRegistros(consignee,camion,viaje)) {
			OrdenConsignee orden = new OrdenConsignee(consignee,camion,viaje,container,servicios);
			this.registrarOrdenConsignee(orden);
		}
	}

	private boolean verificarRegistros(Cliente cliente, Camion camion, Viaje viaje) {
		return this.estaRegistrado(cliente) 
			&& this.estaRegistradoCamion(camion)
			&& this.existeViaje(viaje);
	}


	public boolean existeViaje(Viaje viaje) {
		return this.getViajes().stream().anyMatch(v -> v.equals(viaje));
	}

	public boolean estaRegistradoCamion(Camion camion) {
		return empresasTransportistas.stream().anyMatch(e -> e.estaRegistradoElCamion(camion));
	}

	public boolean estaRegistrado(Cliente cliente) {
		return this.getClientes().contains(cliente);
	}

	public void notificarConsignees(Buque b) {
		String email = "Asunto: Buque ingresando a terminal";
		List<OrdenConsignee> ordenesFiltradas = this.ordenesConsigneeFiltradasPorBuque(b);
		for (OrdenConsignee orden : ordenesFiltradas) {
			Cliente consignee = orden.getCliente();
			consignee.recibirNotificacion(email);
		}
	}
	
	public List<OrdenConsignee> ordenesConsigneeFiltradasPorBuque(Buque b){
		return this.getOrdenesConsignee()
                .stream()
                .filter(orden -> orden.getNombreBuque().equals(b.getNombre()))
                .collect(Collectors.toList());
	}

	public void notificarShippers(Buque b) {
		String email = "Asunto: Buque saliendo de la terminal";
		List<OrdenShipper> ordenesFiltradas = this.ordenesShipperFiltradasPorBuque(b);
		for (OrdenShipper orden : ordenesFiltradas) {
			Cliente shipper = orden.getCliente();
			shipper.recibirNotificacion(email);
		}
	}
	
	public List<OrdenShipper> ordenesShipperFiltradasPorBuque(Buque b){
		return this.getOrdenesShipper()
                .stream()
                .filter(orden -> orden.getNombreBuque().equals(b.getNombre()))
                .collect(Collectors.toList());
	}
	
	
	
	public void enviarFacturas(Buque b) {
		List<OrdenConsignee> ordenesConsignee = this.ordenesConsigneeFiltradasPorBuque(b);
		List<OrdenShipper> ordenesShipper = this.ordenesShipperFiltradasPorBuque(b);
		
		List<Orden> ordenes = new ArrayList<>();
		
		ordenes.addAll(ordenesConsignee);
		ordenes.addAll(ordenesShipper);
				
		for (Orden orden : ordenes) {
			Cliente cliente = orden.getCliente();
			Factura facturaCliente = orden.facturar();
			cliente.recibirFactura(facturaCliente);
		}
	}
	
	public void registrarCircuitos() {
		// Registrar los circuitos marítimos que incluyen a la terminal, considerando las fechas
		//de las distintas programaciones de viajes (fechas de arribo a la propia terminal y a
		//		todas las demás de los circuitos que la incluyen)7
		
		List<Viaje> viajes = this.getViajes();
		for (Viaje viaje : viajes) {
			Circuito circuitoARegistrar = viaje.getCircuito();
			circuitos.add(circuitoARegistrar);
		}
	}
	
	public Set<Circuito> getCircuitos() {
		return this.circuitos;
	}
	
	public List<Integer> tiempoHasta(Naviera n, TerminalPortuaria destino) {
		return n.tiempoDesdeHasta(this, destino);
	}
	
	public Optional <LocalDateTime> proximaFechaHacia(TerminalPortuaria destino) {
		return this.navieras
	            .stream()
	            .flatMap(n -> n.proximaFechaHacia(destino).stream()) 
	            .min(LocalDateTime::compareTo);
	}

	public Point getPosicion() {
		// TODO Auto-generated method stub
		return this.ubicacion;
	}
	
	public void iniciarTrabajos(Buque b) {
		b.iniciarTrabajos();
	}

	public void finalizarTrabajos(Buque b) {
		b.finalizarTrabajos();
	}
}