package models;

public class Patient {
	int id;
	String nom;
	String prenom;
	int age;
	String adr;
	String sexe;
	String nomComplet;
	int tele;
	String motif;
	public Patient(int id, String nom, String prenom, int age, String sexe, int tele) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.sexe = sexe;
		this.tele = tele;
	}
	
	public Patient(int id,String nomComplet,String adr, int age, String sexe, int tele, String motif) {
		super();
		this.id = id;
		this.age = age;
		this.adr = adr;
		this.sexe = sexe;
		this.nomComplet = nomComplet;
		this.tele = tele;
		this.motif = motif;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
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
	
}
