package criterio;

import java.util.List;
import java.util.stream.Collectors;

import circuito.Circuito;
import naviera.Naviera;
import terminalPortuaria.TerminalPortuaria;

public abstract class Criterio {

	
	public abstract Circuito mejorCircuito(List<Naviera> navieras, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino);
	
	protected List<Naviera> filtrarNavieras(List<Naviera> navieras, TerminalPortuaria terminalGestionada,
			TerminalPortuaria puertoDestino) {
		return navieras.stream().filter(n -> n.recorreDesdeHasta(terminalGestionada, puertoDestino)).collect(Collectors.toList());
	}
}
