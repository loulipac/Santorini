package Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoutonMenu extends JButton {
    int largeur, hauteur;
    String image;

    public BoutonMenu(String image, String imageHover, int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.image = image;


        ImageIcon iconButton = ScaleImage(image, hauteur, largeur);
        setIcon(iconButton);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorder(null);
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(this.largeur, this.hauteur));
        setRolloverEnabled(true);
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