package container;

public class Dry extends Container {

    public Dry(String id, int ancho, int largo, int altura, int peso) {
       
        super(id, ancho, largo, altura, peso, true);
    }
  
  public String getTipo() {
		return "Dry";
	}
}