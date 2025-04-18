package jeu;
import java.awt.GraphicsEnvironment;

import jeu.view.IHMConsole;

public class Main {
	public static void main(String[] args) {
        boolean isHeadless = GraphicsEnvironment.isHeadless();
        if (isHeadless) {
            System.out.println("Mode headless détecté. L'interface graphique est désactivée.");
            try {
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Interface graphique activée.");
            IHMConsole ihm = new IHMConsole();
        }
		//GUI gui = new GUI(home);.
		
		//home.setGUI(gui);
		//Jeu jeu = new Jeu();
		//GUI gui = new GUI( jeu);
		//jeu.setGUI( gui);
	}
}
