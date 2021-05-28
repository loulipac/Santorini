package Vue;

import static Modele.Constante.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

class PanelMenu extends JPanel {

    private Bouton bJouer, bTutoriel, bRegles, bQuitter, bFullScreen, bParametres, bSon;
    private JLabel logo;
    private LecteurSon son_bouton;
    Image arriere_plan, colonnes;
    boolean maximized = false, muted = false;
    Dimension taille_fenetre;

    public PanelMenu(Dimension _taille_fenetre) {
        taille_fenetre = _taille_fenetre;
        son_bouton = new LecteurSon("menu_click.wav");
        setLayout(new GridLayout(1, 3));

        JPanel pListeMenu = new JPanel();
        pListeMenu.setLayout(new BoxLayout(pListeMenu, BoxLayout.Y_AXIS));
        pListeMenu.setOpaque(false);
        pListeMenu.setBorder(new EmptyBorder(new Insets(taille_fenetre.width / 15, 50, 30, 50)));

        JPanel pSonEcran = new JPanel();
        pSonEcran.setLayout(new BoxLayout(pSonEcran, BoxLayout.X_AXIS));
        pSonEcran.setBorder(new EmptyBorder(new Insets(taille_fenetre.width / 30, taille_fenetre.width / 5, 30, 50)));
        pSonEcran.setOpaque(false);

        /* Button */
        arriere_plan = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/artwork/base.png");
        colonnes = JeuGraphique.readImage((CHEMIN_RESSOURCE + "/artwork/columns.png"));
        bJouer = new Bouton(CHEMIN_RESSOURCE + "/bouton/jouer.png", CHEMIN_RESSOURCE + "/bouton/jouer_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);
        bTutoriel = new Bouton(CHEMIN_RESSOURCE + "/bouton/tutoriel.png", CHEMIN_RESSOURCE + "/bouton/tutoriel_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);
        bRegles = new Bouton(CHEMIN_RESSOURCE + "/bouton/regle_jeu.png", CHEMIN_RESSOURCE + "/bouton/regle_jeu_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);
        bQuitter = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter.png", CHEMIN_RESSOURCE + "/bouton/quitter_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);
        bParametres = new Bouton(CHEMIN_RESSOURCE + "/bouton/parametres.png", CHEMIN_RESSOURCE + "/bouton/parametres_hover.png", taille_fenetre.width / 20, taille_fenetre.width / 20);
        bFullScreen = new Bouton(CHEMIN_RESSOURCE + "/bouton/fullscreen.png", CHEMIN_RESSOURCE + "/bouton/fullscreen_hover.png", taille_fenetre.width / 20, taille_fenetre.width / 20);
        bSon = new Bouton(CHEMIN_RESSOURCE + "/bouton/son_on.png", CHEMIN_RESSOURCE + "/bouton/son_on_hover.png", taille_fenetre.width / 20, taille_fenetre.width / 20);

        /* Label */
        logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo.png"));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(taille_fenetre.width / 3, taille_fenetre.width / 9));

        /* redirection */
        bJouer.addActionListener(this::actionBoutonJouer);
        bTutoriel.addActionListener(this::actionBoutonTutoriel);
        bRegles.addActionListener(this::actionBoutonRegles);
        bQuitter.addActionListener(this::actionBoutonQuitter);
        bParametres.addActionListener(this::actionBoutonParametres);
        bFullScreen.addActionListener(this::actionFullscreen);
        bSon.addActionListener(this::actionSon);


        /* Adding */
        bFullScreen.setAlignmentY(TOP_ALIGNMENT);
        bSon.setAlignmentY(TOP_ALIGNMENT);
        pSonEcran.add(bSon);
        pSonEcran.add(bFullScreen);

        pListeMenu.add(logo);
        pListeMenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 9)));
        pListeMenu.add(bJouer);
        pListeMenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 45)));
        pListeMenu.add(bTutoriel);
        pListeMenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 45)));
        pListeMenu.add(bRegles);
        pListeMenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 45)));
        pListeMenu.add(bQuitter);

        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 45)));
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
        Fenetre fenetre = (Fenetre) SwingUtilities.getWindowAncestor(this);
        fenetre.getPileCarte().show(fenetre.panelPrincipal, "tutoriel");
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
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.dispose();
        if (maximized) {
            bFullScreen.changeImage(CHEMIN_RESSOURCE + "/bouton/fullscreen.png", CHEMIN_RESSOURCE + "/bouton/fullscreen_hover.png");
            maximized = false;
            f.setUndecorated(false);
            f.setMinimumSize(DEFAULT_FENETRE_TAILLE);
            f.setSize(DEFAULT_FENETRE_TAILLE);
        } else {
            bFullScreen.changeImage(CHEMIN_RESSOURCE + "/bouton/unfullscreen.png", CHEMIN_RESSOURCE + "/bouton/unfullscreen_hover.png");
            maximized = true;
            f.setUndecorated(true);
            f.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        }
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * Met la fenêtre en fullscreen ou non.
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionSon(ActionEvent e) {
        if (muted) {
            bSon.changeImage(CHEMIN_RESSOURCE + "/bouton/son_on.png", CHEMIN_RESSOURCE + "/bouton/son_on_hover.png");
            muted = false;
        } else {
            bSon.changeImage(CHEMIN_RESSOURCE + "/bouton/son_off.png", CHEMIN_RESSOURCE + "/bouton/son_off_hover.png");
            muted = true;
        }
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info info: infos) {
            Mixer mixer = AudioSystem.getMixer(info);
            Line[] lines = mixer.getSourceLines();
            for(Line line : lines) {
                BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
                if (bc != null) {
                    bc.setValue(muted);
                }
            }
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
            BufferedImage img = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/base.png"));
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

            BufferedImage img_colonnes = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/columns.png"));
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