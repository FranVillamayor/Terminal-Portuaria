package filtro;

import java.util.List;
import java.util.stream.Collectors;

import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

public class FiltroPuertoDestino implements Filtro {

	TerminalPortuaria puertoDestino;
	
	public FiltroPuertoDestino(TerminalPortuaria puertoDestino) {
		// TODO Auto-generated constructor stub
		this.puertoDestino = puertoDestino;
	}

	@Override
	public List<Viaje> filtrar(List<Viaje> listaDeViajes) {
		// TODO Auto-generated method stub
		return listaDeViajes.stream().filter(v -> v.tienePuertoDestino(puertoDestino)).collect(Collectors.toList());
	}

	@Override
	public void agregar(Filtro filtro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Filtro filtro) {
		// TODO Auto-generated method stub
		
	}

}
