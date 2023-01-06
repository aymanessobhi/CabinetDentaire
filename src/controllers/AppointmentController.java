package controllers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Blob;

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
import javafx.scene.image.Image;
import models.Appointment;

public class AppointmentController implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	@FXML
	private Button btn_add;

	@FXML
	private Button btn_edit;

	@FXML
	private Button btn_delete;

	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXComboBox<String> cb_dentiste;

	@FXML
	private JFXComboBox<String> cb_patient;

	@FXML
	private JFXComboBox<String> cb_time;

	@FXML
	private TableView<Appointment> table_appointment;

	@FXML
	private TableColumn<Appointment, Integer> tc_idApp;

	@FXML
	private TableColumn<Appointment, String> tc_patient;

	@FXML
	private TableColumn<Appointment, String> tc_dentiste;

	@FXML
	private TableColumn<Appointment, Date> tc_date;

	@FXML
	private TableColumn<Appointment, String> tc_time;

	@FXML
	private TableColumn<Appointment, String> tc_statut;

	@FXML
	private Button btn_cancel;

	@FXML
	private Button btn_active;

	@FXML
	private JFXTextField txt_searchid;

	@FXML
	void ActiveAppointment() {
		String statut = "Actif";
		String sql = "UPDATE rendezvous set statut = ? where id_rdv ='" + txt_searchid.getText() + "'";
		if (!txt_searchid.getText().equals("")) {
			try {
				st = cnx.prepareStatement(sql);
				st.setString(1, statut);
				st.executeUpdate();
				showAppointment();
				date.setValue(null);
				cb_dentiste.setValue("dentiste");
				cb_patient.setValue("patient");
				cb_time.setValue("time");
				Alert alert = new Alert(AlertType.CONFIRMATION, "Statut regler!", ButtonType.OK);
				alert.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Statut non regler", ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void addAppointment() {
		java.util.Date datee = java.util.Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date sqlDate = new Date(datee.getTime());
		String den = cb_dentiste.getValue();
		String sql1 = "select id_dentiste from dentiste where nomComplet ='" + den + "'";
		int dentiste = 0;
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if (result.next()) {
				dentiste = result.getInt("id_dentiste");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Impossible d'ajouter", ButtonType.OK);
			alert.showAndWait();
		}
		String pat = cb_patient.getValue();
		String sql2 = "select id_patient from patient where nomComplet ='" + pat + "'";
		int patient = 0;
		try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			if (result.next()) {
				patient = result.getInt("id_patient");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Impossible d'ajouter", ButtonType.OK);
			alert.showAndWait();
		}
		String time = cb_time.getValue();

		String timeToken = null;
		int dentistToken = 0;
		Date dateToken = null;
		boolean flag = false;
		String sql4 = "select dentiste,time,date from rendezvous where dentiste = '" + dentiste + "'";
		try {
			st = cnx.prepareStatement(sql4);
			result = st.executeQuery();
			while (result.next()) {
				timeToken = result.getString("time");
				dentistToken = result.getInt("dentiste");
				dateToken = result.getDate("date");
				if (time.equals(timeToken) && dentiste == dentistToken && sqlDate.equals(dateToken)) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int m = 0;
		System.out.println(time);
		System.out.println(timeToken);
		System.out.println(time.equals(timeToken));
		System.out.println(dentiste);
		if (flag == true) {
			Alert alert = new Alert(AlertType.ERROR, "Rendez-vous deja occupee", ButtonType.OK);
			alert.showAndWait();
		}
		if (flag == false) {
				String sql = "insert into rendezvous(date,time,dentiste,patient) values (?,?,?,?)";
				try {
					st = cnx.prepareStatement(sql);
					st.setDate(1, sqlDate);
					st.setString(2, time);
					st.setInt(3, dentiste);
					st.setInt(4, patient);
					st.executeUpdate();
					showAppointment();
					txt_searchid.setText("");
					date.setValue(null);
					cb_dentiste.setValue("dentiste");
					cb_patient.setValue("patient");
					cb_time.setValue("time");
					m = 1;
					Alert alert = new Alert(AlertType.CONFIRMATION, "Rendez-vous Ajouter!", ButtonType.OK);
					alert.showAndWait();
				} catch (Exception e) {
					
				}
			}
		}
				

	@FXML
	void cancelAppointment() {
		String statut = "Annulé";
		String sql = "UPDATE rendezvous set statut = ? where id_rdv ='" + txt_searchid.getText() + "'";
		if (!txt_searchid.getText().equals("")) {
			try {
				st = cnx.prepareStatement(sql);
				st.setString(1, statut);
				st.executeUpdate();
				showAppointment();
				date.setValue(null);
				cb_dentiste.setValue("dentiste");
				cb_patient.setValue("patient");
				cb_time.setValue("time");
				Alert alert = new Alert(AlertType.CONFIRMATION, "Statut regler!", ButtonType.OK);
				alert.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Statut non regler", ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void deleteAppointment() {
		String sql = "DELETE FROM rendezvous WHERE id_rdv ='" + txt_searchid.getText() + "'";
		int m = 0;
		if (!txt_searchid.getText().equals("")) {
			try {

				st = cnx.prepareStatement(sql);
				st.executeUpdate();
				showAppointment();
				date.setValue(null);
				cb_dentiste.setValue("dentiste");
				cb_patient.setValue("patient");
				cb_time.setValue("time");
				Alert alert = new Alert(AlertType.CONFIRMATION, "Rendez-vous supprimee!", ButtonType.OK);
				alert.showAndWait();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs", ButtonType.OK);
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Impossible de supprimer", ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void editAppointment() {
		String den = cb_dentiste.getValue();
		String sql1 = "select id_dentiste from dentiste where nomComplet ='" + den + "'";
		int dentiste = 0;
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if (result.next()) {
				dentiste = result.getInt("id_dentiste");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String pat = cb_patient.getValue();
		String sql2 = "select id_patient from patient where nomComplet ='" + pat + "'";
		int patient = 0;
		try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			if (result.next()) {
				patient = result.getInt("id_patient");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String time = cb_time.getValue();
		String sql = "UPDATE rendezvous set date=?,time=?,dentiste=?,patient=? where id_rdv ='" + txt_searchid.getText()
				+ "'";
		if (!txt_searchid.getText().equals("")) {
			try {
				st = cnx.prepareStatement(sql);
				java.util.Date datee = java.util.Date
						.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date sqlDate = new Date(datee.getTime());
				st.setDate(1, sqlDate);
				st.setString(2, time);
				st.setInt(3, dentiste);
				st.setInt(4, patient);
				st.executeUpdate();
				showAppointment();
				date.setValue(null);
				cb_dentiste.setValue("dentiste");
				cb_patient.setValue("patient");
				cb_time.setValue("time");
				Alert alert = new Alert(AlertType.CONFIRMATION, "Rendez-vous modifier!", ButtonType.OK);
				alert.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Impossible de modifier", ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void searchId() {
		String sql = "SELECT id_rdv ,date,time, UPPER(concat(dentiste.nom,\" \",dentiste.prenom)) as dentiste,UPPER(concat(patient.nom,\" \",patient.prenom)) as patient FROM rendezvous,patient,dentiste WHERE rendezvous.dentiste=dentiste.id_dentiste and rendezvous.patient=patient.id_patient and id_rdv = '"
				+ txt_searchid.getText() + "'";
		int m = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				int id = result.getInt("id_rdv");
				txt_searchid.setText(String.valueOf(id));
				date.setValue(result.getDate("date").toLocalDate());
				cb_time.setValue(result.getString("time"));
				cb_dentiste.setValue(result.getString("dentiste"));
				cb_patient.setValue(result.getString("patient"));
				m = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR, "Aucun RDV existe avec Numero = " + txt_searchid.getText() + "",
					ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void searchDate() {
		showAppointment();
	}

	@FXML
	void tableAppointmentEvent() {
		Appointment appointment = table_appointment.getSelectionModel().getSelectedItem();
		String sql = "SELECT id_rdv ,date,time, UPPER(concat(dentiste.nom,\" \",dentiste.prenom)) as dentiste,UPPER(concat(patient.nom,\" \",patient.prenom)) as patient ,statut FROM rendezvous,patient,dentiste WHERE rendezvous.dentiste=dentiste.id_dentiste and rendezvous.patient=patient.id_patient and id_rdv = ?";
		try {
			st = cnx.prepareStatement(sql);
			st.setInt(1, appointment.getId());
			result = st.executeQuery();
			while (result.next()) {
				int id = result.getInt("id_rdv");
				txt_searchid.setText(String.valueOf(id));
				date.setValue(result.getDate("date").toLocalDate());
				cb_time.setValue(result.getString("time"));
				cb_dentiste.setValue(result.getString("dentiste"));
				cb_patient.setValue(result.getString("patient"));
			}
		} catch (Exception e) {

		}
	}

	public ObservableList<Appointment> list = FXCollections.observableArrayList();

	private void showAppointment() {
		table_appointment.getItems().clear();
		String sql = "SELECT id_rdv ,date,time,concat(dentiste.nom,\" \",dentiste.prenom),concat(patient.nom,\" \",patient.prenom),statut FROM rendezvous,patient,dentiste WHERE rendezvous.dentiste=dentiste.id_dentiste and rendezvous.patient=patient.id_patient and date = '"
				+ date.getValue() + "'";
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
		tc_idApp.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
		tc_date.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("date"));
		tc_time.setCellValueFactory(new PropertyValueFactory<Appointment, String>("time"));
		tc_dentiste.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dentiste"));
		tc_patient.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patient"));
		tc_statut.setCellValueFactory(new PropertyValueFactory<Appointment, String>("statut"));
		table_appointment.setItems(list);
	}

	void fillInDentist() {
		String sql = "select UPPER(concat(nom,\" \",prenom)) from dentiste Order by id_dentiste desc";
		List<String> dentists = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				dentists.add(result.getString("UPPER(concat(nom,\" \",prenom))"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb_dentiste.setItems(FXCollections.observableArrayList(dentists));
	}

	void fillInPatient() {
		String sql = "select UPPER(concat(nom,\" \",prenom)) from patient Order by id_patient desc";
		List<String> patients = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				patients.add(result.getString("UPPER(concat(nom,\" \",prenom))"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb_patient.setItems(FXCollections.observableArrayList(patients));
	}

	void fillInTime() {
		List<String> times = new ArrayList<String>();
		times.add("8h30-9h00");
		times.add("9h00-9h30");
		times.add("9h30-10h00");
		times.add("10h00-10h30");
		times.add("10h30-11h00");
		times.add("11h00-11h30");
		times.add("11h30-12h00");
		times.add("14h30-15h00");
		times.add("15h30-16h00");
		times.add("16h00-16h30");
		cb_time.setItems(FXCollections.observableArrayList(times));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx = DBconnection.getConnection();
		date.setValue(LocalDate.now());
		showAppointment();
		fillInDentist();
		fillInPatient();
		fillInTime();
	}
}
