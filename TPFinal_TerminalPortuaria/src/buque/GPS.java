package buque;

import java.awt.Point;


public interface GPS {
	public Point enviarPosicion();
	public boolean hayProblemasClimaticos();
}