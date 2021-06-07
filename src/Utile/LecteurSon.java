package Vue;

import static Utile.Constante.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Classe permettant de jouer un son.
 */
public class LecteurSon {
    File fichier;

    /**
     * Constructeur de LecteurSon qui charge un son en mémoire.
     *
     */
    public LecteurSon(String sound) {
        fichier = new File(CHEMIN_RESSOURCE+"/sons/" + sound);
    }

    /**
     * Joue le son pointé par l'attribut file.
     *
     * @param loop si vrai : joue en boucle le son, si faux le joue une seule fois.
     */
    public void joueSon(boolean loop) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(fichier.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (!loop) {
                clip.start();
            } else {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
