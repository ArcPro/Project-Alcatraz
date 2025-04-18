package jeu.view;

import javax.swing.*;

import jeu.controller.AuthController;
import jeu.utils.ResultatAction;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHMConsole extends JFrame {

    private JTextField champNomUtilisateur;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonInscription;

    public IHMConsole() {
        setTitle("Connexion au jeu");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nom d'utilisateur :"));
        champNomUtilisateur = new JTextField();
        add(champNomUtilisateur);

        add(new JLabel("Mot de passe :"));
        champMotDePasse = new JPasswordField();
        add(champMotDePasse);

        boutonConnexion = new JButton("Connexion");
        boutonInscription = new JButton("Inscription");

        add(boutonConnexion);
        add(boutonInscription);

        // Connexion
        boutonConnexion.addActionListener(e -> {
            String username = champNomUtilisateur.getText();
            String password = new String(champMotDePasse.getPassword());

            ResultatAction result = AuthController.connexion(username, password);
            if (result.succes) {
                dispose();
                new IHMSaves(username);
            } else {
                JOptionPane.showMessageDialog(this, result.message);
            }
        });

        // Inscription
        boutonInscription.addActionListener(e -> {
            String username = champNomUtilisateur.getText();
            String password = new String(champMotDePasse.getPassword());

            ResultatAction result = AuthController.inscription(username, password);
            JOptionPane.showMessageDialog(this, result.message);
        });

        setVisible(true);
    }
}
