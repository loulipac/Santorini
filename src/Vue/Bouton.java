package Vue;

import Utile.Utile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static Utile.Constante.CHEMIN_RESSOURCE;

/**
 * Classe créant un JButton personnalisé avec une image et une image survolé personnalisé ainsi qu'une taille prédéfini.
 */
public class Bouton extends JButton {
    private int largeur;
    private int hauteur;
    private String image;
    private String imageHover;

    /**
     * Constructeur principal de bouton dessinant l'image sur sur le bouton et définissant la taille de ce dernier
     *
     * @param image      nom de l'image du bouton
     * @param imageHover nom de l'image du bouton survolé
     * @param largeur    largeur du bouton
     * @param hauteur    hauteur du bouton
     */
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

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imgCurseur = toolkit.getImage(CHEMIN_RESSOURCE + "/curseur/hand_gris.png");
        setCursor(toolkit.createCustomCursor(imgCurseur.getScaledInstance(32, 32, Image.SCALE_SMOOTH), new Point(16, 16), "c_hand_gris"));
    }

    /**
     * Constructeur prennant une action à faire quand on clique sur le bouton.
     */
    public Bouton(String image, String imageHover, int largeur, int hauteur, ActionListener action) {
        this(image, imageHover, largeur, hauteur);
        addActionListener(action);
    }

    /**
     * Constructeur prennant une dimension (width, height) pour la taille du bouton.
     */
    public Bouton(String image, String imageHover, Dimension size) {
        this(image, imageHover, size.width, size.height);
    }

    /**
     * Constructeur prennant une dimension (width, height) pour la taille du bouton
     * et une action à faire pour le clique sur le bouton.
     */
    public Bouton(String image, String imageHover, Dimension size, ActionListener action) {
        this(image, imageHover, size.width, size.height);
        addActionListener(action);
    }

    /**
     * Redimensionne l'image en gardant le bon aspect de l'image.
     *
     * @param nomImage nom de l'image à redimensionner
     * @param largeur  largeur de la nouvelle image
     * @param hauteur  hauteur de la nouvelle image
     * @return l'image redimensionnée
     */
    private ImageIcon scaleImage(String nomImage, int largeur, int hauteur) {
        ImageIcon imgIc = new ImageIcon(nomImage);
        Image img = imgIc.getImage();
        img = img.getScaledInstance(hauteur, largeur, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /**
     * Change les images du bouton si elles sont différentes indépendamment.
     *
     * @param image      nouvelle image du bouton
     * @param imageHover nouvelle image du bouton survolé
     */
    public void changeImage(String image, String imageHover) {
        if (!image.equals(this.image)) {
            this.image = image;
            ImageIcon iconButton = scaleImage(image, hauteur, largeur);
            setIcon(iconButton);
        }
        if (!imageHover.equals(this.imageHover)) {
            this.imageHover = imageHover;
            ImageIcon iconButtonHover = scaleImage(imageHover, hauteur, largeur);
            setRolloverIcon(iconButtonHover);
        }
    }

    /**
     * Change la taille d'un bouton.
     *
     * @param width  la nouvelle largeur
     * @param height la nouvelle hauteur
     */
    public void setTaille(int width, int height) {
        hauteur = height;
        largeur = width;
        setSize(width, height);
        setIcon(scaleImage(image, hauteur, largeur));
        setRolloverIcon(scaleImage(imageHover, hauteur, largeur));
    }

    // GETTER

    public String getImage() {
        return image;
    }

    public String getImageHover() {
        return imageHover;
    }
}