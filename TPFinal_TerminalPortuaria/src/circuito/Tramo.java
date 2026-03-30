package circuito;

import terminalPortuaria.TerminalPortuaria;

public class Tramo {
	private TerminalPortuaria origen;
	private TerminalPortuaria destino;
	private int duracion;
	private double precio;
	
	public Tramo(TerminalPortuaria origen, TerminalPortuaria destino, int duracion, double precio) {
		this.origen = origen;
		this.destino = destino;
		this.duracion = duracion;
		this.precio = precio;
	}
	
	public TerminalPortuaria getOrigen() {
		return origen;
	}
	
	public TerminalPortuaria getDestino() {
		return destino;
	}

	public int getDuracion() {
		return duracion;
	}

	public double getPrecio() {
		return precio;
	}

}
