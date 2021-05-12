package Vue;

import Modele.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @TODO Reussir a afficher bQuitter en tant que BoutonMenu et non JButton
 */
class PanelMenu extends JPanel {

//    private JButton bQuitter;
    private BoutonMenu bQuitter;
    private BoutonMenu bJouer, bTutoriel, bRegles;
    private JLabel titre;

    public PanelMenu() {

        /* BoxLayout */

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));

        /* Button */

        bJouer = new BoutonMenu("src/Ressources/bouton_menu/jouer.png", 415, 90);
        bTutoriel = new BoutonMenu("src/Ressources/bouton_menu/tutoriel.png", 415, 90);
        bRegles = new BoutonMenu("src/Ressources/bouton_menu/regle_jeu.png", 415, 90);
        bQuitter = new BoutonMenu("src/Ressources/bouton_menu/barre_vide.png", 415, 90);

//        bQuitter = new JButton(new ImageIcon("src/Ressources/assets_recurrents/barre_vide.png"));
//        bQuitter.setOpaque(false);
//        bQuitter.setFocusPainted(false);
//        bQuitter.setBorderPainted(false);
//        bQuitter.setContentAreaFilled(false);
//        bQuitter.setBorder(null);
//        bQuitter.setAlignmentX(CENTER_ALIGNMENT);
//        bQuitter.setMaximumSize(new Dimension(415, 90));

        /* Label */

        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        /* redirection */

        bJouer.addActionListener(this::actionBoutonJouer);
        bTutoriel.addActionListener(this::actionBoutonTutoriel);

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
        FenetreMenu f = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "options");
    }

    public void actionBoutonTutoriel(ActionEvent e) {
        FenetreMenu f = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "plateau");
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