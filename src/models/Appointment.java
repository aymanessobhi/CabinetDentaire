package models;

import java.util.Date;

public class Appointment {
	int id;
	Date date;
	String time;
	String dentiste;
	String patient;
	String statut;
	
	public Appointment(int id, Date date, String time, String dentiste, String patient, String statut) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.dentiste = dentiste;
		this.patient = patient;
		this.statut = statut;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getDentiste() {
		return dentiste;
	}
	public void setDentiste(String dentiste) {
		this.dentiste = dentiste;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
}
