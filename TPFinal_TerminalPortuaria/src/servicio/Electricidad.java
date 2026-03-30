package servicio;

import java.time.LocalDate;

import container.Reefer;
import orden.Orden;

public class Electricidad extends Servicio {
	private double costoKW;
	private Reefer reefer;
	
	
	public Electricidad(String nombreServicio, LocalDate fechaServicio, double costoKW) {
		super(nombreServicio,fechaServicio);
		this.costoKW = costoKW;
		
	}
	@Override
	public double costoServicio(Orden orden) {
		return costoKW * reefer.calcularConsumoTotal();
	}
	public double getCostoKW() {
		return costoKW;
	}
	public void setCostoKW(double costoKW) {
		this.costoKW = costoKW;
	}
	public void setReefer(Reefer reefer) {
		this.reefer = reefer;
	}
}
