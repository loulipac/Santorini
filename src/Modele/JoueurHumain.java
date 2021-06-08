package Modele;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * JoueurHumain qui joue selon des clics de souris depuis la classe EcouteurDeSouris
 * @see Listener.EcouteurDeSouris#mousePressed(MouseEvent) 
 */
public class JoueurHumain extends Joueur {
    public JoueurHumain(Jeu _jeu, int _num_joueur) {
        super(_jeu, _num_joueur);
    }

    /**
     * Joue à une position passé en paramètre.
     */
    public void joue(Point clic_position) {
        jeu.jouer(clic_position);
    }
}
