package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.controlsfx.control.textfield.TextFields;

import excepciones.NoEncontradoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import modelo.*;

public class AemetController implements Initializable {

	private Aemet aemet;
	private Queue<String> ultimasBusquedas;

	@FXML
	private ImageView logoAemet;
	@FXML
	private Button btnPulsa;

	@FXML
	private TextField txtField;
	@FXML
	private Label titulo;

	@FXML
	private AnchorPane panelPlantilla;

	@FXML
	private ComboBox<String> comboCA;

	@FXML
	private ComboBox<String> comboProv;
	@FXML
	private ComboBox<String> comboCiudad;
	@FXML
	private Button refrescar;
	@FXML
	private Button dia1;
	@FXML
	private Button dia2;
	@FXML
	private Button dia3;

	@FXML
	private Accordion acordeonHoras;
	@FXML
	private Accordion acordeonDias;

	@FXML
	private PlantillaController plantilla_1Controller;
	@FXML
	private PlantillaController plantilla_2Controller;
	@FXML
	private PlantillaController plantilla_3Controller;
	@FXML
	private PlantillaController plantilla_4Controller;
	@FXML
	private PlantillaController plantilla_5Controller;
	@FXML
	private PlantillaController plantilla_6Controller;
	@FXML
	private PlantillaController plantilla_7Controller;

	@FXML
	private PlantillaHoraController plantillaHora_1Controller;
	@FXML
	private PlantillaHoraController plantillaHora_2Controller;
	@FXML
	private PlantillaHoraController plantillaHora_3Controller;
	@FXML
	private PlantillaHoraController plantillaHora_4Controller;
	@FXML
	private PlantillaHoraController plantillaHora_5Controller;
	@FXML
	private PlantillaHoraController plantillaHora_6Controller;
	@FXML
	private PlantillaHoraController plantillaHora_7Controller;
	@FXML
	private PlantillaHoraController plantillaHora_8Controller;
	@FXML
	private PlantillaHoraController plantillaHora_9Controller;
	@FXML
	private PlantillaHoraController plantillaHora_10Controller;
	@FXML
	private PlantillaHoraController plantillaHora_11Controller;
	@FXML
	private PlantillaHoraController plantillaHora_12Controller;
	@FXML
	private PlantillaHoraController plantillaHora_13Controller;
	@FXML
	private PlantillaHoraController plantillaHora_14Controller;
	@FXML
	private PlantillaHoraController plantillaHora_15Controller;
	@FXML
	private PlantillaHoraController plantillaHora_16Controller;
	@FXML
	private PlantillaHoraController plantillaHora_17Controller;
	@FXML
	private PlantillaHoraController plantillaHora_18Controller;
	@FXML
	private PlantillaHoraController plantillaHora_19Controller;
	@FXML
	private PlantillaHoraController plantillaHora_20Controller;
	@FXML
	private PlantillaHoraController plantillaHora_21Controller;
	@FXML
	private PlantillaHoraController plantillaHora_22Controller;
	@FXML
	private PlantillaHoraController plantillaHora_23Controller;
	@FXML
	private PlantillaHoraController plantillaHora_24Controller;

	@FXML TitledPane panelDiasTit;
	@Override

	public void initialize(URL arg0, ResourceBundle arg1) {
		acordeonHoras.setVisible(false);


		if (dia1.getText().equals("Dia1")) {
			acordeonDias.setVisible(false);

		}
		btnPulsa.setDisable(true);
		btnPulsa.setVisible(false);
		ultimasBusquedas = new LinkedList<String>();
		aemet = new Aemet();
		aemet.guardarCA();
		HashSet<String> listaNombre = new HashSet<String>(aemet.getListaNombres());
		TreeSet<String> listaCA = new TreeSet<String>(aemet.getListaCA());
		TextFields.bindAutoCompletion(txtField, listaNombre);

		// Al puslar intro hace cosas
		txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {

					asignarPoblacion(txtField.getText());
				}
			}
		});

		ObservableList<String> listaCAObservable = FXCollections.observableArrayList(listaCA);

		comboCA.setItems(listaCAObservable);

		comboBoxMenu(comboCA);
		comboCA.getSelectionModel().selectedItemProperty().addListener((comboBox, valorAnterior, valorActual) -> {
			if (valorActual != null) {
				try {
					aemet.guardarProv(valorActual);
				} catch (NoEncontradoException e) {

					clear();
//					e.mostrarError(btnPulsa);
				}
				TreeSet<String> listaProv = new TreeSet<String>(aemet.getListaProv());

				ObservableList<String> listaProvObservable = FXCollections.observableArrayList(listaProv);
				//---
				TreeSet<String> listaVuida = new TreeSet<String>();
				
				ObservableList<String> listaVuidaObservableList = FXCollections.observableArrayList(listaVuida);

				comboProv.setItems(listaProvObservable);
				comboCiudad.setItems(listaVuidaObservableList);
				//--
				comboBoxMenu(comboProv);
				comboProv.getSelectionModel().selectedItemProperty()
						.addListener((comboBox2, valorAnterior2, valorActual2) -> {

							if (valorActual2 != null) {
								try {
									aemet.guardarCiudad(valorActual2);
								} catch (NoEncontradoException e) {
									clear();
									e.mostrarError(btnPulsa);

								}
								TreeSet<String> listaCiudades = new TreeSet<String>(aemet.getListaCiudades());

								ObservableList<String> listaCiudadesObservable = FXCollections
										.observableArrayList(listaCiudades);

								comboCiudad.setItems(listaCiudadesObservable);

								comboBoxMenu(comboCiudad);
								comboCiudad.getSelectionModel().selectedItemProperty()
										.addListener((comboBox3, valorAnterior3, valorActual3) -> {

											if (valorActual3 != null) {

												asignarPoblacion(valorActual3);
											} else {
												clear();
											}
										});

							} else {
								clear();
							}
						});
			}
		});
		clear();
		dia1.setOnMouseClicked((event) -> asignarPoblacion(dia1.getText()));
		dia2.setOnMouseClicked((event) -> asignarPoblacion(dia2.getText()));
		dia3.setOnMouseClicked((event) -> asignarPoblacion(dia3.getText()));

	}

	public void comboBoxMenu(ComboBox<String> combo) {
		combo.getEditor().setOnMouseClicked(event -> {
			combo.getEditor().selectAll();
			combo.show();
		});
		combo.setOnKeyReleased(event -> {
			String tecla = combo.getEditor().getText() + event.getCharacter();
			String teclaMinus;
			if (!tecla.isEmpty()) {

				teclaMinus = tecla.toLowerCase();
				ObservableList<String> llista = combo.getItems();

				for (String string : llista) {

					if (string.toLowerCase().charAt(0) == teclaMinus.charAt(0)) {

						if (tecla.length() == 2) {
							combo.getSelectionModel().select(string);
							combo.getEditor().setText(string);
							combo.show();
							combo.getEditor().positionCaret(string.length());
							return;
						}
					}
				}
			}
		});
	}

	public void clear() {
		acordeonHoras.setVisible(false);
		titulo.setVisible(false);
		panelPlantilla.setVisible(false);
		logoAemet.setVisible(true);
	}

	public void asignarPoblacion(String info) {

		try {

			dia1.setVisible(true);
			dia2.setVisible(true);
			dia3.setVisible(true);

			acordeonHoras.setVisible(true);
			logoAemet.setVisible(false);
			panelPlantilla.setVisible(true);

			aemet.buscarPorNombre(info);
			titulo.setText(info);
			txtField.clear();
			aemet.descargarDias();
			aemet.descargarHoras();
			aemet.informacioHoraXML();
			aemet.informacioDiaXML();
			try {

				ArrayList<Dia> infoDias = new ArrayList<Dia>(aemet.getInformacionDias());
				this.plantilla_1Controller.refresecarInformacion(infoDias.get(0));
				this.plantilla_2Controller.refresecarInformacion(infoDias.get(1));
				this.plantilla_3Controller.refresecarInformacion(infoDias.get(2));
				this.plantilla_4Controller.refresecarInformacion(infoDias.get(3));
				this.plantilla_5Controller.refresecarInformacion(infoDias.get(4));
				this.plantilla_6Controller.refresecarInformacion(infoDias.get(5));
				this.plantilla_7Controller.refresecarInformacion(infoDias.get(6));
				titulo.setVisible(true);
			} catch (IndexOutOfBoundsException e) {
			}

		ArrayList<Hora> infoHores = new ArrayList<Hora>(aemet.getInformacionHoras());

		try {

			Hora hora = infoHores.get(1);
			this.plantillaHora_1Controller.refrescarInfo(hora, 0);
			this.plantillaHora_2Controller.refrescarInfo(hora, 1);
			this.plantillaHora_3Controller.refrescarInfo(hora, 2);
			this.plantillaHora_4Controller.refrescarInfo(hora, 3);
			this.plantillaHora_5Controller.refrescarInfo(hora, 4);
			this.plantillaHora_6Controller.refrescarInfo(hora, 5);
			this.plantillaHora_7Controller.refrescarInfo(hora, 6);
			this.plantillaHora_8Controller.refrescarInfo(hora, 7);
			this.plantillaHora_9Controller.refrescarInfo(hora, 8);
			this.plantillaHora_10Controller.refrescarInfo(hora, 9);
			this.plantillaHora_11Controller.refrescarInfo(hora, 10);
			this.plantillaHora_12Controller.refrescarInfo(hora, 11);
			this.plantillaHora_13Controller.refrescarInfo(hora, 12);
			this.plantillaHora_14Controller.refrescarInfo(hora, 13);
			this.plantillaHora_15Controller.refrescarInfo(hora, 14);
			this.plantillaHora_16Controller.refrescarInfo(hora, 15);
			this.plantillaHora_17Controller.refrescarInfo(hora, 16);
			this.plantillaHora_18Controller.refrescarInfo(hora, 17);
			this.plantillaHora_19Controller.refrescarInfo(hora, 18);
			this.plantillaHora_20Controller.refrescarInfo(hora, 19);
			this.plantillaHora_21Controller.refrescarInfo(hora, 20);
			this.plantillaHora_22Controller.refrescarInfo(hora, 21);
			this.plantillaHora_23Controller.refrescarInfo(hora, 22);
			this.plantillaHora_24Controller.refrescarInfo(hora, 23);

		} catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		if (!ultimasBusquedas.contains(info)) {

			ultimasBusquedas.add(info);

			if (!ultimasBusquedas.isEmpty()) {

				if (ultimasBusquedas.size() > 3) {

					ultimasBusquedas.remove();
				}

			}

			ArrayList<String> llista = new ArrayList<String>(ultimasBusquedas);
			acordeonDias.setVisible(true);

			try {
				dia1.setText(llista.get(0));
			} catch (IndexOutOfBoundsException e) {
				dia1.setVisible(false);
			}
			try {

				dia2.setText(llista.get(1));
			} catch (Exception e) {
				dia2.setVisible(false);
			}
			try {

				dia3.setText(llista.get(2));
			} catch (Exception e) {
				dia3.setVisible(false);
			}

		}
		} catch (NoEncontradoException e) {
			clear();
			e.mostrarError(btnPulsa);
		}
	}

}
