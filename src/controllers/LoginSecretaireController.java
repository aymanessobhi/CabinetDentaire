package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.DBconnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginSecretaireController implements Initializable{
	private static final javafx.scene.control.ButtonType ButtonType = null;
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
    @FXML
    private VBox VBox;

    @FXML
    private JFXComboBox<String> cb_role;
    
    @FXML
    private JFXTextField txt_user;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXButton btn_connecter;
    private Parent fxml;
    
    @FXML
    void OpenHome() {
    	String nom = txt_user.getText();
		String pass = txt_password.getText();
		String rol = cb_role.getValue();
		String sql = "Select username,role,password from admin";
		int flag=0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(flag ==0) {
			while(result.next()) {
				if (nom.equals(result.getString("username")) && pass.equals(result.getString("password")) && rol.equals("Secretaire") && rol.equals(result.getString("role"))) {
					System.out.println("bien!");
					VBox.getScene().getWindow().hide();
					Stage home = new Stage();
					try {
						fxml = FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
						Scene scene = new Scene(fxml);
						home.setScene(scene);
						home.setResizable(false);
						home.setTitle("Gestion de cabinet dentaire.");
						home.show();
						flag=2;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if(nom.equals(result.getString("username")) && pass.equals(result.getString("password")) && rol.equals("Dentiste") && rol.equals(result.getString("role"))) {
					System.out.println("bien!");
					VBox.getScene().getWindow().hide();
					Stage home = new Stage();
					try {
						fxml = FXMLLoader.load(getClass().getResource("/interfaces/HomeDentist.fxml"));
						Scene scene = new Scene(fxml);
						home.setScene(scene);
						home.show();
						flag=2;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			if(flag==0) {
					Alert alert = new Alert(AlertType.ERROR, "nom de l'utilisateur ou mot de passe incorrect!",
							ButtonType.OK);
					alert.showAndWait();
					System.out.println("incorect");
				}
			}
		} catch (Exception e) {

		}
		
    }

    void fillRole() {
    	List<String> role = new ArrayList<String>();
    	role.add("Dentiste");
    	role.add("Secretaire");
    	cb_role.setItems(FXCollections.observableArrayList(role));
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		cnx = DBconnection.getConnection();
		fillRole();
	}

}
