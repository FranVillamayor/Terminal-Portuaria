package empresaTransportista;

import java.util.ArrayList;
import java.util.List;

public class EmpresaTransportista {
	
	List <Camion> camiones = new ArrayList<Camion>();

	public void registrarCamion(Camion camion) {
		// TODO Auto-generated method stub
		camiones.add(camion);
	}
	
	public List<Camion> getCamiones() {
		return this.camiones;
	}

	public boolean estaRegistradoElCamion(Camion camion1) {
		// TODO Auto-generated method stub
		return camiones.stream().anyMatch(camion -> camion.getPatente() == camion1.getPatente());
	}

	public boolean estaRegistradoElChofer(Chofer choferAsignado) {
		// TODO Auto-generated method stub
		return camiones.stream().anyMatch(c -> c.estaRegistradoElChofer(choferAsignado));
	}

}
