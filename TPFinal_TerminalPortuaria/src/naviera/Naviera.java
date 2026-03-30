package naviera;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import buque.Buque;
import circuito.Circuito;
import terminalPortuaria.TerminalPortuaria;
import viaje.Viaje;

public class Naviera {
	private List<Circuito> circuitos = new ArrayList<>();
	private List<Buque> buques = new ArrayList<>();
	private List<Viaje> viajes = new ArrayList<>();
	
	public void registrarCircuito(Circuito c) {
		this.circuitos.add(c);
	}
	
	public void registrarBuque(Buque b) {
		buques.add(b);
	}
	
	public void programarNuevoViaje(Buque b, Circuito c, LocalDateTime fSalida, TerminalPortuaria tGestionada) {
		if (!this.buques.contains(b) || !this.circuitos.contains(c)) {
			throw new IllegalArgumentException("El buque o circuito no está registrado.");
		}
		
		Viaje nuevoViaje = new Viaje(b, c, fSalida, tGestionada);
		
		b.setViaje(nuevoViaje);
		
		this.viajes.add(nuevoViaje);
	}
	
	public Optional<LocalDateTime> proximaFechaHacia(TerminalPortuaria destino) {
		LocalDateTime ahora = LocalDateTime.now();
		Optional<LocalDateTime> proximaFecha = viajes.stream()
											    .map(v -> v.proximaFechaHacia(destino))
											    .filter(Objects::nonNull)
											    .filter(f -> f.isAfter(ahora))
											    .min(Comparator.comparing(f -> Duration.between(ahora, f)));
		return proximaFecha;
	}
	
	public boolean recorreDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		return circuitos.stream().anyMatch(v -> v.recorreDesdeHasta(origen, destino));
	}
	
	public List<Integer> escalasDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		if (recorreDesdeHasta(origen, destino)) {
			return circuitos.stream()
					.map(c -> c.escalasDesdeHasta(origen, destino))
			        .filter(escalas -> escalas != -1)
			        .collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException("Alguna de las terminales no existe en el circuito o el circuito no recorre esas terminales en ese orden."); 
		}
	}
	
	public List<Double> preciosDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		if (recorreDesdeHasta(origen, destino)) {
			return circuitos.stream()
					.map(c -> c.precioDesdeHasta(origen, destino))
					.filter(precios -> precios != -1)
					.collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException("Alguna de las terminales no existe en el circuito o el circuito no recorre esas terminales en ese orden.");
		}
	}
	
	//Devuelve el circuito que va desde origen hasta destino con menor precio de recorrido.
	public Circuito circuitoMenorPrecioDesdeHasta(TerminalPortuaria puertoOrigen, TerminalPortuaria puertoDestino) {
		return this.circuitos.stream()
								.filter(c -> c.recorreDesdeHasta(puertoOrigen, puertoDestino))
								.min(Comparator.comparingDouble(c -> c.precioDesdeHasta(puertoOrigen, puertoDestino)))
								.orElse(null);
	}
	
	// Devuelve el circuito que va desde origen hasta destino con menor tiempo de recorrido.
	public Circuito circuitoMenorTiempoDesdeHasta(TerminalPortuaria puertoOrigen, TerminalPortuaria puertoDestino) {
		return this.circuitos.stream()
								.filter(c -> c.recorreDesdeHasta(puertoOrigen, puertoDestino))
								.min(Comparator.comparingInt(c -> c.tiempoDesdeHasta(puertoOrigen, puertoDestino)))
								.orElse(null);
	}
	
	//Devuelve el circuito que va desde origen hasta destino con menor puertos intermedios 
	public Circuito circuitoMenorEscalasDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		return this.circuitos.stream()
								.filter(c -> c.recorreDesdeHasta(origen, destino))
								.min(Comparator.comparingInt(c -> c.escalasDesdeHasta(origen, destino)))
								.orElse(null);

	}

	public List<Viaje> getViajesDeTerminal(TerminalPortuaria terminalGestionada) {
		return this.viajes.stream()
							.filter(v -> v.contieneTerminal(terminalGestionada))
							.toList();
	}

	public List<Integer> tiempoDesdeHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
		return this.circuitos.stream()
								.map(c -> c.tiempoDesdeHasta(origen, destino))
								.collect(Collectors.toList());
	}
}