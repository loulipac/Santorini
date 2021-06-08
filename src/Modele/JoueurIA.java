package Modele;

import IA.IA;

import javax.swing.*;
import java.awt.*;

/**
 * JoueurIA joue à intervalle régulier jusqu'à ce qu'elle ne soit plus le joueur en cours ou
 * s'arrête quand on lui demande.
 * @see Jeu#desactiverIA()
 * @see Jeu#iaSwitch()
 */
public class JoueurIA extends Joueur {
    private final IA ia;
    private final Timer timer;
    private static final int VITESSE_BASE = 1000;

    public JoueurIA(Jeu _jeu, int _num_joueur, IA _ia, int vitesse_ia) {
        super(_jeu, _num_joueur);
        ia = _ia;
        timer = new Timer(VITESSE_BASE / vitesse_ia, e -> joue());
        timer.stop();
    }

    /**
     * Demande à l'IA une position pour jouer une action.
     * Mets à jour les observateurs pour voir les actions pas à pas de l'IA.
     * Arrête de jouer si après une action, le joueur change.
     */
    public void joue() {
        Point jeu_ia = ia.joue();
        jeu.jouer(jeu_ia);
        jeu.MAJObservateur();
        if(jeu.getJoueurEnCours().getNum_joueur() != num_joueur) timer.stop();
    }

    /**
     * Met en pause ou en marche l'IA.
     * @param statut vrai met en marche l'IA, sinon en pause
     */
    public void timerIaSet(boolean statut) {
        if (statut) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    /**
     * Modifie la vitesse d'exécution de l'IA.
     * @param new_vitesse vitesse de l'IA
     */
    public void setVitesseIA(int new_vitesse) {
        timer.setDelay(VITESSE_BASE / new_vitesse);
    }
}
