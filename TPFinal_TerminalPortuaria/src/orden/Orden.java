package orden;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cliente.Cliente;
import container.Container;
import empresaTransportista.Camion;
import empresaTransportista.Chofer;
import factura.Factura;
import servicio.Servicio;
import viaje.Viaje;


public abstract class Orden {
	private ArrayList <Servicio> servicios = new ArrayList<Servicio>();
	private Container container;
	private Camion camionAsignado;
	private Viaje viaje;
	
	
	
	public Orden(Container container, Camion camionAsignado, Viaje viaje, ArrayList<Servicio> servicios) {
		super();
		this.container = container;
		this.camionAsignado = camionAsignado;
		this.viaje = viaje;
		this.servicios = servicios;
	}
	
	
	public Factura facturar() {
	    Factura factura = new Factura(this.servicios, this);
	    System.out.println(factura.desgloseDeServicios());
	    return factura;
	}
	
	

	public Chofer choferAsignado(){
		 return this.getCamionAsignado().getChofer();
	}
	
	public void setCamionAsignado(Camion camionAsignado) {
		this.camionAsignado = camionAsignado;
	}
	public double montoTotalServicios() {
	
			return this.servicios.stream().mapToDouble( s -> s.costoServicio(this)  ).sum();
	}
	public double montoTotalViaje() {
		
		return this.viaje.precioFinal();
	}
	public abstract double montoTotal();
	public void agregarServicio(Servicio servicio) {
		servicios.add(servicio);  
	}

	//GETTERS
	public ArrayList<Servicio> getServicios() {
		return servicios;
	}
	public Viaje getViaje() {
		return viaje;
	}
	
	public Camion getCamionAsignado() {
		return camionAsignado;
	}
	
	public Container getContainer() {
		return container;  
	}
	//SETTERS
	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}
	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	
	public void setContainer(Container container) {
		this.container = container;
	}
	public String getNombreBuque() {
	// TODO Auto-generated method stub
		return null;
	}
	public LocalDateTime getHorarioTurno() {
		// TODO Auto-generated method stub
		return null;
	}


	public abstract String getEmailCliente();
	public abstract Cliente getCliente();
}

