package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController implements Initializable{
	private Parent fxml;
	
	@FXML
    private Button logoutButton;

	@FXML
    private AnchorPane root;

    @FXML
    void Appointments() {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Appointments.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void Billing() {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Billign.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

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
    void Dentists() {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Dentists.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void Patients() {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Patients.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void logOut() {
    	
        
    }
    @FXML
    void logout() {
    	Stage stage = (Stage) logoutButton.getScene().getWindow();
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
	public void initialize(URL location, ResourceBundle resources) {
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

