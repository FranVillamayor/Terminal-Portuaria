package filtro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import viaje.Viaje;

public class OperacionOR implements Filtro {

	List<Filtro> filtros = new ArrayList<>();
	
	@Override
	public List<Viaje> filtrar(List<Viaje> listaDeViajes) {
		if (filtros.isEmpty()) {
	        return new ArrayList<>(listaDeViajes);
	    }
	    
	    Set<Viaje> resultado = new HashSet<>(listaDeViajes);
	    
	    for (Filtro filtro : filtros) {
	        Set<Viaje> filtrado = new HashSet<>(filtro.filtrar(listaDeViajes));
	        resultado.addAll(filtrado);
	        
	        // si ya está vacío, salir del loop
	        if (resultado.isEmpty()) {
	            break;
	        }
	    }
	    return new ArrayList<>(resultado);
	}

	@Override
	public void agregar(Filtro filtro) {
		// TODO Auto-generated method stub
		filtros.add(filtro);
	}

	@Override
	public void eliminar(Filtro filtro) {
		// TODO Auto-generated method stub
		filtros.remove(filtro);
	}

}
