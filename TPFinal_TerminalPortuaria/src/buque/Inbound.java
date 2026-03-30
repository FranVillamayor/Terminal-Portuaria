package buque;

public class Inbound extends EstadoBuque {

	@Override
	public void evaluarPosicion(Buque b) {
		if (b.distanciaALaTerminal() == 0.0 && !b.hayProblemasClimaticos()) {
			notificarArribo(b);
			b.setEstado(new Arrived());
		} else if (b.hayProblemasClimaticos()){
			b.setEstado(new Outbound());
		}
	}
	
	private void notificarArribo(Buque b) {
		b.notificarArribo();
	}
}