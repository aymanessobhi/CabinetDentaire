package models;

import java.util.Date;

public class Session {
	int id;
	Date date;
	String time;
	String patient;
	String dentiste;
	public Session(int id, Date date, String time, String patient, String dentiste) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.patient = patient;
		this.dentiste = dentiste;
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
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getDentiste() {
		return dentiste;
	}
	public void setDentiste(String dentiste) {
		this.dentiste = dentiste;
	}	
}
