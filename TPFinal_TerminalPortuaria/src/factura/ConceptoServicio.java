package factura;

import java.time.LocalDate;

public class ConceptoServicio {
	private String nombreServicio;
	private LocalDate fechaEmision;
	private double monto;
	
	public ConceptoServicio(String nombreServicio, LocalDate fechaEmision, double monto) {
		super();
		this.nombreServicio = nombreServicio;
		this.fechaEmision = fechaEmision;
		this.monto = monto;
	}
	
	public String getNombreServicio() {
		return nombreServicio;
	}
	public LocalDate getFechaEmision() {
		return fechaEmision;
	}
	public double getMonto() {
		return monto;
	}

}
