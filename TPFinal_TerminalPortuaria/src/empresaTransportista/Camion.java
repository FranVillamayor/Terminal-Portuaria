package empresaTransportista;

public class Camion {
	
	private String patente;
	private Chofer chofer;

	public Camion(String patente, Chofer chofer) {
		// TODO Auto-generated constructor stub
		this.patente = patente;
		this.chofer = chofer;
	}

	public String getPatente() {
		// TODO Auto-generated method stub
		return patente;
	}
	
	public void setChofer(Chofer c) {
		this.chofer = c;
	}
	
	public Chofer getChofer() {
		return this.chofer;
	}

	public boolean estaRegistradoElChofer(Chofer chofer1) {
		// TODO Auto-generated method stub
		return chofer.getDni() == chofer1.getDni();
	}

}
