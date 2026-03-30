package criterio;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import circuito.Circuito;
import naviera.Naviera;
import terminalPortuaria.TerminalPortuaria;

public class CriterioMenorTiempo extends Criterio{

	@Override
	public Circuito mejorCircuito(List<Naviera> navieras, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		if (navieras.isEmpty()) {
			throw new IllegalArgumentException("la lista de naviera esta vacia");
		}
		
		List<Naviera> filtradas = this.filtrarNavieras(navieras, terminalGestionada, puertoDestino);
		return this.menorTiempo(filtradas, terminalGestionada, puertoDestino);
	}

	private Circuito menorTiempo(List<Naviera> filtradas, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		
		if (filtradas.isEmpty()) {
			throw new IllegalArgumentException(
					"No hay navieras que recorran desde " + terminalGestionada + " hasta " + puertoDestino);
		}
		
		List<Circuito> circuitosFiltrados = this.filtrarCircuitos(filtradas,terminalGestionada,puertoDestino);
		
		return circuitosFiltrados.stream()
			    .min(Comparator.comparing(circuito -> circuito.tiempoDesdeHasta(terminalGestionada, puertoDestino)))
			    .orElse(null);	
		}

	private List<Circuito> filtrarCircuitos(List<Naviera> filtradas, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		
		return filtradas.stream().map(n -> n.circuitoMenorTiempoDesdeHasta(terminalGestionada, puertoDestino))
				.collect(Collectors.toList());
	}
}


