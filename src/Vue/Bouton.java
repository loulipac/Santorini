package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Bouton extends JButton {
    int largeur;
    int hauteur;
    String image;
    String imageHover;

    public Bouton(String image, String imageHover, int largeur, int hauteur, ActionListener action) {
        this(image, imageHover, largeur, hauteur);
        addActionListener(action);
    }

    public Bouton(String image, String imageHover, Dimension size) {
        this(image, imageHover, size.width, size.height);
    }

    public Bouton(String image, String imageHover, Dimension size, ActionListener action) {
        this(image, imageHover, size.width, size.height);
        addActionListener(action);
    }

    public Bouton(String image, String imageHover, int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.image = image;
        this.imageHover = imageHover;

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
        ImageIcon iconButton = scaleImage(image, hauteur, largeur);
        setIcon(iconButton);
        ImageIcon iconButtonHover = scaleImage(imageHover, hauteur, largeur);
        setRolloverIcon(iconButtonHover);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon scaleImage(String nomImage, int largeur, int hauteur) {
        ImageIcon imgIc = new ImageIcon(nomImage);
        Image img = imgIc.getImage();
        img = img.getScaledInstance(hauteur, largeur, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void changeImage(String image, String imageHover) {
        if(image.equals(this.image) && imageHover.equals(this.imageHover)) {
            return;
        }
        this.image = image;
        this.imageHover = imageHover;
        ImageIcon iconButton = scaleImage(image, hauteur, largeur);
        setIcon(iconButton);
        ImageIcon iconButtonHover = scaleImage(imageHover, hauteur, largeur);
        setRolloverIcon(iconButtonHover);
    }

    public void setTaille(int width, int height) {
        hauteur = height;
        largeur = width;
        setSize(width, height);
        setIcon(scaleImage(image, hauteur, largeur));
        setRolloverIcon(scaleImage(imageHover, hauteur, largeur));
    }


}