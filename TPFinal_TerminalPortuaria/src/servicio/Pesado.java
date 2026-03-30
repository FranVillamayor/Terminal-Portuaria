package servicio;

import java.time.LocalDate;

import orden.Orden;

public class Pesado extends Servicio {
	private int costoPesaje;
	
	public Pesado(String nombreServicio, LocalDate fechaServicio, int costoPesaje) {
		super(nombreServicio,fechaServicio);
		this.costoPesaje = costoPesaje;
	}
	
	@Override 
	public double costoServicio(Orden orden) {
		return costoPesaje;
	}
	public int getCostoPesaje() {
		return costoPesaje;
	}
	public void setCostoPesaje(int costoPesaje) {
		this.costoPesaje = costoPesaje;
	}	
}


