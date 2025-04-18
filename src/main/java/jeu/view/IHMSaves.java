package jeu.view;

import javax.swing.*;

import jeu.controller.AuthController;
import jeu.controller.Game;
import jeu.model.Sauvegarde;

import java.awt.*;
import java.util.List;

public class IHMSaves extends JFrame {
	private JList<Sauvegarde> listeSaves;
    private JButton boutonCharger;
    private JButton boutonCreerNouvellePartie;

    public IHMSaves(String nomUtilisateur) {
        setTitle("Choisissez une sauvegarde");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        List<Sauvegarde> saves = AuthController.getSauvegardes(nomUtilisateur);

        listeSaves = new JList<>(new DefaultListModel<>());
        DefaultListModel<Sauvegarde> model = (DefaultListModel<Sauvegarde>) listeSaves.getModel();
        for (Sauvegarde s : saves) {
            model.addElement(s);
        }

        listeSaves.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeSaves.setVisibleRowCount(5);
        listeSaves.setFixedCellHeight(40);
        JScrollPane scroll = new JScrollPane(listeSaves);

        boutonCharger = new JButton("Charger cette partie");
        boutonCreerNouvellePartie = new JButton("Créer une nouvelle partie");

        boutonCharger.addActionListener(e -> {
            Sauvegarde selection = listeSaves.getSelectedValue();
            if (selection != null) {
                dispose();
                Game jeu = new Game(nomUtilisateur);
                jeu.setGUI(new GUI(jeu));
                // chargersave
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une sauvegarde !");
            }
        });
        
        boutonCreerNouvellePartie.addActionListener(e -> {
            dispose();
            Game jeu = new Game(nomUtilisateur);
            jeu.setGUI(new GUI(jeu)); 
            JOptionPane.showMessageDialog(this, "Nouvelle partie créée !");
        });

        add(new JLabel("Sélectionnez votre sauvegarde :", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new GridLayout(2, 1, 10, 10));
        panelBoutons.add(boutonCharger);
        panelBoutons.add(boutonCreerNouvellePartie);

        add(panelBoutons, BorderLayout.SOUTH);

        setVisible(true);
    }

}
