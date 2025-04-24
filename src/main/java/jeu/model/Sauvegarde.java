package jeu.model;

public class Sauvegarde {
    private String nom;
    private String zone;
    private String infos;

    public Sauvegarde(String nom, String zone, String infos) {
        this.nom = nom;
        this.zone = zone;
        this.infos = infos;
    }

    public String getNom() {
        return nom;
    }


    public String getZone() {
        return zone;
    }

    public String getInfos() {
        return infos;
    }

    @Override
    public String toString() {
        return nom + " - Zone: " + zone + " - " + infos;
    }
}