package modelo;

import java.util.ArrayList;

public class Hora {

	private String fecha;
	private ArrayList<Datos> hTemperatura;
	private ArrayList<Datos> hSensacionTermica;
	private ArrayList<Datos> hEstadoClima;
	private ArrayList<Viento> hViento;
	private ArrayList<Datos> hPrecipitacion;
	private ArrayList<Datos> hHumedad;

	public Hora() {
		this.fecha = "";
		this.hTemperatura = new ArrayList<>();
		this.hSensacionTermica = new ArrayList<>();
		this.hEstadoClima = new ArrayList<>();
		this.hViento = new ArrayList<>();
		this.hPrecipitacion = new ArrayList<>();
		this.hHumedad = new ArrayList<>();
	}

	public String getFecha() {
		return fecha;
	}

	public String quitarHora(String dato) {

		return dato.replaceAll(getHora(dato) + " ", "");
	}

	public String getHora(String temperatura) {

		String v[] = temperatura.split(" ");
		return v[0];
	}

	public ArrayList<Datos> gethTemperatura() {
		return hTemperatura;
	}

	public ArrayList<Datos> gethSensacionTermica() {
		return hSensacionTermica;
	}

	public ArrayList<Datos> gethEstadoClima() {
		return hEstadoClima;
	}

	public ArrayList<Viento> gethViento() {
		return hViento;
	}

	public ArrayList<Datos> gethPrecipitacion() {
		return hPrecipitacion;
	}

	public ArrayList<Datos> gethHumedad() {
		return hHumedad;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setHTemperatura(Datos temperatura) {
		Datos d = new Datos();
		d.setDato(temperatura.getDato());
		d.setHora(temperatura.getHora());
		this.hTemperatura.add(d);
	}

	public void setHSensacionTermica(Datos sensacionTermica) {
		Datos d = new Datos();
		d.setDato(sensacionTermica.getDato());
		d.setHora(sensacionTermica.getHora());
		this.hSensacionTermica.add(d);
	}

	public void setHEstadoClima(Datos estadoClima) {
		Datos d = new Datos();
		d.setDato(estadoClima.getDato());
		d.setHora(estadoClima.getHora());
		this.hEstadoClima.add(d);
	}

	public void setHViento(Viento viento) {
		Viento v = new Viento();
		v.setDato(viento.getDato());
		v.setHora(viento.getHora());
		v.setHorientacion(viento.getHorientacion());
		this.hViento.add(v);
	}

	public void setHPrecipitacion(Datos precipitacion) {
		Datos d = new Datos();
		d.setDato(precipitacion.getDato());
		d.setHora(precipitacion.getHora());
		this.hPrecipitacion.add(d);
	}

	public void setHHumedad(Datos humedad) {
		Datos d = new Datos();
		d.setDato(humedad.getDato());
		d.setHora(humedad.getHora());
		this.hHumedad.add(humedad);
	}

}