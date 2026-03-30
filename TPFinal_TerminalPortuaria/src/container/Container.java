package container;

public  class Container {
	private String id;// 4 letras y 7 numeros   
	private int ancho;
	private int largo;
	private int altura;
	private int peso; 
	private boolean servicioEspecial;

	public Container(String id, int ancho, int largo, int altura, int peso, boolean servicioEspecial) {
		super();
		this.id = id;
		this.ancho = ancho;
		this.largo = largo;
		this.altura = altura;
		this.peso = peso;
		this.servicioEspecial = servicioEspecial;
	}
	
	public String getId() {
		return id;
	}
	public int getAncho() {
		return ancho;
	}
	public int getLargo() {
		return largo;
	}
	public int getAltura() {
		return altura;
	}
	public int getPeso() {
		return peso;
	}
	public boolean isServicioEspecial() {
		return servicioEspecial;
	}
	public double pesoTotal() {
        return this.getPeso();
    }
	public int volumen()
	{
		return this.ancho * this.largo * this.altura;
	}

	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
