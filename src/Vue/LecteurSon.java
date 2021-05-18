package Vue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class LecteurSon {
    File fichier;

    public LecteurSon(String sound) {
        fichier = new File("./src/Ressources/sons/" + sound);
    }

    /**
     * Joue le son pointé par l'attribut file.
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
            System.err.println(ex.toString());
        }
    }
}
