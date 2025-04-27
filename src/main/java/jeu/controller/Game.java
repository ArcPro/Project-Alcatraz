package jeu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jeu.model.Sauvegarde;
import jeu.model.Carte;
import jeu.model.Cle;
import jeu.model.Commande;
import jeu.model.Conteneur;
import jeu.model.Couteau;
import jeu.model.Inventaire;
import jeu.model.Lampe;
import jeu.model.Objet;
import jeu.model.Plan;
import jeu.model.Sortie;
import jeu.model.Zone;
import jeu.view.GUI;

public class Game {
	
    private GUI gui; 
	private Zone zoneCourante;
	private Conteneur conteneurCourant;
	Inventaire inventaire;
    
    public Game(String nomUtilisateur) {
    	inventaire = new Inventaire();
        creerCarte();
        gui = null;
    }

    public void setGUI( GUI g) { gui = g; afficherMessageDeBienvenue(); }
    
    private void creerCarte() {
    	// Objets
    	Lampe lampe = new Lampe();
    	Couteau couteau = new Couteau();
    	Plan plan = new Plan();
    	Carte carte = new Carte();
    	Cle cles = new Cle();
    	
    	// Conteneurs
        Conteneur lit = new Conteneur("Lit");
        lit.ajouterObjet(lampe);

        Random rand = new Random();
        Conteneur Tiroir1 = new Conteneur("Premier tiroir");
        Conteneur Tiroir2 = new Conteneur("Second tiroir");
        Conteneur Tiroir3 = new Conteneur("Dernier tiroir");
       
        List<Conteneur> tiroirs = new ArrayList<>();
        tiroirs.add(Tiroir1);
        tiroirs.add(Tiroir2);
        tiroirs.add(Tiroir3);

        Conteneur tiroirAleatoire = tiroirs.get(rand.nextInt(tiroirs.size()));
        tiroirAleatoire.ajouterObjet(lampe);

    	
    	// Zones
        Zone [] zones = new Zone [11];
        zones[0] = new Zone("votre cellule", "Cellule.png" );
        zones[1] = new Zone("le couloir", "Couloir.png");
        zones[2] = new Zone("la suite du couloir", "Couloir2.png" );
        zones[3] = new Zone("les douches", "Douche.png" );
        zones[4] = new Zone("la cuisine", "Cuisine.png" );
        zones[5] = new Zone("la bibliotheque", "Bibliotheque.png" );
        zones[6] = new Zone("l'infirmerie", "Infirmerie.png" );
        zones[7] = new Zone("la cour", "Cour.png" );
        zones[8] = new Zone("le couloir des gardiens", "CouloirG.png" );
        zones[9] = new Zone("la chambre du gardien", "Chambre.jpg" );
        zones[10] = new Zone("le ponton", "Pont.png" );

        
        zones[0].ajouteSortie(Sortie.NORD, zones[1]); // Cellule -> Couloir
        zones[0].ajouterConteneur(lit);
        
        zones[1].ajouteSortie(Sortie.SUD, zones[0]); // Couloir -> Cellule
        zones[1].ajouteSortie(Sortie.NORD, zones[2]); // Couloir -> Couloir2
        zones[1].ajouteSortie(Sortie.EST, zones[3]); // Couloir -> Douches
        zones[1].ajouteSortie(Sortie.OUEST, zones[5]); // Couloir -> Bibliotheque

        zones[2].ajouteSortie(Sortie.SUD, zones[1]); // Couloir2 -> Couloir
        zones[2].ajouteSortie(Sortie.OUEST, zones[6]); // Couloir2 -> Infirmerie
        zones[2].ajouteSortie(Sortie.EST, zones[4]); // Couloir2 -> Cuisine
        zones[2].ajouteSortie(Sortie.NORD, zones[7]); // Couloir2 -> Cour
        
        zones[3].ajouteSortie(Sortie.SUD, zones[1]); // Douches -> Couloir
        
        zones[4].ajouteSortie(Sortie.SUD, zones[2]); // Cuisine -> Couloir2
        zones[4].ajouterConteneur(Tiroir1);
        zones[4].ajouterConteneur(Tiroir2);
        zones[4].ajouterConteneur(Tiroir3);
        
        zones[5].ajouteSortie(Sortie.SUD, zones[1]); // Bibliotheque -> Couloir
        
        zones[6].ajouteSortie(Sortie.SUD, zones[2]); // Infirmerie -> Couloir2
        
        zones[7].ajouteSortie(Sortie.SUD, zones[2]); // Cour -> Couloir2
        zones[7].ajouteSortie(Sortie.NORD, zones[8]); // Cour -> Couloir des gardiens
        
        zones[8].ajouteSortie(Sortie.SUD, zones[9]); // Couloir des gardiens -> Cour
        zones[8].ajouteSortie(Sortie.NORD, zones[9]); // Couloir des gardiens -> Chambre

        zones[9].ajouteSortie(Sortie.SUD, zones[8]); // Chambre -> Couloir
        zones[9].ajouteSortie(Sortie.NORD, zones[10]); // Chambre -> Pont

        



       


        zoneCourante = zones[0]; 
    }

    private void afficherLocalisation() {
            gui.afficher( zoneCourante.descriptionLongue(inventaire));
            gui.afficher();
    }

    private void afficherMessageDeBienvenue() {
    	gui.afficher("Vous avez été arrêté injustement, vous vous retrouvez sur la célèbre prison d'Alcatraz.");
    	gui.afficher();
    	gui.afficher("Votre objectif sera de vous enfuir, afin de retrouver la liberté.");
    	gui.afficher();
        gui.afficher("Tapez '?' pour obtenir de l'aide.");
        gui.afficher();
        gui.afficher("-------------------------------------------");
        gui.afficher();
        afficherLocalisation();
        gui.afficheImage(zoneCourante.nomImage());
    }
    
    public void traiterCommande(String commandeLue) {
    	gui.afficher( "> "+ commandeLue + "\n");
    	String[] morceaux = commandeLue.trim().split("\\s+", 2);
        String commande = morceaux[0].toUpperCase(); // Premier mot
        String argument = (morceaux.length > 1) ? morceaux[1] : null;
        switch (commande) {
        case "?" : case "AIDE" : 
            afficherAide(); 
        	break;
        case "F" : case "FOUILLER" : 
        	fouillerMeuble(argument); 
        	break;
        case "P" : case "PRENDRE" : 
            prendreObjet(argument); 
        	break;
        case "N" : case "NORD" :
        	allerEn( "NORD"); 
        	break;
       case "S" : case "SUD" :
        	allerEn( "SUD"); 
        	break;
        case "E" : case "EST" :
        	allerEn( "EST"); 
        	break;
        case "O" : case "OUEST" :
        	allerEn( "OUEST"); 
        	break;
        case "Q" : case "QUITTER" :
        	terminer();
        	break;
       	default : 
            gui.afficher("Commande inconnue");
            break;
        }
    }

    private void afficherAide() {
        gui.afficher("Etes-vous perdu ?");
        gui.afficher();
        gui.afficher("Les commandes autorisées sont :");
        gui.afficher();
        gui.afficher(Commande.toutesLesDescriptions().toString());
        gui.afficher();
    }
    
    private void fouillerMeuble(String m) {
    	if (m == null || m.isEmpty()) {
            gui.afficher("Fouiller quoi ?");
            gui.afficher();
            return;
        }
    	
    	Conteneur meuble = zoneCourante.getListeConteneurs().stream()
    		    .filter(c -> c.getNom().toLowerCase().contains(m.toLowerCase()))
    		    .findFirst()
    		    .orElse(null);
        System.out.println(meuble);
        if (meuble != null) {
            conteneurCourant = meuble;
            String result = "";
            List<Objet> liste = meuble.voirContenu();
    		for (Objet o : liste) {
    			result += o.getNom() + " ";
    		}
    		if (result == "") result = "Vide";
            gui.afficher("Contenu du " + meuble.getNom() + " : " + result);
            gui.afficher();
            gui.afficher("P ou PRENDRE +  nom de l'objet pour le mettre dans votre inventaire.");
            gui.afficher();
        } else {
            gui.afficher("Aucun meuble nommé \"" + m + "\" ici.");
            gui.afficher();
        }
    }
    
    private void prendreObjet(String o) {
        if (o == null || o.isEmpty()) {
            gui.afficher("Prendre quoi ?");
            return;
        }

        List<Objet> objets = conteneurCourant.voirContenu();
        Objet objetTrouve = null;

        for (Objet obj : objets) {
            if (obj.getNom().toLowerCase().contains(o.toLowerCase())) {
                objetTrouve = obj;
                break;
            }
        }

        if (objetTrouve != null) {
        	conteneurCourant.retirerObjet(objetTrouve); // Enlève de la liste
            inventaire.ajouterObjet(objetTrouve); // Ajoute à l'inventaire du joueur
            gui.afficher("Vous avez pris : " + objetTrouve.getNom());
            gui.afficher();
            gui.afficher( zoneCourante.descriptionLongue(inventaire));
            gui.afficher();
        } else {
            gui.afficher("Objet non trouvé dans ce meuble.");
            gui.afficher();
        }
    }

    private void allerEn(String direction) {
    	Zone nouvelle = zoneCourante.obtientSortie( direction, inventaire);
    	if ( nouvelle == null ) {
        	gui.afficher( "Pas de sortie " + direction);
    		gui.afficher();
    	}
        else {
        	zoneCourante = nouvelle;
        	gui.afficher(zoneCourante.descriptionLongue(inventaire));
        	gui.afficher();
        	gui.afficheImage(zoneCourante.nomImage());
        }
    }
    
    private void terminer() {
    	gui.afficher( "Au revoir...");
    	gui.enable( false);
    }

	public static List<Sauvegarde> getSauvegardes(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
