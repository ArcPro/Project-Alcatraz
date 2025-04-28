package jeu.model;

public class Quete {
	private Objet objetDemande;
	private Objet recompense;
	public int status;
	
	public Quete(Objet r) {
		status = 0;
		recompense = r;
	}
	
	public Quete(Objet d, Objet r) {
		status = 0;
		objetDemande = d;
		recompense = r;
	}
	
	public Objet getRecompense() {
		return recompense;
	}
	public Objet getObjet() {
		return objetDemande;
	}
}
