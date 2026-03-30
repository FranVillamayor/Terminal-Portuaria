package cliente;

import java.util.ArrayList;
import java.util.List;

import factura.Factura;

public class Cliente {

	private String nombre;
	private String apellido;
	private int dni;
	private String email;
	List<String> correos = new ArrayList<String>();
	List<Factura> facturas = new ArrayList<Factura>();
	
	public Cliente(String nombre, String apellido, int dni, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public int getDni() {
		return dni;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void recibirNotificacion(String email) {
		this.correos.add(email);
	}
	
	public void recibirFactura(Factura factura) {
		this.facturas.add(factura);
	}
	
	public List<Factura> getFacturas() {
		return this.facturas;
	}

}
