package Vue;

import Modele.Constante;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

class PanelMenu extends JPanel {

    private Bouton bJouer, bTutoriel, bRegles, bQuitter, bFullScreen, bParametres;
    private JLabel logo;
    private LecteurSon son_bouton;
    Image arriere_plan, colonnes;
    boolean maximized = false;
    Frame frame;

    public PanelMenu(int largeur, int hauteur, Frame frame) {
        this.frame = frame;
        son_bouton = new LecteurSon("menu_click.wav");
        /* BoxLayout */

        GridLayout gridlayout = new GridLayout(1, 3);
        setLayout(gridlayout);

        JPanel pListeMenu = new JPanel();
        BoxLayout lListeMenu = new BoxLayout(pListeMenu, BoxLayout.Y_AXIS);
        pListeMenu.setLayout(lListeMenu);
        pListeMenu.setBorder(new EmptyBorder(new Insets(largeur / 15, 50, 30, 50)));
        pListeMenu.setOpaque(false);

        JPanel pSonEcran = new JPanel();
        BoxLayout lSonEcran = new BoxLayout(pSonEcran, BoxLayout.X_AXIS);
        pSonEcran.setLayout(lSonEcran);
        pSonEcran.setBorder(new EmptyBorder(new Insets(largeur / 30, largeur / 5, 30, 50)));
        pSonEcran.setOpaque(false);
        pSonEcran.setSize(new Dimension(largeur, largeur / 20));

        /* Button */
        arriere_plan = JeuGraphique.readImage(Constante.CHEMIN_RESSOURCE + "/artwork/base.png");
        colonnes = JeuGraphique.readImage((Constante.CHEMIN_RESSOURCE + "/artwork/columns.png"));
        bJouer = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/jouer.png", Constante.CHEMIN_RESSOURCE + "/bouton/jouer_hover.png", largeur / 4, largeur / 20);
        bTutoriel = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/tutoriel.png", Constante.CHEMIN_RESSOURCE + "/bouton/tutoriel_hover.png", largeur / 4, largeur / 20);
        bRegles = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/regle_jeu.png", Constante.CHEMIN_RESSOURCE + "/bouton/regle_jeu_hover.png", largeur / 4, largeur / 20);
        bQuitter = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/quitter.png", Constante.CHEMIN_RESSOURCE + "/bouton/quitter_hover.png", largeur / 4, largeur / 20);
        bParametres = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/parametres.png", Constante.CHEMIN_RESSOURCE + "/bouton/parametres_hover.png", largeur / 20, largeur / 20);
        bFullScreen = new Bouton(Constante.CHEMIN_RESSOURCE + "/bouton/fullscreen.png", Constante.CHEMIN_RESSOURCE + "/bouton/fullscreen.png", largeur / 20, largeur / 20);

        /* Label */
        logo = new JLabel(new ImageIcon(Constante.CHEMIN_RESSOURCE + "/logo/logo.png"));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(415, 100));

        /* redirection */
        bJouer.addActionListener(this::actionBoutonJouer);
        bTutoriel.addActionListener(this::actionBoutonTutoriel);
        bRegles.addActionListener(this::actionBoutonRegles);
        bQuitter.addActionListener(this::actionBoutonQuitter);
        bParametres.addActionListener(this::actionBoutonParametres);
        bFullScreen.addActionListener(this::actionFullscreen);


        /* Adding */
        bFullScreen.setAlignmentY(TOP_ALIGNMENT);
        pSonEcran.add(bFullScreen);

        pListeMenu.add(logo);
        pListeMenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 9)));
        pListeMenu.add(bJouer);
        pListeMenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        pListeMenu.add(bTutoriel);
        pListeMenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        pListeMenu.add(bRegles);
        pListeMenu.add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        pListeMenu.add(bQuitter);


        add(Box.createRigidArea(new Dimension(largeur, hauteur / 45)));
        add(pListeMenu);
        add(pSonEcran);

        setBackground(new Color(47, 112, 162));
    }

    /**
     * Remplace le contenu de la fenetre par les options
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonJouer(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "options");
    }

    public void actionBoutonParametres(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "parametres");
    }


    /**
     * Remplace le contenu de la fenetre par le plateau du jeu
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonTutoriel(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "plateau");
    }

    /**
     * Remplace le contenu de la fenetre par les règles du jeu
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonRegles(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "regles");
    }

    /**
     * Ferme la fenetre
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonQuitter(ActionEvent e) {
        son_bouton.joueSon(false);
        System.exit(0);
    }

    /**
     * Met la fenêtre en fullscreen ou non.
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionFullscreen(ActionEvent e) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
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