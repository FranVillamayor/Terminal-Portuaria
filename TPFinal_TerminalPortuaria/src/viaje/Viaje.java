package viaje;

import terminalPortuaria.TerminalPortuaria;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import buque.Buque;
import circuito.Circuito;
import circuito.Tramo;

public class Viaje {
	private Buque buque;
	private Circuito circuito;
	private LocalDateTime fechaSalida;
	private TerminalPortuaria terminalGestionada;
	private Map<TerminalPortuaria, LocalDateTime> cronograma = new LinkedHashMap<TerminalPortuaria, LocalDateTime>();;
	
	
	public Viaje(Buque buque, Circuito circuito, LocalDateTime fechaSalida, TerminalPortuaria terminalGestionada) {
		this.buque = buque;
		this.circuito = circuito;
		this.fechaSalida = fechaSalida;
		this.terminalGestionada = terminalGestionada;
		this.cronograma = crearCronograma(circuito);
	}
	
	public LocalDateTime fechaSalidaTerminalGestionada() {
		return this.cronograma.get(terminalGestionada);
	}

	private Map<TerminalPortuaria, LocalDateTime> crearCronograma(Circuito circuito) {
		Map<TerminalPortuaria, LocalDateTime> nuevoCronograma = new LinkedHashMap<>();
		LocalDateTime fechaActual = this.fechaSalida;
		for (Tramo tramo : circuito.getTramos()) {
			nuevoCronograma.put(tramo.getOrigen(), fechaActual);
			fechaActual = fechaActual.plusHours(tramo.getDuracion());
		}
		return nuevoCronograma;
	}
	
	public Buque getBuque() {
		return this.buque;
	}
	
	public LocalDateTime proximaFechaHacia(TerminalPortuaria terminal) {
		if (this.circuito.contieneTerminal(terminal)) {
			return fechaSalidaTerminalGestionada();
		} else {
			return null;
		}
	}
	
	// Retorna verdadero si en el cronograma hay una proxima terminal, luego de la terminal gestionada, con la fecha de llegada f.
	public boolean tieneProximaFechaDeLlegada(LocalDateTime f) {
		boolean despuesDeLaTerminal = false;
		for (Entry<TerminalPortuaria, LocalDateTime> e : this.cronograma.entrySet()) {
			if (despuesDeLaTerminal && e.getValue().equals(f)) {
				return true;
			}
			
			if (e.getKey().equals(this.terminalGestionada)) {
				despuesDeLaTerminal = true;
			}
		}
		return false;
	}

	// Retorna verdadero si el cronograma tiene a la Terminal Gestionada con la fechaDeSalida f.
	public boolean tieneFechaSalida(LocalDateTime f) {
		return fechaSalidaTerminalGestionada().isEqual(f);
	}
	
	// Retorna verdadero si en el cronograma tiene a puertoDestino como proximos arrivos.
	public boolean tienePuertoDestino(TerminalPortuaria puertoDestino) {
		if (this.circuito.contieneTerminal(puertoDestino)) {
			return LocalDateTime.now().isBefore(this.cronograma.get(puertoDestino));
		}
		return false;
	}
	
	// Si la terminalGestionada es el origen devuelve la fechaSalida en caso contrario devuelve la fecha de llegada.
	public LocalDateTime fechaLlegadaTerminalGestionada() {
		return cronograma.get(terminalGestionada);
	}
	
	public double precioFinal() {
		return this.circuito.precioFinal();
	}

	public Circuito getCircuito() {
		return this.circuito;
	}
	
	public boolean contieneTerminal(TerminalPortuaria terminal) {
		return this.circuito.contieneTerminal(terminal); 
	}
}