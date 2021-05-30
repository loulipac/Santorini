package Vue;

import javax.swing.*;
import java.awt.*;

public class BoutonRadio extends JRadioButton {

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
    }

    private ImageIcon scaleImage(String nomImage, int largeur, int hauteur) {
        ImageIcon imgIc = new ImageIcon(nomImage);
        Image img = imgIc.getImage();
        img = img.getScaledInstance(hauteur, largeur, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

}