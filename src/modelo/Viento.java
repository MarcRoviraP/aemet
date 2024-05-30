package modelo;

public class Viento extends Datos {

	private String horientacion;

	public Viento() {
		super();
		this.horientacion = "";
	}

	public String getHorientacion() {
		return horientacion;
	}

	public void setHorientacion(String horientacion) {
		this.horientacion = horientacion;
	}

	@Override
	public String toString() {
		return horientacion + " " + getDato();
	}

}
