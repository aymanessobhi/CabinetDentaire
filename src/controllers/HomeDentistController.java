package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeDentistController implements Initializable{
	private Parent fxml;
	@FXML
    private AnchorPane root;
	

	@FXML
    private Button logoutbtn;
	
    @FXML
    void Consultations() {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Sessions.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void Dashborad() {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Dashboard.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void logOut() {
    	Stage stage = (Stage) logoutbtn.getScene().getWindow();
    	stage.close();
    	Stage login = new Stage();
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/LoginSecretaire.fxml"));
			Scene scene = new Scene(fxml);
			login.setScene(scene);
			login.setResizable(false);
			login.setTitle("Gestion de cabinet dentaire.");
			login.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Dashboard.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
