package Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Modele.Constante.*;

public class JoueurIA extends Joueur {
    IA ia;
    Timer timer;
    final int VITESSE_BASE = 1;

    public JoueurIA(Jeu _jeu, int _num_joueur, IA _ia, int vitesse_ia) {
        super(_jeu, _num_joueur);
        ia = _ia;

        timer = new Timer(VITESSE_BASE / vitesse_ia, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                joue();
            }
        });
        timer.stop();
    }

    public void setVitesseIA(int new_vitesse) {
        timer.setDelay(VITESSE_BASE / new_vitesse);
    }

    @Override
    public void joue() {
        Point jeu_ia = ia.joue();
        jeu.jouer(jeu_ia);
        jeu.MAJObservateur();
        if(jeu.getJoueur_en_cours() != num_joueur) timer.stop();
    }

    public void timerIaSet(boolean statut) {
        if (statut) {
            timer.start();
        } else {
            timer.stop();
        }
    }
}
