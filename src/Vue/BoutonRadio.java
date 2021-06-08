package Vue;

import javax.swing.*;
import java.awt.*;

/**
 * Classe créant un JRadioButton personnalisé avec une image et une image survolé personnalisé
 * ainsi qu'une taille prédéfini et un groupe de bouton.
 */
public class BoutonRadio extends JRadioButton {

    /**
     * Constructeur de BoutonRadio prenant une image et une taille.
     * Met en image de bouton l'image passé en paramètre mais aussi l'image clické et l'image de bouton survolé.
     * Met le bouton dans le groupe passé en paramètre.
     *
     * @param image   nom de l'image
     * @param largeur largeur du bouton
     * @param hauteur hauteur du bouton
     * @param groupe  groupe de radio bouton
     */
    public BoutonRadio(String image, int largeur, int hauteur, ButtonGroup groupe) {
        String img = image + ".png";
        String imgSurvole = image + "_hover.png";
        String imgClique = image + "_clicked.png";

        /* Parametres principaux du bouton */
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorder(null);
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(largeur, hauteur));
        setRolloverEnabled(true);

        /* Ajout de l'image par dessus le bouton*/
        ImageIcon iconeBouton = scaleImage(img, hauteur, largeur);
        setIcon(iconeBouton);
        ImageIcon iconeBoutonSurvole = scaleImage(imgSurvole, hauteur, largeur);
        setRolloverIcon(iconeBoutonSurvole);
        ImageIcon iconeBoutonClique = scaleImage(imgClique, hauteur, largeur);
        setSelectedIcon(iconeBoutonClique);

        /* BoutonRadio ajouté au groupe de bouton*/
        groupe.add(this);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Constructeur prennant une dimension (width, height) pour la taille du bouton radio.
     */
    public BoutonRadio(String image, Dimension taille, ButtonGroup groupe) {
        this(image, taille.width, taille.height, groupe);
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

}