package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class PanelParametres extends JPanel {
    private Bouton bAbandonner, bNouvellePartie, bSauvegarder, bReprendre;
    Font lilly_belle;
    private LecteurSon son_bouton;

    public PanelParametres(int largeur, int hauteur) {
        son_bouton = new LecteurSon("menu_click.wav");
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Ressources/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT , 30);
        initialiserPanel(largeur, hauteur);
    }

    /**
     * Ajoute tous les composants au panel
     *
     * @param largeur la largeur de la fenetre
     * @param hauteur la hauteur de la fenetre
     */
    public void initialiserPanel(int largeur, int hauteur) {
        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        JLabel logo = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        JLabel parametres_texte = new JLabel("Paramètres");

        logo.setAlignmentX(CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(largeur/3, largeur/9));

        parametres_texte.setForeground(new Color(82,60,43));
        parametres_texte.setFont(lilly_belle);

        /* JPanel */
        ParametresPanel contenu = new ParametresPanel();

        contenu.setAlignmentX(CENTER_ALIGNMENT);
        contenu.setMaximumSize(new Dimension((int) (largeur * 0.55), hauteur * 2/3));

        /* Boutons*/
        bAbandonner = new Bouton("src/Ressources/bouton/abandonner.png", "src/Ressources/bouton/abandonner_hover.png", largeur / 6, largeur / 30);
        bNouvellePartie = new Bouton("src/Ressources/bouton/nouvelle_partie.png", "src/Ressources/bouton/nouvelle_partie_hover.png", largeur / 6, largeur / 30);
        bSauvegarder = new Bouton("src/Ressources/bouton/sauvegarder.png", "src/Ressources/bouton/sauvegarder_hover.png", largeur / 6, largeur / 30);
        bReprendre = new Bouton("src/Ressources/bouton/reprendre.png", "src/Ressources/bouton/reprendre_hover.png", largeur / 4, largeur / 20);

        /* Evenements */

        bReprendre.addActionListener(this::actionBoutonReprendre);

        /* Adding */

        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        contenu.add(parametres_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        contenu.add(bAbandonner);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        contenu.add(bNouvellePartie);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        contenu.add(bSauvegarder);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));

        add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        add(logo);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        add(contenu);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        add(bReprendre);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));

    }

    /**
     * Remet l'affichage du jeu
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonReprendre(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getPileCarte().show(f2.panelPrincipal, "plateau");
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Chargement de l"image de fond
        try {
            BufferedImage img_colonnes = ImageIO.read(new File("src/Ressources/artwork/columns.png"));
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
            System.err.println("Erreur image de fond: " + e.getMessage());
        }
    }

    private class ParametresPanel extends JPanel {
        public ParametresPanel() {
            super();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            try {
                BufferedImage bg_panel = ImageIO.read(new File("src/Ressources/artwork/bg_regles.png"));
                g2d.drawImage(
                        bg_panel,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erreur image de fond: " + e.getMessage());
            }
        }
    }

    public Dimension conserverRatio(Dimension imgSize, Dimension boundary) {
        int largeur_original = imgSize.width;
        int hauteur_original = imgSize.height;
        int largeur_max = boundary.width;
        int hauteur_max = boundary.height;
        int nouvelle_largeur = largeur_original;
        int nouvelle_hauteur = hauteur_original;

        // Vérifie si on doit redimensionner la largeur
        if (largeur_original > largeur_max) {
            // redimensionne la largeur
            nouvelle_largeur = largeur_max;
            // redimensionne la hauteur pour preserver le ratio
            nouvelle_hauteur = (nouvelle_largeur * hauteur_original) / largeur_original;
        }
        // Verifie si on doit redimensionner la hauteur
        if (nouvelle_hauteur > hauteur_max) {
            // redimensionne la hauteur
            nouvelle_hauteur = hauteur_max;
            // redimensionne la largeur pour preserver le ratio
            nouvelle_largeur = (nouvelle_hauteur * largeur_original) / hauteur_original;
        }

        return new Dimension(nouvelle_largeur, nouvelle_hauteur);
    }
}