package Modele;

import java.awt.*;

public class JoueurHumain implements Joueur {

    private int num_joueur;
    Jeu jeu;

    public JoueurHumain(Jeu _jeu, int _num_joueur) {
        jeu = _jeu;
        num_joueur = _num_joueur;
    }

    @Override
    public void joue(Point clic_position) {
        if(jeu.getJoueur_en_cours() == num_joueur) {
            jeu.jouer(clic_position);
        }
    }

    public int getNum_joueur() {
        return num_joueur;
    }
}
