package servicio;

import java.time.LocalDate;

import orden.Orden;

public abstract class Servicio {
	private String nombreServicio;
	private LocalDate fechaServicio;  
	
	public Servicio(String nombreServicio, LocalDate fechaServicio) {
		super();
		this.nombreServicio = nombreServicio;
		this.fechaServicio = fechaServicio;
	}


	
	public abstract double costoServicio(Orden orden);

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public LocalDate getFechaServicio() {
		return fechaServicio;
	}

	public void setFechaServicio(LocalDate fechaServicio) {
		this.fechaServicio = fechaServicio;
	}

	

}
