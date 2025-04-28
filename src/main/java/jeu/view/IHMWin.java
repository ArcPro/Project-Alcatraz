package jeu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class IHMWin extends JFrame {

    public IHMWin() {
        setTitle("Victoire !");
        setSize(400, 250);
        setLocationRelativeTo(null); // Centre la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel message = new JLabel("<html><center><h1>Félicitations !</h1><br>Vous vous êtes évadé d'Alcatraz !</center></html>", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 18));
        add(message, BorderLayout.CENTER);

        JButton quitter = new JButton("Quitter");
        quitter.addActionListener((ActionEvent e) -> System.exit(0));
        add(quitter, BorderLayout.SOUTH);

        setVisible(true);
    }
}
