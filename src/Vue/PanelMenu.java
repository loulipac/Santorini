package Vue;

import Utile.*;
import Vue.PanelPartie.PanelPlateau.PanelPlateau;

import static Utile.Constante.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class PanelMenu extends JPanel {
    private Bouton bFullScreen;
    private Bouton bSon;
    private final LecteurSon son_bouton;
    private boolean maximized = false;
    private boolean muted = false;
    private final Dimension taille_fenetre;


    public PanelMenu(Dimension _taille_fenetre) {
        taille_fenetre = _taille_fenetre;
        son_bouton = new LecteurSon("menu_click.wav");
        initialiserComposant();
    }

    private void initialiserComposant() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLayeredPane main_panel = new JLayeredPane();
        main_panel.setOpaque(false);
        main_panel.setLayout(new OverlayLayout(main_panel));

        JPanel main_contenu = new JPanel();
        main_contenu.setOpaque(false);
        main_contenu.setLayout(new BoxLayout(main_contenu, BoxLayout.Y_AXIS));
        main_contenu.setMaximumSize(taille_fenetre);

        JPanel floating_button = new JPanel();
        floating_button.setLayout(new BorderLayout());
        floating_button.setOpaque(false);
        floating_button.setMaximumSize(new Dimension((int) (taille_fenetre.width - taille_fenetre.height * 0.15), (int) (taille_fenetre.height - taille_fenetre.height * 0.15)));
        JPanel constraint = new JPanel();
        constraint.setOpaque(false);
        floating_button.setLayout(new BorderLayout());

        double ratio_marge = 0.02;
        double ratio_logo = 0.35;
        double ratio_footer = 0.10;
        double taille_restante = taille_fenetre.height - taille_fenetre.height * (ratio_logo + ratio_footer) - (taille_fenetre.height * ratio_marge) * 5;
        double height = taille_restante / 5;

        Dimension taille_bouton = new Dimension(
                (int) (height * RATIO_BOUTON_CLASSIQUE),
                (int) (height)
        );

        Dimension taille_petit_bouton = new Dimension(
                (int) (height * RATIO_BOUTON_PETIT),
                (int) (height)
        );

        Bouton bJouer = new Bouton(CHEMIN_RESSOURCE + "/bouton/jouer.png", CHEMIN_RESSOURCE + "/bouton/jouer_hover.png",
                taille_bouton.width, taille_bouton.height, this::actionBoutonJouer);
        Bouton bJouerLigne = new Bouton(CHEMIN_RESSOURCE + "/bouton/jouer_ligne.png", CHEMIN_RESSOURCE + "/bouton/jouer_ligne_hover.png",
                taille_bouton.width, taille_bouton.height, this::actionBoutonJouerLigne);
        Bouton bCharger = new Bouton(CHEMIN_RESSOURCE + "/bouton/charger_partie.png", CHEMIN_RESSOURCE + "/bouton/charger_partie_hover.png",
                taille_bouton.width, taille_bouton.height, this::actionCharger);

        Bouton bReglesTutoriel = new Bouton(CHEMIN_RESSOURCE + "/bouton/regles_tutoriel.png", CHEMIN_RESSOURCE + "/bouton/regles_tutoriel_hover.png",
                taille_bouton.width, taille_bouton.height, this::actionBoutonRegles);


        Bouton bQuitter = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter.png", CHEMIN_RESSOURCE + "/bouton/quitter_hover.png",
                taille_bouton.width, taille_bouton.height, this::actionBoutonQuitter);

        bFullScreen = new Bouton(CHEMIN_RESSOURCE + "/bouton/fullscreen.png", CHEMIN_RESSOURCE + "/bouton/fullscreen_hover.png",
                taille_petit_bouton.width, taille_bouton.height, this::actionFullscreen);
        bSon = new Bouton(CHEMIN_RESSOURCE + "/bouton/son_on.png", CHEMIN_RESSOURCE + "/bouton/son_on_hover.png",
                taille_petit_bouton.width, taille_bouton.height, this::actionSon);

        /* Label */
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new GridBagLayout());
        ImageIcon logo_img = new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo_hd.png");
        double ratio_logo_img = (double) logo_img.getIconWidth() / logo_img.getIconHeight();
        double taille_logo = taille_fenetre.height * 0.15;
        ImageIcon logo_resize = new ImageIcon(logo_img.getImage().getScaledInstance((int) (taille_logo * ratio_logo_img), (int) (taille_logo), Image.SCALE_SMOOTH));
        JLabel logo = new JLabel(logo_resize);
        logo.setAlignmentX(CENTER_ALIGNMENT);
        Dimension logoPanel_taille = new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * ratio_logo));
        logoPanel.setMaximumSize(logoPanel_taille);
        logoPanel.setPreferredSize(logoPanel_taille);
        logoPanel.add(logo);

        /* Adding */
        bFullScreen.setAlignmentY(TOP_ALIGNMENT);
        bSon.setAlignmentY(TOP_ALIGNMENT);
        constraint.setBorder(BorderFactory.createEmptyBorder((int) (taille_bouton.height * 0.1), (int) (taille_bouton.height * 0.1), 0, 0));
        constraint.add(bSon, BorderLayout.NORTH);
        constraint.add(bFullScreen, BorderLayout.NORTH);
        floating_button.add(constraint, BorderLayout.EAST);

        Dimension taille_margin = new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * ratio_marge));

        main_contenu.add(logoPanel);
        addMargin(main_contenu, taille_margin);
        main_contenu.add(bJouer);
        addMargin(main_contenu, taille_margin);
        main_contenu.add(bJouerLigne);
        addMargin(main_contenu, taille_margin);
        main_contenu.add(bCharger);
        addMargin(main_contenu, taille_margin);
        main_contenu.add(bReglesTutoriel);
        addMargin(main_contenu, taille_margin);
        main_contenu.add(bQuitter);
        addMargin(main_contenu, new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * ratio_footer)));

        main_panel.add(main_contenu, JLayeredPane.DEFAULT_LAYER);
        main_panel.add(floating_button, JLayeredPane.POPUP_LAYER);
        add(main_panel);

        setBackground(new Color(47, 112, 162));
    }

    private void addMargin(JPanel parent, Dimension taille) {
        parent.add(Box.createRigidArea(taille));
    }

    /**
     * Remplace le contenu de la fenetre par les options
     */
    private void actionBoutonJouer(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("options");
    }

    /**
     * Affiche le panel de jeu en ligne
     */
    private void actionBoutonJouerLigne(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("multi");
    }

    /**
     * Affiche un interface pour choisir un fichier, et charge une partie si un ficheir est choisi.
     */
    private void actionCharger(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);

        JFileChooser chooser = new JFileChooser(SAVES_PATH);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sauvegardes", "sav");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File fichier = new File(SAVES_PATH + chooser.getSelectedFile().getName());
                Scanner lecteur = new Scanner(fichier);
                f.setPanel(new PanelPlateau(getSize(), lecteur));
            } catch (FileNotFoundException ex) {
                System.out.println("Le fichier " + chooser.getSelectedFile().getName() + " n'existe pas");
                ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Le fichier n'a pas le bon format");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Remplace le contenu de la fenetre par les règles du jeu.s
     */
    private void actionBoutonRegles(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("regles");
    }

    /**
     * Quitte le jeu et ferme la fenêtre.
     */
    private void actionBoutonQuitter(ActionEvent e) {
        son_bouton.joueSon(false);
        System.exit(0);
    }

    /**
     * Met la fenêtre en fullscreen ou non.
     */
    private void actionFullscreen(ActionEvent e) {
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
     */
    private void actionSon(ActionEvent e) {
        if (muted) {
            bSon.changeImage(CHEMIN_RESSOURCE + "/bouton/son_on.png", CHEMIN_RESSOURCE + "/bouton/son_on_hover.png");
            muted = false;
        } else {
            bSon.changeImage(CHEMIN_RESSOURCE + "/bouton/son_off.png", CHEMIN_RESSOURCE + "/bouton/son_off_hover.png");
            muted = true;
        }
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : infos) {
            Mixer mixer = AudioSystem.getMixer(info);
            Line[] lines = mixer.getSourceLines();
            for (Line line : lines) {
                BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
                if (bc != null) {
                    bc.setValue(muted);
                }
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utile.dessineBackground(g, getSize(), this);
    }
}