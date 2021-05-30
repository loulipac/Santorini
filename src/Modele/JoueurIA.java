package Modele;

import javax.swing.*;
import java.awt.*;

public class JoueurIA extends Joueur {
    private IA ia;
    private Timer timer;
    private static final int VITESSE_BASE = 1000;

    public JoueurIA(Jeu _jeu, int _num_joueur, IA _ia, int vitesse_ia) {
        super(_jeu, _num_joueur);
        ia = _ia;

        timer = new Timer(VITESSE_BASE / vitesse_ia, e -> joue());
        timer.stop();
    }

    public void setVitesseIA(int new_vitesse) {
        timer.setDelay(VITESSE_BASE / new_vitesse);
    }

    @Override
    public void joue() {
        Point jeu_ia = ia.joue();
        System.out.println("IA joue : " + jeu_ia);
        jeu.jouer(jeu_ia);
        jeu.MAJObservateur();
        if(jeu.getJoueur_en_cours() != num_joueur) timer.stop();
    }

    @Override
    public void joue(Point clic_position) {
        throw new UnsupportedOperationException();
    }

    public void timerIaSet(boolean statut) {
        if (statut) {
            timer.start();
        } else {
            timer.stop();
        }
    }
}
