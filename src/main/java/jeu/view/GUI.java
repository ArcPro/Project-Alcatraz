package jeu.view;
import javax.swing.*;

import jeu.controller.Game;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GUI implements ActionListener
{
    private Game jeu;
    private JFrame fenetre;
    private JTextField entree;
    private JTextArea texte;
    private JLabel image;
    private JLabel chronoLabel;

    public GUI(Game j) {
        jeu = j;
        creerGUI();
    }

    public void afficher(String s) {
        texte.append(s);
        texte.setCaretPosition(texte.getDocument().getLength());
    }
    
    public void afficher() {
        afficher("\n");
    }

    public void afficheImage(String nomImage) {
        URL imageURL = this.getClass().getClassLoader().getResource("images/" + nomImage);
        if (imageURL != null) {
            ImageIcon originalIcon = new ImageIcon(imageURL);
            Image originalImage = originalIcon.getImage();

            Image scaledImage = originalImage.getScaledInstance(1000, 750, Image.SCALE_SMOOTH);

            image.setIcon(new ImageIcon(scaledImage));
            fenetre.pack();
        } else {
            System.out.println("Image introuvable : " + nomImage);
        }
    }

    public void enable(boolean ok) {
        entree.setEditable(ok);
        if ( ! ok )
            entree.getCaret().setBlinkRate(0);
    }

    private void creerGUI() {
        fenetre = new JFrame("Jeu");

        JPanel mainPanel = new JPanel(new BorderLayout());

        image = new JLabel();
        mainPanel.add(image, BorderLayout.NORTH);

        texte = new JTextArea();
        texte.setEditable(false);
        JScrollPane listScroller = new JScrollPane(texte);
        listScroller.setPreferredSize(new Dimension(200, 200));
        mainPanel.add(listScroller, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        entree = new JTextField(34);
        bottomPanel.add(entree, BorderLayout.CENTER);

        chronoLabel = new JLabel("15:00", SwingConstants.RIGHT);
        chronoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        chronoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bottomPanel.add(chronoLabel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        fenetre.getContentPane().add(mainPanel);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        entree.addActionListener(this);

        fenetre.pack();
        fenetre.setVisible(true);
        entree.requestFocus();

    }

    public void actionPerformed(ActionEvent e) {
		executerCommande();
    }

    private void executerCommande() {
        String commandeLue = entree.getText();
        entree.setText("");
        jeu.traiterCommande( commandeLue);
    }
    
    public void afficherTemps(String temps) {
        chronoLabel.setText(temps);
        if (temps.startsWith("00:")) {
            chronoLabel.setForeground(Color.RED);
        } else {
            chronoLabel.setFont(chronoLabel.getFont().deriveFont(Font.BOLD));
        }
    }
}