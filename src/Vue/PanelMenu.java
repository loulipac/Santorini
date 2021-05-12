package Vue;

import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @TODO les valeurs des dimensions sont hard coded basé sur la taille des images, faire quelque chose de plus clean ? (setup automatique ?)
 */
class PanelMenu extends JPanel {

    private JButton bJouer,bTutoriel,bRegles,bQuitter;
    private JLabel titre;

    public PanelMenu() {

        /* BoxLayout */

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));

        /* Button */
        bJouer = new JButton(new ImageIcon("src/Ressources/bouton_menu/jouer.png"));
        bJouer.setOpaque(false);
        bJouer.setFocusPainted(false);
        bJouer.setBorderPainted(false);
        bJouer.setContentAreaFilled(false);
        bJouer.setBorder(null);
        bJouer.setAlignmentX(CENTER_ALIGNMENT);
        bJouer.setMaximumSize(new Dimension(415, 90));

        bTutoriel = new JButton(new ImageIcon("src/Ressources/bouton_menu/tutoriel.png"));
        bTutoriel.setOpaque(false);
        bTutoriel.setFocusPainted(false);
        bTutoriel.setBorderPainted(false);
        bTutoriel.setContentAreaFilled(false);
        bTutoriel.setBorder(null);
        bTutoriel.setAlignmentX(CENTER_ALIGNMENT);
        bTutoriel.setMaximumSize(new Dimension(415, 90));

        bRegles = new JButton(new ImageIcon("src/Ressources/bouton_menu/regle_jeu.png"));
        bRegles.setOpaque(false);
        bRegles.setFocusPainted(false);
        bRegles.setBorderPainted(false);
        bRegles.setContentAreaFilled(false);
        bRegles.setBorder(null);
        bRegles.setAlignmentX(CENTER_ALIGNMENT);
        bRegles.setMaximumSize(new Dimension(415, 90));

        bQuitter = new JButton(new ImageIcon("src/Ressources/assets_recurrents/barre_vide.png"));
        bQuitter.setOpaque(false);
        bQuitter.setFocusPainted(false);
        bQuitter.setBorderPainted(false);
        bQuitter.setContentAreaFilled(false);
        bQuitter.setBorder(null);
        bQuitter.setAlignmentX(CENTER_ALIGNMENT);
        bQuitter.setMaximumSize(new Dimension(415, 90));

        /* Label */

        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        /* redirection */

        bJouer.addActionListener(this::actionBoutonJouer);
        bTutoriel.addActionListener(this::actionBoutonTutoriel);

        /* Adding */
        add(Box.createRigidArea(new Dimension(40,20)));
        add(titre);
        add(Box.createRigidArea(new Dimension(40,110)));
        add(bJouer);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bTutoriel);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bRegles);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bQuitter);
        add(Box.createRigidArea(new Dimension(40,40)));

//        add(new JeuGraphique(new Jeu(5,5).getPlateau()));
        //setBackground(Color.GREEN);
        setBackground(new Color(47, 112, 162));
    }

    public void actionBoutonJouer(ActionEvent e) {
        FenetreMenu f = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "options");
    }

    public void actionBoutonTutoriel(ActionEvent e) {
        FenetreMenu f = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "plateau");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    /*public void paintComponent(Graphics g)
    {
        //Chargement de l"image de fond
        try {
            BufferedImage img = ImageIO.read(new File("src/Ressources/artwork/base.png"));
            g.drawImage(img, this.getWidth()/5, this.getHeight()/5, 900, 540, this);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " +e.getMessage());
        }
    }*/
}