package Vue;

import Modele.Constante;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

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
            jg.getJeu().jouer(pos_y, pos_x);
            jg.repaint();
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
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/outil_rouge.png");
        //jg.setCursor(toolkit.createCustomCursor(image , new Point(16, 16), "c_construire_rouge"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}