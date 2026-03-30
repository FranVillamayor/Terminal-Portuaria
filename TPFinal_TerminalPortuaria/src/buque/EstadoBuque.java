package buque;

public abstract class EstadoBuque {
	
	public boolean puedePagarServicios() {
		return false;
	}
	
	public boolean puedeRegistrar() {
		return false;
	}
	
	public void iniciarTrabajos(Buque b) { 
		return;
	}
	
	public void finalizarTrabajos(Buque b) { 
		return;
	}
	
	public void evaluarPosicion(Buque b) {
		return;
	}
	
	public void depart(Buque b) {
		return;
	}
}