package container;

import java.util.ArrayList;


public class DryComposite extends Dry{
	private ArrayList<Dry> cargas = new ArrayList<Dry>();

	public DryComposite(String id, int ancho, int largo, int altura, int peso) {
		super(id, ancho, largo, altura, peso);
	}
	
	public void add(Dry dry) {
		cargas.add(dry);
	}
	public void remove(Dry dry) {
		cargas.remove(dry);
	}
	@Override
    public double pesoTotal() {
        double total = getPeso(); // el peso propio del contenedor
        for (Dry carga : cargas) {
            total += carga.pesoTotal(); // suma pesos de los hijos
        }
        return total;
    }
}
