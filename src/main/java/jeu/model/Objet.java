package jeu.model;

public class Objet {
	private String nom;
	private String description;
	
	public Objet(String nom, String description) {
		this.nom = nom;
		this.description = description;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void utiliser() {}
}
