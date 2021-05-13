package Modele;

import javax.swing.*;
import java.awt.*;

public class BoutonRadio extends JRadioButton {
    int largeur, hauteur;
    String image;

    public BoutonRadio(String image, String imageSelected, int largeur, int hauteur, ButtonGroup groupe) {
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
        ImageIcon iconButtonHover = ScaleImage(imageSelected, hauteur, largeur);
        setSelectedIcon(iconButtonHover);

        /* BoutonRadio ajouté au groupe de bouton*/
        groupe.add(this);
    }

    private ImageIcon ScaleImage(String nomImage, int largeur, int hauteur){
        ImageIcon imgIc = new ImageIcon(nomImage);
        Image img = imgIc.getImage();
        img = img.getScaledInstance(hauteur, largeur,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

}