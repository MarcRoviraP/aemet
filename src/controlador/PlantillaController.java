package controlador;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Dia;

public class PlantillaController {

	@FXML
	private Label barra;

	@FXML
	private Label barra1;

	@FXML
	private Label barra2;

	@FXML
	private Label estadoClima;

	@FXML
	private Label fechaLabel;

	@FXML
	private Label humedadMax;

	@FXML
	private Label humedadMin;

	@FXML
	private Label lluviaMax;

	@FXML
	private Label sensMax;

	@FXML
	private Label sensMin;

	@FXML
	private Label tempMax;

	@FXML
	private Label tempMin;

	@FXML
	private Label ultravioletaLabel;

	@FXML
	private Label vientoLabel;

	@FXML
	private ImageView viewClima;

	@FXML
	private ImageView vientoH;

	@FXML
	private ImageView nubes;

	public void refresecarInformacion(Dia d) {

		tempMax.setText(d.getTemperaturaMaxima());
		tempMin.setText(d.getTemperaturaMinima());
		sensMax.setText(d.getSensacionMaxima());
		sensMin.setText(d.getSensacionMinima());
		humedadMax.setText(d.getHumedadMaxima());
		humedadMin.setText(d.getHumedadMinima());
		estadoClima.setText(d.getEstadoClima());
		fechaLabel.setText(d.getFecha());
		ultravioletaLabel.setText(d.getUltravioleta());
		vientoLabel.setText(d.getVientoGeneral() + " Km/h");
		actualizarEstadoClima(d);
		actualizarViento(d);
		actualizarNubes(d);

		try {

			lluviaMax.setText(d.getPrecipitacionMaxima() + "%");
		} catch (Exception e) {
			lluviaMax.setText("0%");
		}
	}

	private void actualizarNubes(Dia d) {

		int max;
		try {
			max = d.getPrecipitacionMaxima().isEmpty() ? 0 : Integer.parseInt(d.getPrecipitacionMaxima());

		} catch (NullPointerException e) {

			d.setPrecipitacionMaxima("0");
			max = 0;
		}
		Image img;
		if (max >= 0 && max <= 25) {

			img = new Image(getClass().getResourceAsStream("../utilidades/img/nubes/nubes.png"));
		} else if (max > 25 && max <= 60) {

			img = new Image(getClass().getResourceAsStream("../utilidades/img/nubes/lluvia.png"));
		} else {
			img = new Image(getClass().getResourceAsStream("../utilidades/img/nubes/tormenta.png"));

		}

		nubes.setImage(img);
	}

	private void actualizarViento(Dia d) {
		Image img = new Image(getClass().getResourceAsStream("../utilidades/img/viento/" + d.getVientoH() + ".png"));

		vientoH.setImage(img);
	}

	private void actualizarEstadoClima(Dia d) {
		
		String rutaDia = "../utilidades/img/estados/" + d.getEstadoClima() + ".png";
		String rutaNoche = "../utilidades/img/estados/" + d.getEstadoClima() + " noche.png";
		String ruta;
		
		if (LocalTime.now().isAfter(LocalTime.parse("22:00:00")) && LocalTime.now().isBefore(LocalTime.parse("08:00:00"))) {
			

			ruta = rutaNoche;
		}else {
			ruta = rutaDia;
			
		}
		Image img;
		
		try {
			
			img = new Image(getClass().getResourceAsStream(ruta));
		} catch (NullPointerException e) {
			img = new Image(getClass().getResourceAsStream(rutaDia));
		}

		viewClima.setImage(img);
	}
}
