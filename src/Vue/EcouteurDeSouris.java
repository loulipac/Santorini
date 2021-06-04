package Vue;

import Modele.Jeu;
import Modele.Joueur;
import Modele.JoueurIA;

import static Modele.Constante.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe Listener de souris pour effectuer les actions sur la grille de jeu.
 */
public class EcouteurDeSouris extends MouseAdapter {
    JeuGraphique jg;
    Jeu jeu;
    int largeur_plateau;
    int hauteur_plateau;
    PanelPlateau pp;

    /**
     * Constructeur de EcouteurDeSouris. Utilise un JeuGraphique.
     *
     * @see JeuGraphique
     */
    public EcouteurDeSouris(JeuGraphique jg, Jeu _jeu, PanelPlateau _pp) {
        this.jg = jg;
        this.pp = _pp;
        jeu = _jeu;
    }

    /**
     * Utilise getJeu().jouer de JeuGraphique pour effectuer une action à la case calculé depuis la position de la souris.
     *
     * @param e evenement lorsqu'un clic intervient (contient la position du clic par exemple)
     * @see JeuGraphique#getJeu()
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (pp.isParametreVisible()) return;
        if (jeu.getNetUser() != null && jeu.getNetUser().getNumJoueur() != jeu.getJoueur_en_cours().getNum_joueur()) return;
        Joueur joueur_en_cours = jeu.getJoueur_en_cours();
        if (joueur_en_cours.getClass() != JoueurIA.class) {
            this.largeur_plateau = jg.getTailleCase() * PLATEAU_COLONNES;
            this.hauteur_plateau = jg.getTailleCase() * PLATEAU_LIGNES;
            if (e.getX() <= largeur_plateau && e.getY() <= hauteur_plateau) {
                joueur_en_cours.joue(new Point(
                        e.getY() / jg.getTailleCase(),
                        e.getX() / jg.getTailleCase()
                ));
                jg.repaint();
            }
        }

    }
}