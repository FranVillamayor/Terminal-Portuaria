package container;

import java.time.Duration;
import java.time.LocalDateTime;

public class Reefer extends Container {
	private double consumoPorHora;
	private LocalDateTime inicioDeConexion;
	private LocalDateTime finConexion;
	private double horasConectado;
	
	
	public Reefer(String id, int ancho, int largo, int altura, int peso, boolean servicioEspecial, double consumoPorHora) {
		super(id, ancho, largo, altura, peso, false);
		this.consumoPorHora = consumoPorHora;
		
	}

	
	public double getConsumoPorHora() {
		return consumoPorHora;
	}

	public void conectar(LocalDateTime inicio) {
        this.inicioDeConexion = inicio;
    }

    public void desconectar(LocalDateTime fin) {
        this.finConexion = fin;
    }
	
    public double getHorasConectado() {
        if (inicioDeConexion != null && finConexion != null) {
            Duration duracion = Duration.between(inicioDeConexion, finConexion);
            return duracion.toHours();
        }
        return horasConectado; 
    }
    
    public double calcularConsumoTotal() {
    	double horas = getHorasConectado();
    	return horas * consumoPorHora;
    }
    public String getTipo() {
		return "Reefer";
	}
}

