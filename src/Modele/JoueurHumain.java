package Modele;

import java.awt.*;

public class JoueurHumain extends Joueur {
    public JoueurHumain(Jeu _jeu, int _num_joueur) {
        super(_jeu, _num_joueur);
    }

    @Override
    public void joue(Point clic_position) {
        jeu.jouer(clic_position);
    }
}
