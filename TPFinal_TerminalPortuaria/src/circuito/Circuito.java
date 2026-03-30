package circuito;

import java.util.ArrayList;
import java.util.List;

import terminalPortuaria.TerminalPortuaria;

public class Circuito {
	private List<Tramo> tramos = new ArrayList<Tramo>();
	
	public void agregarTramo(Tramo t) {
		tramos.add(t);
	}

	public TerminalPortuaria terminalOrigen() {
		return tramos.getFirst().getOrigen();
	}
	
	public boolean recorreDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		int indiceOrigen = indiceDeTerminal(origen);
		
		if (indiceOrigen == -1) {
 			return false;	// Si no encontró el origen devuelve false.
		}
		
		for (int j = indiceOrigen; j < this.tramos.size(); j++) {
			if (tramos.get(j).getDestino().equals(destino)) {
				return true;
			}
		}
		
		return false; // No se encontró ningún destino igual al destino solicitado luego del origen dado.
	}

	public int tiempoDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) throws IllegalArgumentException{
		if (!recorreDesdeHasta(origen, destino)) {
            throw new IllegalArgumentException("Alguna de las terminales no existe en el circuito o el circuito no recorre esas terminales en ese orden.");
        }
		int duracion = 0;
		boolean sumando = false;
		
		for (Tramo t : tramos) {
			if(t.getOrigen().equals(origen)) sumando = true; // Busco la terminal origen en la lista de tramos.
			if(sumando) duracion += t.getDuracion(); // Sumo el tiempo de cada tramo hasta la terminal destino.
			if(t.getDestino().equals(destino)) break; // Salgo del bucle porque ya llegué al destino.
		}
		
		return duracion;
	}

	public double precioFinal() {
		double precioFinal = 0.0;
		for (Tramo t : tramos) {
			precioFinal += t.getPrecio();
		}
		return precioFinal;
	}
	
	public boolean contieneTerminal(TerminalPortuaria terminal) {
		return tramos.stream().anyMatch(t -> t.getOrigen().equals(terminal));
	}
	
	public List<Tramo> getTramos() {
		return this.tramos;
	}

	public double precioDesdeHasta(TerminalPortuaria terminalGestionada, TerminalPortuaria puertoDestino) {
		// Retorna el precio total de recorrido entre origen y destino, en caso de que el circuito tenga
		// en el recorrido a terminalGestionada y puertoDestino
		if (recorreDesdeHasta(terminalGestionada, puertoDestino)) {
			double precio = 0;
			int indiceTerminalGestionada = indiceDeTerminal(terminalGestionada);
			
			for (int i = indiceTerminalGestionada; i < tramos.size() && !tramos.get(i).getOrigen().equals(puertoDestino); i++) {
				precio += this.tramos.get(i).getPrecio();
			}
			return precio;
		} else {
			return -1;
		}
	}
	
	protected int indiceDeTerminal(TerminalPortuaria t) {
		if (contieneTerminal(t)) {
			int indice = -1;
			for (int i = 0; i < this.tramos.size(); i++) {
				if (tramos.get(i).getOrigen().equals(t)) {
					indice = i;
					break;
				}
			}
			return indice;
		} else {
			return -1;
		}
	}

	public int escalasDesdeHasta(TerminalPortuaria terminalGestionada, TerminalPortuaria puertoDestino) {
		if (recorreDesdeHasta(terminalGestionada, puertoDestino)) {
			int escalas = 0;
			int indiceTerminalGestionada = indiceDeTerminal(terminalGestionada);
			
			for (int i = indiceTerminalGestionada; i < tramos.size(); i++) {
	            if (tramos.get(i).getDestino().equals(puertoDestino)) {
	                break;
	            }
	            escalas++;
	        }
			return escalas;
		} else {
			return -1;
		}
	}

}
