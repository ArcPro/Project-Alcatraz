package jeu.model;

import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Lit un MP3 depuis les resources, en boucle, dans un thread daemon.
 */
public class MP3Player {
    private Player player;
    private Thread playThread;

    /** Lance la lecture en boucle du MP3 (resourcePath ex : "sounds/background.mp3"). */
    public void playLoop(String resourcePath) {
        stop(); // Stoppe une éventuelle lecture en cours

        playThread = new Thread(() -> {
            try {
                // Boucle infinie tant que le thread n'est pas interrompu
                while (!Thread.currentThread().isInterrupted()) {
                    try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
                         BufferedInputStream bis = new BufferedInputStream(is)) {
                        player = new Player(bis);
                        player.play();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "MP3-Player-Thread");

        playThread.setDaemon(true);
        playThread.start();
    }

    /** Arrête la lecture et ferme le player. */
    public void stop() {
        if (playThread != null) {
            playThread.interrupt();
            playThread = null;
        }
        if (player != null) {
            player.close();
            player = null;
        }
    }
}
