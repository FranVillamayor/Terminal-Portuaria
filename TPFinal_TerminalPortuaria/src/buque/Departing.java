package buque;

public class Departing extends EstadoBuque {

	@Override
	public void evaluarPosicion(Buque b) {
		if (b.distanciaALaTerminal() > 1.0) {
			this.notificarSalida(b);
			b.setEstado(new Outbound());
		}
	}
	
	private void notificarSalida(Buque b) {
		b.notificarSalida();
	}
	
	@Override
	public boolean puedePagarServicios() {
		return true;
	}
}