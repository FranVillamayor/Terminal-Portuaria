package buque;

public class Arrived extends EstadoBuque {
	@Override
	public void iniciarTrabajos(Buque b){
		b.setEstado(new Working());
	}
 }
