package filtro;

import java.util.List;

import viaje.Viaje;

public interface Filtro {

	public List<Viaje> filtrar(List<Viaje> listaDeViajes);
	public void agregar(Filtro filtro);
	public void eliminar(Filtro filtro);
}
