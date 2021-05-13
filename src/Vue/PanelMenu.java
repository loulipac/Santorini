package Vue;

import Modele.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


class PanelMenu extends JPanel {

    private BoutonMenu bJouer, bTutoriel, bRegles, bQuitter;
    private JLabel titre;

    public PanelMenu(int largeur, int hauteur) {

        /* BoxLayout */

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));

        /* Button */

        bJouer = new BoutonMenu("src/Ressources/bouton_menu/jouer.png", "src/Ressources/bouton_menu/jouer_hover.png", (int)(largeur*0.3), (int)(hauteur*0.106));
        bTutoriel = new BoutonMenu("src/Ressources/bouton_menu/tutoriel.png", "src/Ressources/bouton_menu/tutoriel_hover.png", (int)(largeur*0.3), (int)(hauteur*0.1));
        bRegles = new BoutonMenu("src/Ressources/bouton_menu/regle_jeu.png", "src/Ressources/bouton_menu/regle_jeu_hover.png", (int)(largeur*0.3), (int)(hauteur*0.1));
        bQuitter = new BoutonMenu("src/Ressources/bouton_menu/quitter.png", "src/Ressources/bouton_menu/quitter_hover.png", (int)(largeur*0.3), (int)(hauteur*0.1));


        /* Label */

        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        /* redirection */

        bJouer.addActionListener(this::actionBoutonJouer);
        bTutoriel.addActionListener(this::actionBoutonTutoriel);
        bRegles.addActionListener(this::actionBoutonRegles);
        bQuitter.addActionListener(this::actionBoutonQuitter);

        /* Adding */
        add(Box.createRigidArea(new Dimension(40, 20)));
        add(titre);
        add(Box.createRigidArea(new Dimension(40, 110)));
        add(bJouer);
        add(Box.createRigidArea(new Dimension(40, 40)));
        add(bTutoriel);
        add(Box.createRigidArea(new Dimension(40, 40)));
        add(bRegles);
        add(Box.createRigidArea(new Dimension(40, 40)));
        add(bQuitter);
        add(Box.createRigidArea(new Dimension(40, 40)));

        setBackground(new Color(47, 112, 162));
    }

    public void actionBoutonJouer(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "options");
    }

    public void actionBoutonTutoriel(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "plateau");
    }

    public void actionBoutonRegles(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "regles");
    }

    public void actionBoutonQuitter(ActionEvent e) {
        System.exit(0);
     }


//    public void paintComponent(Graphics g)
//    {
//        //Chargement de l"image de fond
//        try {
//            BufferedImage img = ImageIO.read(new File("src/Ressources/artwork/base.png"));
//            g.drawImage(img, this.getWidth()/5, this.getHeight()/5, 900, 540, this);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Erreur image de fond: " +e.getMessage());
//        }
//    }
}