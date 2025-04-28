package jeu.model;

import java.util.ArrayList;
import java.util.List;

public class Personnage {
	public String nom;
	public Zone zone;
	public List<String> dialogue;
	public int status;
	public Quete quete;
	public Personnage(String nom, Quete q) {
		quete = q;
		this.nom = nom;
		this.dialogue = new ArrayList<>();
		this.status = 0;
	}
}
