package Vue;

import javax.swing.*;
import java.awt.*;

public class Bouton extends JButton {
    int largeur, hauteur;
    String image;

    public Bouton(String image, String imageHover, int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.image = image;

        /* Parametres principaux du bouton */
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorder(null);
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(this.largeur, this.hauteur));
        setRolloverEnabled(true);

        /* Ajout de l'image par dessus le bouton*/
        ImageIcon iconButton = ScaleImage(image, hauteur, largeur);
        setIcon(iconButton);
        ImageIcon iconButtonHover = ScaleImage(imageHover, hauteur+5, largeur+5);
        setRolloverIcon(iconButtonHover);
    }

    private ImageIcon ScaleImage(String nomImage, int largeur, int hauteur){
        ImageIcon imgIc = new ImageIcon(nomImage);
        Image img = imgIc.getImage();
        img = img.getScaledInstance(hauteur, largeur,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

}