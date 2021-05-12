package Vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurDeSouris implements MouseListener {
    JeuGraphique jg;
    int largeur_plateau, hauteur_plateau;

    public EcouteurDeSouris( JeuGraphique jg) {
        this.jg = jg;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.largeur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getColonnes();
        this.hauteur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getLignes();

        if(e.getX() <= largeur_plateau && e.getY() <= hauteur_plateau) {
            int pos_x = e.getX() / jg.getTailleCase();
            int pos_y = e.getY() / jg.getTailleCase();
            System.out.println("pos_x : " + pos_x + ", pos_y : " + pos_y);
            jg.getJeu().jouer(pos_x, pos_y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}