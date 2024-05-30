package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Dia {

    private String fecha;
    private String estadoClima;
    private String temperaturaMaxima;
    private String temperaturaMinima;
    private String sensacionMaxima;
    private String sensacionMinima;
    private String precipitacionMaxima;
    private String humedadMaxima;
    private String humedadMinima;
    private String ultravioleta;
    private ArrayList<Datos> rangosTemperatura;
    private ArrayList<Datos> rangosSensacion;
    private ArrayList<Datos> rangosPrecipitacion;
    private ArrayList<Viento> rangosViento;
    private ArrayList<Datos> rangosHumedad;


    public Dia() {
        this.fecha = "";
        this.estadoClima = "";
        this.temperaturaMaxima = "";
        this.temperaturaMinima = "";
        this.sensacionMaxima = "";
        this.sensacionMinima = "";
        this.precipitacionMaxima = "";
        this.humedadMaxima = "";
        this.humedadMinima = "";
        this.ultravioleta = "";
        this.rangosTemperatura = new ArrayList<>();
        this.rangosSensacion = new ArrayList<>();
        this.rangosPrecipitacion = new ArrayList<>();
        this.rangosViento = new ArrayList<>();
        this.rangosHumedad = new ArrayList<>();

    }

	public void setPrecipitacionMaxima(String precipitacionMaxima) {
		this.precipitacionMaxima = precipitacionMaxima;
	}



	public String getVientoH() {
		return rangosViento.get(0).getHorientacion();
	}

	public String getVientoGeneral() {
		return rangosViento.get(0).getDato();
	}

	public String getPrecipitacionMaxima() {
		return precipitacionMaxima.isEmpty()?"0":precipitacionMaxima;
	}

	public String datosMin(ArrayList<Datos> lista) {

		double minima = Double.MAX_VALUE;
		double tmp;
		for (Datos datos : lista) {
			datos.getDato();
			tmp = Double.parseDouble(datos.getDato());
			minima = Math.max(minima, tmp);
		}

		return minima + "";
	}

	public String datosMax(ArrayList<Datos> lista) {

		try {

			int maxim = Integer.MIN_VALUE;
			int tmp;
			for (Datos datos : lista) {
				tmp = Integer.parseInt(datos.getDato());
				maxim = Math.max(maxim, tmp);
			}

			
			return maxim + "";
		} catch (Exception e) {
			this.precipitacionMaxima = "0";
			return "0";
		}
	}

	public void setRangosTemperatura(String s) {

		try {
			
		String[] v = parsearRangos(s);
		Datos temp = new Datos();
		if (v.length != 0) {

			temp.setDato(v[1]);
			temp.setHora(v[0]);
			rangosTemperatura.add(temp);
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	public void setRangosSensacion(String s) {
		
		try {
			
		String[] v = parsearRangos(s);
		Datos temp = new Datos();
		if (v.length != 0) {

			temp.setDato(v[1]);
			temp.setHora(v[0]);
			rangosSensacion.add(temp);
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	public void setRangosPrecipitacion(String s) {
		String[] v = parsearRangos(s);
		Datos temp = new Datos();
		if (v.length != 0) {

			temp.setDato(v[1]);
			temp.setHora(v[0]);
			rangosPrecipitacion.add(temp);
			this.precipitacionMaxima = datosMax(rangosPrecipitacion);
		}
	}

	public void setRangosHumedad(String s) {
		
		try {
			
		String[] v = parsearRangos(s);
		Datos temp = new Datos();
		if (v.length != 0) {

			temp.setDato(v[1]);
			temp.setHora(v[0]);
			rangosHumedad.add(temp);
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	public void setRangosViento(String s) {
		
		try {
			
		String[] v = parsearRangos(s);
		Viento temp = new Viento();
		if (v.length != 0) {

			temp.setHora(v[0]);
			temp.setHorientacion(v[1]);
			temp.setDato(v[2]);
			rangosViento.add(temp);
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	public String[] parsearRangos(String formato) {
		String[] v = formato.split(" ");
		return v;

	}

	public String getFecha() {
		LocalDate localDate = LocalDate.parse(fecha);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", Locale.getDefault());

		return localDate.format(formatter).toUpperCase();
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstadoClima() {
		return estadoClima;
	}

	public void setEstadoClima(String estadoClima) {
		this.estadoClima = estadoClima;
	}

	public String getTemperaturaMaxima() {
		return temperaturaMaxima + "º";
	}

	public void setTemperaturaMaxima(String temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}

	public String getTemperaturaMinima() {
		return temperaturaMinima + "º";
	}

	public void setTemperaturaMinima(String temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}

	public String getSensacionMaxima() {
		return sensacionMaxima + "º";
	}

	public void setSensacionMaxima(String sensacionMaxima) {
		this.sensacionMaxima = sensacionMaxima;
	}

	public String getSensacionMinima() {
		return sensacionMinima + "º";
	}

	public void setSensacionMinima(String sensacionMinima) {
		this.sensacionMinima = sensacionMinima;
	}

	public String getHumedadMaxima() {
		return humedadMaxima + "%";
	}

	public void setHumedadMaxima(String humedadMaxima) {
		this.humedadMaxima = humedadMaxima;
	}

	public String getHumedadMinima() {
		return humedadMinima + "%";
	}

	public void setHumedadMinima(String humedadMinima) {
		this.humedadMinima = humedadMinima;
	}

	public String getUltravioleta() {

		return ultravioleta.isEmpty() ? "0" : ultravioleta;
	}

	public void setUltravioleta(String ultravioleta) {
		this.ultravioleta = ultravioleta;
	}

	public void imprimirDia() {
		if (!fecha.isEmpty()) {
			System.out.println("Fecha: " + fecha);
		}
		if (!estadoClima.isEmpty()) {
			System.out.println("Estado del clima: " + estadoClima);
		}
		if (!temperaturaMaxima.isEmpty()) {
			System.out.println("Temperatura máxima: " + temperaturaMaxima);
		}
		if (!temperaturaMinima.isEmpty()) {
			System.out.println("Temperatura mínima: " + temperaturaMinima);
		}

		if (!rangosTemperatura.isEmpty()) {

			System.out.println("Rangos de temperatura:");
			for (Datos temp : rangosTemperatura) {
				if (!temp.getHora().isEmpty() && !temp.getDato().isEmpty()) {
					System.out.println("Hora: " + temp.getHora() + ", Temperatura: " + temp.getDato());
				}
			}
		}

		if (!sensacionMaxima.isEmpty()) {
			System.out.println("Sensación térmica máxima: " + sensacionMaxima);
		}
		if (!sensacionMinima.isEmpty()) {
			System.out.println("Sensación térmica mínima: " + sensacionMinima);
		}

		if (!rangosSensacion.isEmpty()) {

			System.out.println("Rangos de sensación térmica:");
			for (Datos temp : rangosSensacion) {
				if (!temp.getHora().isEmpty() && !temp.getDato().isEmpty()) {
					System.out.println("Hora: " + temp.getHora() + ", Sensación térmica: " + temp.getDato());
				}
			}
		}

		try {

			if (!precipitacionMaxima.isEmpty()) {
				System.out.println("Precipitacion máxima: " + precipitacionMaxima);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (!rangosPrecipitacion.isEmpty()) {

			System.out.println("Rangos de precipitación:");
			for (Datos dato : rangosPrecipitacion) {
				if (!dato.getHora().isEmpty() && !dato.getDato().isEmpty()) {
					System.out.println("Hora: " + dato.getHora() + ", Precipitación: " + dato.getDato());
				}
			}
		}

		if (!humedadMaxima.isEmpty()) {
			System.out.println("Humedad máxima: " + humedadMaxima);
		}
		if (!humedadMinima.isEmpty()) {
			System.out.println("Humedad mínima: " + humedadMinima);
		}

		if (!rangosHumedad.isEmpty()) {

			System.out.println("Rangos de humedad:");
			for (Datos temp : rangosHumedad) {
				if (!temp.getHora().isEmpty() && !temp.getDato().isEmpty()) {
					System.out.println("Hora: " + temp.getHora() + ", Humedad: " + temp.getDato());
				}
			}
		}

		if (!rangosViento.isEmpty()) {

			System.out.println("Rangos de viento:");
			for (Viento viento : rangosViento) {
				if (!viento.getHora().isEmpty() && !viento.getHorientacion().isEmpty() && !viento.getDato().isEmpty()) {
					System.out.println("Hora: " + viento.getHora() + ", Dirección del viento: "
							+ viento.getHorientacion() + ", Velocidad del viento: " + viento.getDato());
				}
			}
		}

		if (!ultravioleta.isEmpty()) {
			System.out.println("Índice ultravioleta: " + ultravioleta);
		}

		System.out.println();
	}

}
