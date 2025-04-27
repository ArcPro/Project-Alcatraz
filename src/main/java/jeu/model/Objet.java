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
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    
	    Objet objet = (Objet) o;
	    return nom.equalsIgnoreCase(objet.nom); // compare les noms sans tenir compte de la casse
	}

	@Override
	public int hashCode() {
	    return nom.toLowerCase().hashCode();
	}
}
