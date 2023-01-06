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
import models.Dentist;
import models.Patient;

public class DentistController implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
    @FXML
    private JFXTextField txt_nom;

    @FXML
    private JFXTextField txt_prenom;

    @FXML
    private JFXTextField txt_adr;

    @FXML
    private JFXTextField txt_age;

    @FXML
    private JFXTextField txt_tele;

    @FXML
    private JFXComboBox<String> cb_sexe;

    @FXML
    private TableView<Dentist> table_dentist;

    @FXML
    private TableColumn<Dentist, Integer> tc_id;

    @FXML
    private TableColumn<Dentist, String> tc_nomComplet;

    @FXML
    private TableColumn<Dentist, String> tc_adr;

    @FXML
    private TableColumn<Dentist, Integer> tc_age;

    @FXML
    private TableColumn<Dentist, String> tc_sexe;

    @FXML
    private TableColumn<Dentist, Integer> tc_tele;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_edit;

    @FXML
    private Button btn_delete;

    @FXML
    private JFXTextField txt_searchid;
    
    public ObservableList<Dentist> data = FXCollections.observableArrayList();

    @FXML
    void addDentist() {
    	String nom = txt_nom.getText();
    	String prenom = txt_prenom.getText();
    	String adresse = txt_adr.getText();
    	String agee= txt_age.getText();
    	int age = Integer.parseInt(agee);
    	String sexe = cb_sexe.getValue();
    	String tele= txt_tele.getText();
    	int telephone = Integer.parseInt(tele);
    	String nomComplet = nom.toUpperCase()+" "+prenom.toUpperCase();
    	int m=0;
    	String sql = "insert into dentiste(nom,prenom,adresse,age,sexe,telephone,nomComplet) values(?,?,?,?,?,?,?)";
    	if (!nom.equals("") && !prenom.equals("") && !adresse.equals("") && !sexe.equals("")) {
    	try {
			st = cnx.prepareStatement(sql);
			st.setString(1, nom);
			st.setString(2, prenom);
			st.setString(3, adresse);
			st.setInt(4, age);
			st.setString(5, sexe);
			st.setInt(6, telephone);
			st.setString(7, nomComplet);
			st.executeUpdate();
			showAllDentist();
			txt_nom.setText("");
			txt_prenom.setText("");
			txt_searchid.setText("");
			txt_adr.setText("");
			txt_age.setText("0");
			cb_sexe.setValue("sexe");
			txt_tele.setText("06");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Dentist bien Ajouter!", ButtonType.OK);
			alert.showAndWait();
			m=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}}
    	if(m==0){
    		Alert alert = new Alert(AlertType.WARNING, "Impossible de Ajouter", ButtonType.OK);
			alert.showAndWait();
    	}
    }

    @FXML
    void deleteDentist() {
    	String sql = "delete from dentiste where id_dentiste ='" + txt_searchid.getText() + "'";
		int m=0;
		if (!txt_searchid.getText().equals("")) {
    	try {
			st = cnx.prepareStatement(sql);
			st.executeUpdate();
			showAllDentist();
			txt_nom.setText("");
			txt_prenom.setText("");
			txt_searchid.setText("");
			txt_adr.setText("");
			txt_age.setText("");
			cb_sexe.setValue("sexe");
			txt_tele.setText("");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Dentiste supprimee!", ButtonType.OK);
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
    void editDentist() {
    	String nom = txt_nom.getText();
    	String prenom = txt_prenom.getText();
    	String adresse = txt_adr.getText();
    	String agee= txt_age.getText();
    	int age = Integer.parseInt(agee);
    	String sexe = cb_sexe.getValue();
    	String tele= txt_age.getText();
    	int telephone = Integer.parseInt(tele);
    	int m=0;
    	String sql = "update dentiste set nom = ?,prenom=?,adresse=?,age=?,sexe=?,telephone=? where id_dentiste  ='"+ txt_searchid.getText() + "'";
    	if (!nom.equals("") && !prenom.equals("") && !adresse.equals("") && !sexe.equals("")) {
    	try {
			st = cnx.prepareStatement(sql);
			st.setString(1, nom);
			st.setString(2, prenom);
			st.setString(3, adresse);
			st.setInt(4, age);
			st.setString(5, sexe);
			st.setInt(6, telephone);
			st.executeUpdate();
			showAllDentist();
			txt_nom.setText("");
			txt_prenom.setText("");
			txt_searchid.setText("");
			txt_adr.setText("");
			txt_age.setText("0");
			cb_sexe.setValue("sexe");
			txt_tele.setText("06");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Dentist bien Modifier!", ButtonType.OK);
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
    void searchDentist() {
    	String sql = "select * from dentiste where id_dentiste = '" + txt_searchid.getText()+ "'";
		int m = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				txt_nom.setText(result.getString("nom"));
				txt_prenom.setText(result.getString("prenom"));
				txt_adr.setText(result.getString("adresse"));
				int age1 = result.getInt("age");
				txt_age.setText(String.valueOf(age1));
				cb_sexe.setValue(result.getString("sexe"));
				int tele = result.getInt("telephone");
				txt_tele.setText(String.valueOf(tele));
				m = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR,"Aucun Dentiste existe avec ID = " + txt_searchid.getText() + "", ButtonType.OK);
			alert.showAndWait();
		}
    }

    @FXML
    void tableDentistEvent() {
    	List<String> sexe = new ArrayList<String>();
    	sexe.add("homme");sexe.add("femme");
    	cb_sexe.setItems(FXCollections.observableArrayList(sexe));
		Dentist dentist = table_dentist.getSelectionModel().getSelectedItem();
		String sql = "select * from dentiste where id_dentiste = ?";
		try {
			st = cnx.prepareStatement(sql);
			st.setInt(1,dentist.getId());
			result = st.executeQuery();
			while (result.next()) {
				int id = result.getInt("id_dentiste");
				txt_searchid.setText(String.valueOf(id));
				txt_nom.setText(result.getString("nom"));
				txt_prenom.setText(result.getString("prenom"));
				txt_adr.setText(result.getString("adresse"));
				int age1 = result.getInt("age");
				txt_age.setText(String.valueOf(age1));
				cb_sexe.setValue(result.getString("sexe"));
				int tele = result.getInt("telephone");
				txt_tele.setText(String.valueOf(tele));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    private void remplirSex() {
    	List<String> sexe = new ArrayList<String>();
    	sexe.add("homme");sexe.add("femme");
    	cb_sexe.setItems(FXCollections.observableArrayList(sexe));
	}

    private void showAllDentist() {
    	table_dentist.getItems().clear();
		String sql = "SELECT id_dentiste, concat(nom,\" \",prenom), adresse, age, sexe, telephone FROM dentiste";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				data.add(new Dentist(result.getInt("id_dentiste"), result.getString("concat(nom,\" \",prenom)"),
				result.getString("adresse"), result.getInt("age"), result.getString("sexe"),result.getInt("telephone")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tc_id.setCellValueFactory(new PropertyValueFactory<Dentist, Integer>("id"));
		tc_nomComplet.setCellValueFactory(new PropertyValueFactory<Dentist, String>("nomComplet"));
		tc_adr.setCellValueFactory(new PropertyValueFactory<Dentist, String>("adr"));
		tc_age.setCellValueFactory(new PropertyValueFactory<Dentist, Integer>("age"));
		tc_sexe.setCellValueFactory(new PropertyValueFactory<Dentist, String>("sexe"));
		tc_tele.setCellValueFactory(new PropertyValueFactory<Dentist, Integer>("tele"));
		table_dentist.setItems(data);
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx = DBconnection.getConnection();
		showAllDentist();
		remplirSex();
		txt_age.setText("0");
		txt_tele.setText("06");
	}

	
}
