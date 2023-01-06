package models;

public class Dentist {
	int id;
	String nom;
	String prenom;
	String adr;
	int age;
	String sexe;
	int tele;
	String nomComplet;
	public Dentist(int id, String nom, String prenom, String adr, int age, String sexe, int tele) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adr = adr;
		this.age = age;
		this.sexe = sexe;
		this.tele = tele;
	}
	public Dentist(int id,String nomComplet, String adr, int age, String sexe, int tele) {
		super();
		this.id = id;
		this.nomComplet = nomComplet;
		this.adr = adr;
		this.age = age;
		this.sexe = sexe;
		this.tele = tele;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdr() {
		return adr;
	}
	public void setAdr(String adr) {
		this.adr = adr;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public int getTele() {
		return tele;
	}
	public void setTele(int tele) {
		this.tele = tele;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
}
