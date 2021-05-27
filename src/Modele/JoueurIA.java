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

    public JoueurIA(Jeu _jeu, int _num_joueur, IA _ia) {
        super(_jeu, _num_joueur);
        ia = _ia;

        timer = new Timer(500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                joue();
            }
        });
        timer.stop();
    }

    @Override
    public void joue() {
        Point jeu_ia = ia.joue();
        System.out.println("IA joue : " + jeu_ia);
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
