package jeu.controller;

import org.mindrot.jbcrypt.BCrypt;

import jeu.model.ConnexionDB;
import jeu.model.Sauvegarde;
import jeu.utils.ResultatAction;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthController {

    /**
     * Tente d'enregistrer un nouvel utilisateur.
     * @return true si créé, false si nom déjà pris ou erreur SQL
     */
	public static ResultatAction inscription(String username, String password) {
	    if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
	        return new ResultatAction(false, "Veuillez remplir tous les champs.");
	    }

	    String sql = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe_hash) VALUES (?, ?)";
	    String hash = BCrypt.hashpw(password, BCrypt.gensalt());

	    try (Connection conn = ConnexionDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, username.trim());
	        ps.setString(2, hash);
	        ps.executeUpdate();
	        return new ResultatAction(true, "Compte créé avec succès !");
	    } catch (SQLIntegrityConstraintViolationException ex) {
	        return new ResultatAction(false, "Ce nom d'utilisateur est déjà utilisé.");
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return new ResultatAction(false, "Erreur lors de la création du compte.");
	    }
	}

	public static ResultatAction connexion(String username, String password) {
	    if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
	        return new ResultatAction(false, "Veuillez remplir tous les champs.");
	    }

	    String sql = "SELECT mot_de_passe_hash FROM utilisateurs WHERE nom_utilisateur = ?";
	    try (Connection conn = ConnexionDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, username.trim());
	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) {
	                return new ResultatAction(false, "Utilisateur inconnu.");
	            }
	            String hash = rs.getString("mot_de_passe_hash");
	            if (BCrypt.checkpw(password, hash)) {
	                return new ResultatAction(true, "Connexion réussie !");
	            } else {
	                return new ResultatAction(false, "Mot de passe incorrect.");
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return new ResultatAction(false, "Erreur lors de la connexion.");
	    }
	}

    /**
     * Récupère la liste des sauvegardes pour un utilisateur donné.
     */
    public static List<Sauvegarde> getSauvegardes(String username) {
        List<Sauvegarde> liste = new ArrayList<>();

        String sql =
        	    "SELECT s.nom_partie, " +
        	    "       s.zone_actuelle, " +
        	    "       DATE_FORMAT(s.date_sauvegarde, '%d/%m/%Y %H:%i:%s') AS date_save " +
        	    "FROM   sauvegardes s " +
        	    "JOIN   utilisateurs u ON s.utilisateur_id = u.id " +
        	    "WHERE  u.nom_utilisateur = ? " +
        	    "ORDER  BY s.date_sauvegarde DESC";


        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nomPartie = rs.getString("nom_partie");
                    String zone      = rs.getString("zone_actuelle");
                    String dateSave  = rs.getString("date_save");
                    liste.add(new Sauvegarde(nomPartie, zone, dateSave));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return liste;
    }
}