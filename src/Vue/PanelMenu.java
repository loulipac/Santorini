package Vue;

import Modele.Bouton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

class PanelMenu extends JPanel {

    private Bouton bJouer, bTutoriel, bRegles, bQuitter;
    private JLabel titre;
    private SoundPlayer sound_button;

    public PanelMenu(int largeur, int hauteur) {
        sound_button = new SoundPlayer("menu_click.wav");
        /* BoxLayout */

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));

        /* Button */

        bJouer = new Bouton("src/Ressources/bouton_menu/jouer.png", "src/Ressources/bouton_menu/jouer_hover.png", (int) (largeur * 0.3), (int) (hauteur * 0.106));
        bTutoriel = new Bouton("src/Ressources/bouton_menu/tutoriel.png", "src/Ressources/bouton_menu/tutoriel_hover.png", (int) (largeur * 0.3), (int) (hauteur * 0.1));
        bRegles = new Bouton("src/Ressources/bouton_menu/regle_jeu.png", "src/Ressources/bouton_menu/regle_jeu_hover.png", (int) (largeur * 0.3), (int) (hauteur * 0.1));
        bQuitter = new Bouton("src/Ressources/bouton_menu/quitter.png", "src/Ressources/bouton_menu/quitter_hover.png", (int) (largeur * 0.3), (int) (hauteur * 0.1));


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
        sound_button.playSound();
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "options");
    }

    public void actionBoutonTutoriel(ActionEvent e) {
        sound_button.playSound();
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "plateau");
    }

    public void actionBoutonRegles(ActionEvent e) {
        sound_button.playSound();
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "regles");
    }

    public void actionBoutonQuitter(ActionEvent e) {
        sound_button.playSound();
        System.exit(0);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Chargement de l"image de fond
        try {
            BufferedImage img = ImageIO.read(new File("src/Ressources/artwork/base.png"));
            Dimension img_dim = new Dimension(img.getWidth(), img.getHeight());
            Dimension boundary_dim = new Dimension((int) (getWidth() - getWidth() * 0.25), (int) (getHeight() - getHeight() * 0.25));
            Dimension scaled_dim = getScaledDimension(img_dim, boundary_dim);
            g.drawImage(
                    img,
                    getWidth() / 2 - img.getWidth() / 2,
                    getHeight() / 2 - img.getHeight() / 2,
                    scaled_dim.width,
                    scaled_dim.height,
                    this
            );

            BufferedImage img_columns = ImageIO.read(new File("src/Ressources/artwork/columns.png"));
            g.drawImage(
                    img_columns,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

    public Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}