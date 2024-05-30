package excepciones;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;

public class NoEncontradoException extends Exception{
	
	public void mostrarError(Control btn) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Poblacion incorrecta");
		alert.setHeaderText("Porfavor introduzca una poblacion valida.");

		alert.showAndWait();		
	}

}
