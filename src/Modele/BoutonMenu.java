package Modele;

import javax.swing.*;
import java.awt.*;

public class BoutonMenu extends JButton {
    int largeur, hauteur;
    String image;

    public BoutonMenu(String image, int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.image = image;

        setIcon(new ImageIcon(this.image));
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorder(null);
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(this.largeur, this.hauteur));
    }

}