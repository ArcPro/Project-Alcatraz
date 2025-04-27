package jeu.model;

import java.util.List;

public class Complice extends Personnage {
	public Complice(String nom) {
		super(nom, new Quete(new Couteau(), new Plan()));
		setupDialogue();
	}
	
	private void setupDialogue() {
		this.dialogue.add("Hé, toi, le nouveau... T'as l'air d'avoir encore un peu d'espoir dans les yeux... Ça va pas durer ici.");
		this.dialogue.add("Moi aussi, j'ai envie de voir autre chose que ces murs... et j'ai un plan.");
		this.dialogue.add("J'ai réussi à mettre la main sur une carte d'accès. Elle ouvre la porte vers les dortoirs des gardiens.");
		this.dialogue.add("Mais j'compte pas te la filer pour rien...");
		this.dialogue.add("Ramène-moi un couteau et la carte est à toi. Sinon... retourne faire des pompes dans ta cellule");
	}
}
