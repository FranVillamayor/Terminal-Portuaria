package orden;

import java.util.ArrayList;

import cliente.Cliente;
import container.Container;
import empresaTransportista.Camion;
import servicio.Servicio;
import viaje.Viaje;

public class OrdenConsignee extends Orden {
	private Cliente consignee;

	public OrdenConsignee(Cliente consignee, Camion camionAsignado, Viaje viaje, Container container,
			ArrayList<Servicio> servicios) {
		super(container, camionAsignado, viaje, servicios);
		this.consignee = consignee;
	}

	@Override
	public Cliente getCliente() {
		return consignee;
	}

	public void setConsignee(Cliente consignee) {
		this.consignee = consignee;
	}
	@Override
    public double montoTotal() {
		return  this.montoTotalServicios() + this.montoTotalViaje();
	}

	public String getEmailCliente() {
		return this.consignee.getEmail();
	}
	

}
