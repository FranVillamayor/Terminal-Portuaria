package buque;

public class Working extends EstadoBuque {
	@Override
	public void finalizarTrabajos(Buque b) {
		b.getTerminalGestionada().finalizarTrabajos(b);
	}
	
	@Override 
	public void depart(Buque b) {
		b.setEstado(new Departing());
	}
	
}
