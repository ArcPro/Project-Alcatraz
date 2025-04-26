package jeu.controller;

import java.util.List;
import jeu.model.Sauvegarde;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import jeu.model.Chrono;
import jeu.model.Commande;
import jeu.model.Sortie;
import jeu.model.Zone;
import jeu.view.GUI;

public class Game {
    private Chrono chrono;
    private GUI gui; 
	private Zone zoneCourante;
    private Sauvegarde sauvegarde;





    public Game(String nomUtilisateur) {
        creerCarte();
        this.chrono = new Chrono();
        demarrerChrono();
        gui = null;


    }

    public void setGUI( GUI g) { gui = g; afficherMessageDeBienvenue(); }
    
    private void creerCarte() {






        Zone [] zones = new Zone [4];
        zones[0] = new Zone("le couloir", "Couloir.jpg" );
        zones[1] = new Zone("l'escalier", "Escalier.jpg" );
        zones[2] = new Zone("la grande salle", "GrandeSalle.jpg" );
        zones[3] = new Zone("la salle à manger", "SalleAManger.jpg" );
        zones[0].ajouteSortie(Sortie.EST, zones[1]);
        zones[1].ajouteSortie(Sortie.OUEST, zones[0]);
        zones[1].ajouteSortie(Sortie.EST, zones[2]);
        zones[2].ajouteSortie(Sortie.OUEST, zones[1]);
        zones[3].ajouteSortie(Sortie.NORD, zones[1]);
        zones[1].ajouteSortie(Sortie.SUD, zones[3]);
        zoneCourante = zones[1]; 
    }




    private void afficherLocalisation() {
            gui.afficher( zoneCourante.descriptionLongue());
            gui.afficher();
    }

    private void afficherMessageDeBienvenue() {
    	gui.afficher("Bienvenue !");
    	gui.afficher();
        gui.afficher("Tapez '?' pour obtenir de l'aide.");
        gui.afficher();
        afficherLocalisation();
        gui.afficheImage(zoneCourante.nomImage());
    }
    
    public void traiterCommande(String commandeLue) {
    	gui.afficher( "> "+ commandeLue + "\n");
        switch (commandeLue.toUpperCase()) {
        case "?" : case "AIDE" : 
            afficherAide(); 
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
        case "TEMPS":  // Nouvelle commande pour afficher le temps
            afficherTempsRestant();
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

    private void allerEn(String direction) {
    	Zone nouvelle = zoneCourante.obtientSortie( direction);
    	if ( nouvelle == null ) {
        	gui.afficher( "Pas de sortie " + direction);
    		gui.afficher();
    	}
        else {
        	zoneCourante = nouvelle;
        	gui.afficher(zoneCourante.descriptionLongue());
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

    private void afficherTempsRestant() {
        int minutes = chrono.getTempsRestant() / 60;
        int secondes = chrono.getTempsRestant() % 60;
        gui.afficher(String.format("Temps restant : %02d:%02d", minutes, secondes));
    }




    private void demarrerChrono() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chrono.reduireTemps();
                int temps = chrono.getTempsRestant();

                // Mise à jour UI (thread-safe)
                SwingUtilities.invokeLater(() -> {
                    gui.afficherTemps(formatTemps(temps));
                });

                if (temps <= 0) {
                    timer.cancel();
                    gameOver();
                }
            }
        }, 1000, 1000); // Délai 1s, répété toutes les 1s
    }

    private String formatTemps(int secondes) {
        return String.format("%02d:%02d", secondes / 60, secondes % 60);
    }

    private void gameOver() {
        gui.afficher("\nTemps écoulé !");
    }
}


