package Vue;

import Modele.Constante;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @TODO : Changer le logo titre
 */

class PanelMenu extends JPanel {

    private Bouton bJouer, bTutoriel, bRegles, bQuitter, bFullScreen;
    private JLabel titre;
    private SoundPlayer sound_button;

    public PanelMenu(int largeur, int hauteur) {
        sound_button = new SoundPlayer("menu_click.wav");
        /* BoxLayout */

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));

        /* Button */

        bJouer = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/jouer.png", Constante.CHEMIN_RESSOURCE + "/bouton/jouer_hover.png", largeur / 4, largeur / 20);
        bTutoriel = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/tutoriel.png", Constante.CHEMIN_RESSOURCE + "/bouton/tutoriel_hover.png", largeur / 4, largeur / 20);
        bRegles = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/regle_jeu.png", Constante.CHEMIN_RESSOURCE + "/bouton/regle_jeu_hover.png", largeur / 4, largeur / 20);
        bQuitter = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/quitter.png", Constante.CHEMIN_RESSOURCE + "/bouton/quitter_hover.png", largeur / 4, largeur / 20);
        // bFullScreen = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/fullscreen.png", Constante.CHEMIN_RESSOURCE + "/bouton/fullscreen.png", largeur/, largeur*0.1);

        /* Label */
        titre = new JLabel(new ImageIcon(Constante.CHEMIN_RESSOURCE + "/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        /* redirection */
        bJouer.addActionListener(this::actionBoutonJouer);
        bTutoriel.addActionListener(this::actionBoutonTutoriel);
        bRegles.addActionListener(this::actionBoutonRegles);
        bQuitter.addActionListener(this::actionBoutonQuitter);

        /* Adding */
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 9)));
        add(titre);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 9)));
        add(bJouer);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        add(bTutoriel);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        add(bRegles);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        add(bQuitter);

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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Chargement de l"image de fond
        try {
            BufferedImage img = ImageIO.read(new File(Constante.CHEMIN_RESSOURCE + "/artwork/base.png"));
            Dimension img_dim = new Dimension(img.getWidth(), img.getHeight());
            Dimension taille_max = new Dimension((int) (getWidth() * 0.8), (int) (getHeight() * 0.8));
            Dimension taille_redimensionnee = conserverRatio(img_dim, taille_max);
            g2d.drawImage(
                    img,
                    getWidth() / 2 - ((int) (taille_redimensionnee.getWidth() / 2)),
                    getHeight() / 2 - ((int) (taille_redimensionnee.getHeight() / 2)),
                    taille_redimensionnee.width,
                    taille_redimensionnee.height,
                    this
            );

            BufferedImage img_colonnes = ImageIO.read(new File(Constante.CHEMIN_RESSOURCE + "/artwork/columns.png"));
            g2d.drawImage(
                    img_colonnes,
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

    public Dimension conserverRatio(Dimension imgSize, Dimension boundary) {
        int largeur_original = imgSize.width;
        int hauteur_original = imgSize.height;
        int largeur_max = boundary.width;
        int hauteur_max = boundary.height;
        int nouvelle_largeur = largeur_original;
        int nouvelle_hauteur = hauteur_original;

        // first check if we need to scale width
        if (largeur_original > largeur_max) {
            //scale width to fit
            nouvelle_largeur = largeur_max;
            //scale height to maintain aspect ratio
            nouvelle_hauteur = (nouvelle_largeur * hauteur_original) / largeur_original;
        }
        // then check if we need to scale even with the new height
        if (nouvelle_hauteur > hauteur_max) {
            //scale height to fit instead
            nouvelle_hauteur = hauteur_max;
            //scale width to maintain aspect ratio
            nouvelle_largeur = (nouvelle_hauteur * largeur_original) / hauteur_original;
        }

        return new Dimension(nouvelle_largeur, nouvelle_hauteur);
    }
}