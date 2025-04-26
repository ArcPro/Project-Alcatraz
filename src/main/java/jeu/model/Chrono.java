package jeu.model;


import java.util.Timer;


public class Chrono {

    private Timer timer;
    private int tempsRestant;
    public final static int TEMPS_MAX_SEC = 100;

    public Chrono(int temps) {
        this.timer = new Timer();
        this.tempsRestant = temps;
    }

    public Chrono() {
        this(TEMPS_MAX_SEC);
    }

    public Timer getTimer() {
        return timer;
    }

    public int getTempsRestant() {
        return this.tempsRestant;
    }

    public void reduireTemps() {
        this.tempsRestant--;
    }
}

