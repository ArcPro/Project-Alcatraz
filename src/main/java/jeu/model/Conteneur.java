package jeu.model;

import java.util.List;
import java.util.ArrayList;

public class Conteneur {
	private List<Objet> liste;
	private String nom;
	
	public Conteneur(String n) {
		this.nom = n;
		liste = new ArrayList<>();
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void retirerObjet(Objet o) {
		liste.remove(liste.indexOf(o));
	}
	
	public void ajouterObjet(Objet o) {
		liste.add(o);
	}
	
	public List<Objet> voirContenu() {
		return liste;
		
		//return liste.toString();
	}
	
	public String toString() {
		return getNom();
	}
}
