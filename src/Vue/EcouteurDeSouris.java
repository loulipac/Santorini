package Vue;

import Modele.Jeu;
import Modele.Joueur;
import Modele.JoueurIA;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe Listener de souris pour effectuer les actions sur la grille de jeu.
 */
public class EcouteurDeSouris extends MouseAdapter {
    JeuGraphique jg;
    Jeu jeu;
    int largeur_plateau, hauteur_plateau;

    /**
     * Constructeur de EcouteurDeSouris. Utilise un JeuGraphique.
     *
     * @param jg
     * @see JeuGraphique
     */
    public EcouteurDeSouris(JeuGraphique jg, Jeu _jeu) {
        this.jg = jg;
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
        Joueur joueur_en_cours = jeu.getJoueurType_en_cours();
        if(joueur_en_cours.getClass() != JoueurIA.class) {
            this.largeur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getColonnes();
            this.hauteur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getLignes();
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