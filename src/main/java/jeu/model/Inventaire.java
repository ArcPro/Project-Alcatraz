package jeu.model;

import java.util.List;
import java.util.ArrayList;

public class Inventaire {
	private List<Objet> inventaire;
	
	public Inventaire() {
		inventaire = new ArrayList<Objet>();
	}
	
	public void ajouterObjet(Objet o) {
		this.inventaire.add(o);
	}
	
	public void retirerObjet(Objet o) {
		this.inventaire.remove(o);
	}
	
	public boolean contient(Objet o) {
        return inventaire.contains(o);
    }
}
