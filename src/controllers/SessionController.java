package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Session;


public class SessionController implements Initializable  {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	@FXML
    private Button btn_delete;
	
    @FXML
    private TableView<Session> table_consultaion;

    @FXML
    private TableColumn<Session, String> tc_rdv;

    @FXML
    private TableColumn<Session, String> tc_patient;

    @FXML
    private TableColumn<Session, String> tc_dentiste;

    @FXML
    private TableColumn<Session, Date> tc_date;

    @FXML
    private TableColumn<Session, Integer> tc_idConsultation;

    @FXML
    private Button btn_create;

    @FXML
    private JFXTextField txt_searchId;

    @FXML
    private Button btn_show;
    
    @FXML
    private Label lab_rdv;
    
    @FXML
    private JFXDatePicker datePicker;
    
    @FXML
    private JFXComboBox<Integer> cb_rdv;

    @FXML
    private JFXTextField txt_rdv;
    
    @FXML
    private Button btn_edit;

    private Parent fxml;
    @FXML
    void createConsultation() {
    	String rdv = txt_rdv.getText();
    	int rendezvous = Integer.parseInt(rdv);
    	String sql = "INSERT INTO consultation (rendezvous, dentiste, patient) SELECT id_rdv, dentiste, patient FROM rendezvous where id_rdv = ? ";
    	String sql1 = "UPDATE consultation set dateConsultaion = ? where rendezvous ='"+txt_rdv.getText()+"'";
    	try {
			st = cnx.prepareStatement(sql);
			st.setInt(1,rendezvous);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			st = cnx.prepareStatement(sql1);
			java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date datedd = new Date(date.getTime());
			st.setDate(1,datedd);
			st.executeUpdate();
			showAllConsultation();
			txt_rdv.setText("");
			datePicker.setValue(null);
			Alert alert = new Alert(AlertType.CONFIRMATION, "Consultation ajouter avec succes!", ButtonType.OK);
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void deleteConsultation() {
    	String sql = "delete from consultation where id_consultation ='" + txt_searchId.getText() + "'";
		int m=0;
		if (!txt_searchId.getText().equals("")) {
    	try {
			st = cnx.prepareStatement(sql);
			st.executeUpdate();
			showAllConsultation();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Consultation supprimee!", ButtonType.OK);
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
    void editConsultation() {
    	String rdv = txt_rdv.getText();
    	String sql = "UPDATE consultation set dateConsultaion=? where rendezvous = '"+rdv+"' ";
    	try {
			st = cnx.prepareStatement(sql);
			java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date datedd = new Date(date.getTime());
			st.setDate(1,datedd);
			st.executeUpdate();
			showAllConsultation();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Bien Modification !!!", ButtonType.OK);
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    @FXML
    void searchConsultaion() {
    	String sql = "select id_consultation,dateConsultaion,rendezvous from consultation,rendezvous where consultation.rendezvous = rendezvous.id_rdv and id_consultation='"+txt_searchId.getText()+"'";
    	int m=0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				int id = result.getInt("id_consultation");
				txt_searchId.setText(String.valueOf(id));
				datePicker.setValue(result.getDate("dateConsultaion").toLocalDate());
				int id1 = result.getInt("rendezvous");
				txt_rdv.setText(String.valueOf(id1));
				m = 1;
				Alert alert = new Alert(AlertType.CONFIRMATION,"numero de consultation existe !! " +txt_searchId.getText() + "", ButtonType.OK);
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR,"Aucun consultation existe avec numero" +txt_searchId.getText()+ "", ButtonType.OK);
			alert.showAndWait();
		}
    }
    
    @FXML
    void searchRDV() {
    	String sql = "select id_rdv from rendezvous where statut = 'actif' and id_rdv='"+txt_rdv.getText()+"'";
    	int m=0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				int id = result.getInt("id_rdv");
				txt_rdv.setText(String.valueOf(id));
				m = 1;
				Alert alert = new Alert(AlertType.CONFIRMATION,"numero de RDV existe !! " +txt_rdv.getText() + "", ButtonType.OK);
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR,"Aucun RDV existe avec numero : "+txt_rdv.getText()+ " ou RDV annule", ButtonType.OK);
			alert.showAndWait();
		}
    }
    
    public ObservableList<Session> list = FXCollections.observableArrayList();
    @FXML
    void showAllConsultation() {
    	table_consultaion.getItems().clear();
    	String sql = "SELECT id_consultation,consultation.dateConsultaion,rendezvous.time,dentiste.nomComplet,patient.nomComplet FROM consultation,patient,dentiste,rendezvous WHERE consultation.rendezvous=rendezvous.id_rdv and dentiste.id_dentiste=consultation.dentiste and consultation.patient=patient.id_patient";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new Session(result.getInt(1), result.getDate(2), result.getString(3), result.getString(4), result.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tc_rdv.setCellValueFactory(new PropertyValueFactory<Session, String>("time"));
		tc_date.setCellValueFactory(new PropertyValueFactory<Session, Date>("date"));
		tc_idConsultation.setCellValueFactory(new PropertyValueFactory<Session, Integer>("id"));
		tc_dentiste.setCellValueFactory(new PropertyValueFactory<Session, String>("dentiste"));
		tc_patient.setCellValueFactory(new PropertyValueFactory<Session, String>("patient"));
		table_consultaion.setItems(list);
    }
    
    @FXML
    void tableConsuEvent() {
    	Session session = table_consultaion.getSelectionModel().getSelectedItem();
    	Stage home = new Stage();
    	try {
			String sql ="SELECT date,dentiste.nomComplet,patient.nomComplet FROM consultation,patient,dentiste,rendezvous WHERE consultation.rendezvous=rendezvous.id_rdv and dentiste.id_dentiste=consultation.dentiste and consultation.patient=patient.id_patient and id_consultation=?";
			try {
				st = cnx.prepareStatement(sql);
				st.setInt(1,session.getId());
				result = st.executeQuery();
				while (result.next()) {
					datePicker.setValue(result.getDate("date").toLocalDate());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Treatments.fxml"));
			Scene scene = new Scene(fxml);
			home.setScene(scene);
			home.setResizable(false);
			home.show();
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = DBconnection.getConnection();
		showAllConsultation();
	}

}
