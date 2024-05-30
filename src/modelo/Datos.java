package modelo;

public class Datos {
	private String hora;
	private String dato;

	public Datos() {

		this.hora = "";
		this.dato = "";
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	@Override
	public String toString() {
		return hora + " " + dato;
	}
	
	

}
