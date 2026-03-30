package criterio;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import circuito.Circuito;
import naviera.Naviera;
import terminalPortuaria.TerminalPortuaria;

public class CriterioMenosEscalas extends Criterio {

	@Override
	public Circuito mejorCircuito(List<Naviera> navieras, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		if (navieras.isEmpty()) {
			throw new IllegalArgumentException("la lista de navieras esta vacia");
		}
		List<Naviera> filtrados = this.filtrarNavieras(navieras, terminalGestionada, puertoDestino);
		return this.menosEscalas(filtrados, terminalGestionada, puertoDestino);
	}
	
	
	private Circuito menosEscalas(List<Naviera> filtradas, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		if (filtradas.isEmpty()) {
			throw new IllegalArgumentException(
					"No hay navieras que recorran desde " + terminalGestionada + " hasta " + puertoDestino);
		}
		
		List<Circuito> circuitosFiltrados = this.filtrarCircuitos(filtradas,terminalGestionada,puertoDestino);
		
		return circuitosFiltrados.stream()
			    .min(Comparator.comparing(c -> c.escalasDesdeHasta(terminalGestionada, puertoDestino)))
			    .orElse(null);	
		}


	private List<Circuito> filtrarCircuitos(List<Naviera> filtradas, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		
		return filtradas.stream().map(n -> n.circuitoMenorEscalasDesdeHasta(terminalGestionada, puertoDestino))
				.collect(Collectors.toList());
	}


}
