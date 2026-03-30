package buque;

public class Outbound extends EstadoBuque {

	@Override
	public boolean puedeRegistrar() {
		return true;
	}

	@Override
	public void evaluarPosicion(Buque b) {
		if (b.distanciaALaTerminal() < 50.0) {
			b.setEstado(new Inbound());
		}
	}

}
