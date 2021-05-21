package Vue;

import Modele.Constante;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Classe Listener de souris pour effectuer les actions sur la grille de jeu.
 */
public class EcouteurDeSouris extends MouseAdapter {
    JeuGraphique jg;
    int largeur_plateau, hauteur_plateau;

    /**
     * Constructeur de EcouteurDeSouris. Utilise un JeuGraphique.
     *
     * @param jg
     * @see JeuGraphique
     */
    public EcouteurDeSouris(JeuGraphique jg) {
        this.jg = jg;
    }

    /**
     * Utilise getJeu().jouer de JeuGraphique pour effectuer une action à la case calculé depuis la position de la souris.
     *
     * @param e evenement lorsqu'un clic intervient (contient la position du clic par exemple)
     * @see JeuGraphique#getJeu()
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.largeur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getColonnes();
        this.hauteur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getLignes();
        if (e.getX() <= largeur_plateau && e.getY() <= hauteur_plateau) {
            int pos_x = e.getX() / jg.getTailleCase();
            int pos_y = e.getY() / jg.getTailleCase();
            jg.getJeu().jouer(pos_y, pos_x);
            jg.repaint();
            if(jg.getIa() != null){
                System.out.println("IA joue");
                jg.getIa().joue();
                jg.repaint();
            }
        }
    }
}