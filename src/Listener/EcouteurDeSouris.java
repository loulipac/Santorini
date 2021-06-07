package Listener;

import Modele.Jeu;
import Modele.Joueur;
import Modele.JoueurHumain;
import Modele.JoueurIA;
import Vue.JeuGraphique;
import Vue.PanelPlateau;

import static Utile.Constante.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe Listener de souris pour effectuer les actions sur la grille de jeu.
 */
public class EcouteurDeSouris extends MouseAdapter {
    private final JeuGraphique jg;
    private final Jeu jeu;
    private final PanelPlateau pp;

    /**
     * Constructeur de EcouteurDeSouris. Utilise un JeuGraphique.
     *
     * @see JeuGraphique
     */
    public EcouteurDeSouris(JeuGraphique jg, Jeu _jeu, PanelPlateau _pp) {
        this.jg = jg;
        this.pp = _pp;
        this.jeu = _jeu;
    }

    /**
     * Utilise getJeu().jouer de JeuGraphique pour effectuer une action à la case calculé depuis la position de la souris.
     *
     * @param e evenement lorsqu'un clic intervient (contient la position du clic par exemple)
     * @see JeuGraphique#getJeu()
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (pp.isParametreVisible() || (jeu.getNetUser() != null && jeu.getNetUser().getNumJoueur() != jeu.getJoueurEnCours().getNum_joueur()))
            return;
        if (jeu.getJoueurEnCours().getClass() != JoueurIA.class &&
                e.getX() <= (jg.getTailleCase() * PLATEAU_COLONNES) &&
                e.getY() <= (jg.getTailleCase() * PLATEAU_LIGNES)) {
            ((JoueurHumain) jeu.getJoueurEnCours()).joue(new Point(
                    e.getY() / jg.getTailleCase(),
                    e.getX() / jg.getTailleCase()
            ));
            jg.repaint();
        }
    }

    /**
     * Evènement quand la souris sort de la grille de jeu, permet d'effacer les hovers sur la grille
     * (tel que le batisseur en transparence lors du placement).
     */
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        jg.setCase_sous_souris(null);
        jg.repaint();
    }
}