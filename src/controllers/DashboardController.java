package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointment;
import models.Patient;

public class DashboardController implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
    @FXML
    private Label lbl_nbrPatient;

    @FXML
    private Label lab_nbrCons;

    @FXML
    private Label lab_nbreDentist;

    @FXML
    private Label lab_rdvActif;

    @FXML
    private TableView<Patient> table_patient;

    @FXML
    private TableColumn<Patient, Integer> tc_id;

    @FXML
    private TableColumn<Patient, String> tc_nom;

    @FXML
    private TableColumn<Patient, String> tc_prenom;

    @FXML
    private TableColumn<Patient, Integer> tc_age;
    
    @FXML
    private TableColumn<Patient, String> tc_sexe;

    @FXML
    private TableColumn<Patient, Integer> tc_tele;

    @FXML
    private TableView<Appointment> table_rdv;

    @FXML
    private TableColumn<Appointment, Integer> tc_rdv;

    @FXML
    private TableColumn<Appointment, String> tc_patient;

    @FXML
    private TableColumn<Appointment, String> tc_dentsit;

    @FXML
    private TableColumn<Appointment, String> tc_time;

    
    public ObservableList<Patient> data = FXCollections.observableArrayList();
    
    
    
    private void showPatient() {
    	table_patient.getItems().clear();
		String sql = "select id_patient,nom,prenom,age,sexe,telephone from patient Order by id_patient desc limit 12";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				data.add(new Patient(result.getInt("id_patient"), result.getString("nom"),
				result.getString("prenom"), result.getInt("age"), result.getString("sexe"),result.getInt("telephone")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tc_id.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
		tc_nom.setCellValueFactory(new PropertyValueFactory<Patient, String>("nom"));
		tc_prenom.setCellValueFactory(new PropertyValueFactory<Patient, String>("prenom"));
//		tc_age.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("age"));
		tc_sexe.setCellValueFactory(new PropertyValueFactory<Patient, String>("sexe"));
		tc_tele.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("tele"));
		table_patient.setItems(data);
	}
    public ObservableList<Appointment> list = FXCollections.observableArrayList();
    private void showNewAppointment() {
    	table_rdv.getItems().clear();
		String sql = "SELECT id_rdv ,date,time,concat(dentiste.nom,\" \",dentiste.prenom),concat(patient.nom,\" \",patient.prenom),statut FROM rendezvous,patient,dentiste WHERE rendezvous.dentiste=dentiste.id_dentiste and rendezvous.patient=patient.id_patient and statut ='actif' ";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new Appointment(result.getInt(1), result.getDate(2), result.getString(3), result.getString(4),
						result.getString(5), result.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tc_rdv.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
		tc_time.setCellValueFactory(new PropertyValueFactory<Appointment, String>("time"));
		tc_dentsit.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dentiste"));
		tc_patient.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patient"));
		table_rdv.setItems(list);
	}
    private void showNumber() {
    	String sql = "select count(*) from patient";
		int i = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				i = result.getInt(1);
			}
			lbl_nbrPatient.setText(Integer.toString(i));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql1 = "select count(*) from dentiste";
		int j = 0;
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if (result.next()) {
				j = result.getInt(1);
			}
			lab_nbreDentist.setText(Integer.toString(j));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql2 = "select count(*) from rendezvous where statut='actif'";
		int k = 0;
		try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			if (result.next()) {
				k = result.getInt(1);
			}
			lab_rdvActif.setText(Integer.toString(k));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql3 = "select count(*) from consultation";
		int z = 0;
		try {
			st = cnx.prepareStatement(sql3);
			result = st.executeQuery();
			if (result.next()) {
				z = result.getInt(1);
			}
			lab_nbrCons.setText(Integer.toString(z));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = DBconnection.getConnection();
		showPatient();
		showNumber();
		showNewAppointment();
	}
	

}