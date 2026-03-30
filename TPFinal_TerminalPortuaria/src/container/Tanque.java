package container;

public class Tanque extends Container {
	public Tanque(String id, int ancho, int largo, int altura, int peso, boolean servicioEspecial) {
		super(id, ancho, largo, altura, peso, true);
		// TODO Auto-generated constructor stub
	}

	public String getTipo() {
		return "Tanque";
	}
}
