package jeu.model;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Zone 
{
    private String description;
    private String nomImage;
    private HashMap<String,Zone> sorties;   
    private List<Conteneur> listeConteneurs;
    private List<Personnage> listePersonnage;
    private Objet objetRequis;
    private int code;

    public Zone(String description, String image) {
        this.description = description;
        nomImage = image;
        sorties = new HashMap<>();
        listeConteneurs = new ArrayList<>();
        listePersonnage = new ArrayList<>();
        this.code = 0;
    }
    
    public Zone(String description, String image, Objet o) {
        this.description = description;
        nomImage = image;
        sorties = new HashMap<>();
        listeConteneurs = new ArrayList<>();
        this.objetRequis = o;
        this.code = 0;
    }
    
    public Zone(String description, String image, int code) {
        this.description = description;
        nomImage = image;
        sorties = new HashMap<>();
        listeConteneurs = new ArrayList<>();
        this.code = code;
    }
    
    public void ajouterConteneur(Conteneur c) {
    	listeConteneurs.add(c);
    }
    
    public void ajouterPersonnage(Personnage p) {
    	listePersonnage.add(p);
    }

    public void ajouteSortie(Sortie sortie, Zone zoneVoisine) {
        sorties.put(sortie.name(), zoneVoisine);
    }

    public String nomImage() {
        return nomImage;
    }
    
    public int getCode() {
        return code;
    }
     
    public String toString() {
        return description;
    }

    public String descriptionLongue(Inventaire inventaire)  {
        return "Vous êtes dans " + description + "\nSorties : " + sorties(inventaire) + "\nMeubles : " + voirConteneurs();
    }

    private String sorties(Inventaire inventaire) {
        return sorties.entrySet().stream()
            .filter(entry -> {
                Zone destination = entry.getValue();
                Objet objetRequis = destination.objetRequis;
                return objetRequis == null || inventaire.contient(objetRequis);
            })
            .map(entry -> entry.getKey()) // direction (ex: "nord")
            .collect(Collectors.joining(", "));
    }

    public Zone obtientSortie(String direction, Inventaire inventaire) {
        Zone zoneCible = sorties.get(direction);
        if (zoneCible == null) return null;

        Objet requis = zoneCible.objetRequis;

        if (requis == null || inventaire.contient(requis)) {
            return zoneCible;
        } else {
            return null;
        }
    }
    
    public String voirConteneurs() {
    	return listeConteneurs.isEmpty() ? "Aucun"
    		       : listeConteneurs.stream().map(Object::toString).collect(Collectors.joining(", "));
	}
    
    public List<Conteneur> getListeConteneurs() {
        return listeConteneurs;
    }
    
    public List<Personnage> getListePersonnage() {
        return listePersonnage;
    }
}

