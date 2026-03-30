package servicio;

import java.time.LocalDate;

import orden.Orden;

public class Lavado extends Servicio {
	private int precioFijo; //PREGUNTAR SI LO PUEDO ELIMINAR

	private int precioSuperaVolumen;
	private int precioNoSuperaVolumen;
	
	@Override
	public double costoServicio(Orden orden) {
		if(orden.getContainer().volumen() < 70) {
			this.setPrecioFijo ( precioNoSuperaVolumen);
		}else {
			this.setPrecioFijo (precioSuperaVolumen); 
		}
		return precioFijo;
	}

	public void setPrecioFijo(int precioFijo) {
		this.precioFijo = precioFijo;
	}

	public Lavado(String nombreServicio, LocalDate fechaServicio, int precioFijo, int precioSuperaVolumen, int precioNoSuperaVolumen) {
		super(nombreServicio, fechaServicio);
		this.precioFijo = precioFijo;
		this.precioSuperaVolumen = precioSuperaVolumen;
		this.precioNoSuperaVolumen = precioNoSuperaVolumen;
	}
	
	public void setPrecioSuperaVolumen(int precioSuperaVolumen) {
		this.precioSuperaVolumen = precioSuperaVolumen;
	}
	
	public void setPrecioNoSuperaVolumen(int precioNoSuperaVolumen) {
		this.precioNoSuperaVolumen = precioNoSuperaVolumen;
	}	
}
 