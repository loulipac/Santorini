package Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Modele.Constante.*;

public class JoueurIA implements Joueur {
    private int num_joueur;
    Jeu jeu;
    IA ia;
    Timer timer;

    public JoueurIA(Jeu _jeu, int _num_joueur, int ia_type) {
        jeu = _jeu;
        num_joueur = _num_joueur;

        timer = new Timer(500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                joue();
            }
        });


        creerIA(ia_type);
    }

    private void creerIA(int ia_type) {
        switch (ia_type) {
            case IA_FACILE -> ia = new IAFacile(jeu);
            case IA_NORMAL, IA_DIFFICILE -> System.out.println("Non implémenté");
        }
    }

    public void timerIaSet(boolean statut) {
        if (statut) {
            timer.start();
        } else {
            timer.stop();
        }
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
    }

    @Override
    public int getNum_joueur() {
        return num_joueur;
    }
}
