package Vue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {
    File f;

    public SoundPlayer(String sound) {
        f = new File("./src/Ressources/sons/" + sound);
    }

    public void playSound() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}
