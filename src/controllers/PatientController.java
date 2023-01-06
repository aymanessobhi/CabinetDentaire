package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Patient;

public class PatientController implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
    @FXML
    private JFXTextField txt_nom;

    @FXML
    private JFXTextField txt_prenom;

    @FXML
    private JFXTextField txt_adresse;

    @FXML
    private JFXTextField txt_age;

    @FXML
    private JFXTextField txt_tele;

    @FXML
    private JFXComboBox<String> cb_sexe;

    @FXML
    private JFXTextArea txt_motif;

    @FXML
    private TableView<Patient> table_patient;

    @FXML
    private TableColumn<Patient, Integer> tc_id;

    @FXML
    private TableColumn<Patient, String> tc_nomComplet;

    @FXML
    private TableColumn<Patient, String> tc_adre;

    @FXML
    private TableColumn<Patient, Integer> tc_age;

    @FXML
    private TableColumn<Patient, String> tc_sexe;

    @FXML
    private TableColumn<Patient, Integer> tc_tele;

    @FXML
    private TableColumn<Patient, String> tc_motif;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_edit;

    @FXML
    private Button btn_delete;

    @FXML
    private JFXTextField txt_searchid;

    public ObservableList<Patient> data = FXCollections.observableArrayList();
    
    @FXML
    void addPatient() {
    	String nom = txt_nom.getText();
    	String prenom = txt_prenom.getText();
    	String adresse = txt_adresse.getText();
    	String agee= txt_age.getText();
    	int age = Integer.parseInt(agee);
    	String sexe = cb_sexe.getValue();
    	String tele= txt_tele.getText();
    	int telephone = Integer.parseInt(tele);
    	String motif = txt_motif.getText(); 
    	String nomComplet = nom.toUpperCase()+" "+prenom.toUpperCase();
    	String sql = "insert into patient(nom,prenom,adresse,age,sexe,telephone,motif_consultation,nomComplet) values(?,?,?,?,?,?,?,?)";
		int m=0;
		if (!nom.equals("") && !prenom.equals("") && !adresse.equals("") && !sexe.equals("") && !motif.equals("") && !agee.equals(null)) {
    	try {
    		st = cnx.prepareStatement(sql);
			st.setString(1, nom);
			st.setString(2, prenom);
			st.setString(3, adresse);
			st.setInt(4, age);
			st.setString(5, sexe);
			st.setInt(6, telephone);
			st.setString(7, motif);
			st.setString(8, nomComplet);
			st.execute();
			showAllPatient();
			txt_nom.setText("");
			txt_prenom.setText("");
			txt_searchid.setText("");
			txt_adresse.setText("");
			txt_age.setText("0");
			cb_sexe.setValue("sexe");
			txt_tele.setText("06");
			txt_motif.setText("");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Patient ajouter!", ButtonType.OK);
			alert.showAndWait();
			m=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(m==0){
    		Alert alert = new Alert(AlertType.WARNING, "Impossible d'ajouter", ButtonType.OK);
			alert.showAndWait();
    	}
    }

    @FXML
    void deletePatient() {
    	String sql = "delete from patient where id_patient ='" + txt_searchid.getText() + "'";
		int m=0;
		if (!txt_searchid.getText().equals("")) {
    	try {
			st = cnx.prepareStatement(sql);
			st.executeUpdate();
			showAllPatient();
			txt_nom.setText("");
			txt_prenom.setText("");
			txt_searchid.setText("");
			txt_adresse.setText("");
			txt_age.setText("");
			cb_sexe.setValue("sexe");
			txt_tele.setText("");
			txt_motif.setText("");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Patient supprimee!", ButtonType.OK);
			alert.showAndWait();
			m=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(m==0){
    		Alert alert = new Alert(AlertType.WARNING, "Impossible de supprimer", ButtonType.OK);
			alert.showAndWait();
    	}
    }

    @FXML
    void editPatient() {
    	String nom = txt_nom.getText();
    	String prenom = txt_prenom.getText();
    	String adresse = txt_adresse.getText();
    	String agee= txt_age.getText();
    	int age = Integer.parseInt(agee);
    	String sexe = cb_sexe.getValue();
    	String tele= txt_tele.getText();
    	int telephone = Integer.parseInt(tele);
    	String motif = cb_sexe.getValue();
    	int m=0;
    	String sql = "update patient set nom = ?,prenom=?,adresse=?,age=?,sexe=?,telephone=?,motif_consultation=? where id_patient  ='"+ txt_searchid.getText() + "'";
    	if (!nom.equals("") && !prenom.equals("") && !adresse.equals("") && !sexe.equals("") && !motif.equals("") ) {
    	try {
			st = cnx.prepareStatement(sql);
			st.setString(1, nom);
			st.setString(2, prenom);
			st.setString(3, adresse);
			st.setInt(4, age);
			st.setString(5, sexe);
			st.setInt(6, telephone);
			st.setString(7, motif);
			st.executeUpdate();
			showAllPatient();
			txt_nom.setText("");
			txt_prenom.setText("");
			txt_searchid.setText("");
			txt_adresse.setText("");
			txt_age.setText("0");
			cb_sexe.setValue("sexe");
			txt_tele.setText("06");
			txt_motif.setText("");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Patient bien Modifier!", ButtonType.OK);
			alert.showAndWait();
			m=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}}
    	if(m==0){
    		Alert alert = new Alert(AlertType.WARNING, "Impossible de modifier", ButtonType.OK);
			alert.showAndWait();
    	}
    	}
    @FXML
    void searchPatient() {
    	String sql = "select * from patient where id_patient = '" + txt_searchid.getText()+ "'";
		int m = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				txt_nom.setText(result.getString("nom"));
				txt_prenom.setText(result.getString("prenom"));
				txt_adresse.setText(result.getString("adresse"));
				int age1 = result.getInt("age");
				txt_age.setText(String.valueOf(age1));
				cb_sexe.setValue(result.getString("sexe"));
				int tele = result.getInt("telephone");
				txt_tele.setText(String.valueOf(tele));
				txt_motif.setText(result.getString("motif_consultation"));
				m = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR,
					"Aucun patient existe avec ID = " + txt_searchid.getText() + "", ButtonType.OK);
			alert.showAndWait();
		}
    }
    @FXML
    void remplirSex() {
    	List<String> sexe = new ArrayList<String>();
    	sexe.add("homme");sexe.add("femme");
    	cb_sexe.setItems(FXCollections.observableArrayList(sexe));
    }
    @FXML
    void tablePatienteEvent() {
    	List<String> sexe = new ArrayList<String>();
    	sexe.add("homme");sexe.add("femme");
    	cb_sexe.setItems(FXCollections.observableArrayList(sexe));
		Patient patient = table_patient.getSelectionModel().getSelectedItem();
		String sql = "select * from Patient where id_patient = ?";
		try {
			st = cnx.prepareStatement(sql);
			st.setInt(1,patient.getId());
			result = st.executeQuery();
			while (result.next()) {
				int id = result.getInt("id_patient");
				txt_searchid.setText(String.valueOf(id));
				txt_nom.setText(result.getString("nom"));
				txt_prenom.setText(result.getString("prenom"));
				txt_adresse.setText(result.getString("adresse"));
				int age1 = result.getInt("age");
				txt_age.setText(String.valueOf(age1));
				cb_sexe.setValue(result.getString("sexe"));
				int tele = result.getInt("telephone");
				txt_tele.setText(String.valueOf(tele));
				txt_motif.setText(result.getString("motif_consultation"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    private void showAllPatient() {
    	table_patient.getItems().clear();
		String sql = "SELECT id_patient, concat(nom,\" \",prenom), adresse, age, sexe, telephone, motif_consultation FROM patient";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				data.add(new Patient(result.getInt("id_patient"), result.getString("concat(nom,\" \",prenom)"),
				result.getString("adresse"), result.getInt("age"), result.getString("sexe"),result.getInt("telephone"),result.getString("motif_consultation")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tc_id.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
		tc_nomComplet.setCellValueFactory(new PropertyValueFactory<Patient, String>("nomComplet"));
		tc_adre.setCellValueFactory(new PropertyValueFactory<Patient, String>("adr"));
		tc_age.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("age"));
		tc_sexe.setCellValueFactory(new PropertyValueFactory<Patient, String>("sexe"));
		tc_tele.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("tele"));
		table_patient.setItems(data);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx = DBconnection.getConnection();
		showAllPatient();
		remplirSex();
		txt_age.setText("0");
		txt_tele.setText("06");
	}

}