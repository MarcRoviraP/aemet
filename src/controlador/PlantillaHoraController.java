package controlador;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import modelo.Dia;
import modelo.Hora;

public class PlantillaHoraController {


    @FXML
    private ImageView lluviaIMG;
    @FXML
    private ImageView climaIMG;
	@FXML
	private AnchorPane panelPlantilla;
    @FXML
    private Label horaLbl;

    @FXML
    private Label humedadLbl;

    @FXML
    private Label precipitacionLbl;

    @FXML
    private Label temperaturaLbl;

    @FXML
    private Label termicaLbl;

    @FXML
    private Label vientoLbl;



    public void refrescarInfo (Hora hora,int pos){
    	
    	try {
    		horaLbl.setText(hora.getHora(hora.gethTemperatura().get(pos)+"") + ":00");
    		temperaturaLbl.setText(hora.quitarHora(hora.gethTemperatura().get(pos) + "º"));
    		humedadLbl.setText(hora.quitarHora(hora.gethHumedad().get(pos)+ "%"));
    		precipitacionLbl.setText(hora.quitarHora(hora.gethPrecipitacion().get(pos)+ "%"));
    		termicaLbl.setText(hora.quitarHora(hora.gethSensacionTermica().get(pos)+ "º"));
    		vientoLbl.setText(hora.gethViento().get(pos)+ "km/h");
    		actualizarEstadoClima(hora.quitarHora(hora.gethEstadoClima().get(pos)+"").replaceAll("\\s+$", ""));
    		
    		try {
				
    		actualizarNubes(Double.parseDouble(hora.quitarHora(hora.gethPrecipitacion().get(pos)+ "")));
    		} catch (NumberFormatException e) {
    			precipitacionLbl.setText("0%");
    			// TODO: handle exception
    		}
    	
    	} catch (IndexOutOfBoundsException e) {

			panelPlantilla.setVisible(false);
		}
    }
    
	private void actualizarEstadoClima(String clima) {
		
		String rutaDia = "../utilidades/img/estados/" + clima + ".png";
		String rutaNoche = "../utilidades/img/estados/" + clima + " noche.png";
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
			// TODO: handle exception
		}

		climaIMG.setImage(img);
	}
	private void actualizarNubes(double lluvia) {


		Image img;
		if (lluvia >= 0 && lluvia <= 25) {

			img = new Image(getClass().getResourceAsStream("../utilidades/img/nubes/nubes.png"));
		} else if (lluvia > 25 && lluvia <= 60) {

			img = new Image(getClass().getResourceAsStream("../utilidades/img/nubes/lluvia.png"));
		} else {
			img = new Image(getClass().getResourceAsStream("../utilidades/img/nubes/tormenta.png"));

		}

		lluviaIMG.setImage(img);
	}
}
