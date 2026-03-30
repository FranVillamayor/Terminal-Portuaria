package servicio;
import java.time.LocalDateTime;
import java.time.Period;

import java.time.Duration;
import java.time.LocalDate;

import orden.Orden;

public class Almacenamiento extends Servicio {
	private int costoPorDia;
	
	public Almacenamiento(String nombreServicio, LocalDate fechaServicio, int costoPorDia) {
		super(nombreServicio,fechaServicio);
		this.costoPorDia = costoPorDia;
	}

	@Override 
	public double costoServicio(Orden orden) {

		long dias = Duration.between(orden.getViaje().fechaLlegadaTerminalGestionada(), LocalDateTime.now()).toDays();
		return  costoPorDia * dias;

	}

	public int getCostoPorDia() {
		return costoPorDia;
	}

	public void setCostoPorDia(int costoPorDia) {
		this.costoPorDia = costoPorDia;
	}

}
