package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.DBconnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class TreatmentController implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
    @FXML
    private Label lab_idRdv;

    @FXML
    private Label lab_nomPatient;

    @FXML
    private Label lab_nomDentist;

    @FXML
    private JFXTextArea txt_presc;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXButton btn_create;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private FontAwesomeIconView iconSearch;

    @FXML
    private JFXTextField txt_serachid;

    @FXML
    private JFXTextArea txt_abservation;

    @FXML
    private JFXButton btn_delete;
    
    @FXML
    void createTreatment() {
    	String idCons = txt_serachid.getText();
    	int consultation = Integer.parseInt(idCons);
    	String abservation = txt_abservation.getText();
    	String medicament = txt_presc.getText();
    	Date dated = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	java.sql.Date sqldate = new java.sql.Date(dated.getTime());
    	String sql = "INSERT INTO ordonance (date,abservation,medicament,consultation) VALUES (?,?,?,?)";
    	try {
			st = cnx.prepareStatement(sql);
			st.setDate(1,sqldate);
			st.setString(2, abservation);
			st.setString(3,medicament);
			st.setInt(4,consultation);
			st.executeUpdate();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Ordonnance Cree!", ButtonType.OK);
			alert.showAndWait();
		} catch (Exception e) {
			System.out.println("imodahoasf");
		}
    }
    
    @FXML
    void deleteTreatment() {
    	String sql = "delete from ordonance where consultation ='" + txt_serachid.getText() + "'";
		int m=0;
		if (!txt_serachid.getText().equals("")) {
    	try {
			st = cnx.prepareStatement(sql);
			st.executeUpdate();
			txt_abservation.setText("");
			txt_presc.setText("");
			txt_serachid.setText("");
			lab_idRdv.setText("");
			lab_nomDentist.setText("");
			lab_nomPatient.setText("");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Ordonance supprimee!", ButtonType.OK);
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
    void editTreatment() {
    	String idCons = txt_serachid.getText();
    	int consultation = Integer.parseInt(idCons);
    	String abservation = txt_abservation.getText();
    	String medicament = txt_presc.getText();
    	Date dated = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	java.sql.Date sqldate = new java.sql.Date(dated.getTime());
    	String sql = "UPDATE ordonance set date = ?,abservation=?,medicament = ? where consultation = '"+txt_serachid.getText()+"'";
    	int m=0;
    	if(!txt_serachid.getText().equals("")) {
    		try {
    			st = cnx.prepareStatement(sql);
    			st.setDate(1,sqldate);
    			st.setString(2, abservation);
    			st.setString(3,medicament);
    			st.executeUpdate();
    			Alert alert = new Alert(AlertType.CONFIRMATION, "Ordonnance modifier!", ButtonType.OK);
    			alert.showAndWait();
    			m=1;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	if(m==0){
    		Alert alert = new Alert(AlertType.ERROR, "impossible de modifier!", ButtonType.OK);
			alert.showAndWait();
    	}
    	
    }
    
    @FXML
    void ImprimerOrdonance() {
    	Document docu = new Document();
    	try {
			PdfWriter.getInstance(docu, new FileOutputStream("Ordonnance.pdf"));
			docu.open();
			String format = "dd/mm/yy hh:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			java.util.Date date = new java.util.Date();
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("F:\\javaProjects\\workspace\\CabinetDentaire\\src\\images\\dentaire2.png");
			img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
			docu.add(img);
			docu.add(new Paragraph("\t\n\n\n\n\n\nPatient : \t\t"+lab_nomPatient.getText()+""
					+"\t\nDentist : \t"+lab_nomDentist.getText()+""
					+"\t\nNumero de consultation : \t"+txt_serachid.getText()+""
					+"\t\n\nAbservation : \t\t\n\n"+txt_abservation.getText()+""
					+"\t\n\nMedicament : \t\t\n\n"+txt_presc.getText()+""
					+"\n\n\n Le : "+sdf.format(date)+""));
			docu.close();
			Desktop.getDesktop().open(new File("Ordonnance.pdf"));
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void searchConsultation() {
    	txt_abservation.setText("");
		txt_presc.setText("");
    	String sql = "SELECT id_consultation,consultation.dateConsultaion,consultation.rendezvous,dentiste.nomComplet,patient.nomComplet FROM consultation,patient,dentiste,rendezvous WHERE consultation.rendezvous=rendezvous.id_rdv and dentiste.id_dentiste=consultation.dentiste and consultation.patient=patient.id_patient and id_consultation='"+txt_serachid.getText()+"'";
		int m = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				lab_nomDentist.setText(result.getString("dentiste.nomComplet"));
				lab_nomPatient.setText(result.getString("patient.nomComplet"));
				datePicker.setValue(result.getDate("consultation.dateConsultaion").toLocalDate());
				int rdv = result.getInt("consultation.rendezvous");
				lab_idRdv.setText(String.valueOf(rdv));
				int cons = result.getInt("id_consultation");
				txt_serachid.setText(String.valueOf(cons));
				m = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql1 = "SELECT abservation,medicament FROM ordonance WHERE consultation='"+txt_serachid.getText()+"'";
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if (result.next()) {
				txt_abservation.setText(result.getString("abservation"));
				txt_presc.setText(result.getString("medicament"));
				m = 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR,
					"Aucun consultation existe avec Numero = "+txt_serachid.getText()+"", ButtonType.OK);
			alert.showAndWait();
		}
		if (m == 2) {
			Alert alert = new Alert(AlertType.WARNING,"Deja existe", ButtonType.OK);
			alert.showAndWait();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		cnx = DBconnection.getConnection();
	}

}