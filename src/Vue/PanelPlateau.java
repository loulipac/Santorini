package Vue;

import static Modele.Constante.*;

import IO.IO;
import Modele.ConfigurationPartie;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe générant la fenêtre de jeu.
 */
public class PanelPlateau extends JPanel implements Observer {

    private Jeu jeu;
    private JeuGraphique jg;
    Font lilly_belle;
    JLabel jt;
    Dimension taille_fenetre;
    int ia1_mode;
    int ia2_mode;
    Bouton on_off_ia;
    Image colonne_rouge;
    Image colonne_bleu;
    Image arriere_plan;
    Image colonne_fin;
    ParametrePanel pp;
    VictoirePanel victoire_panel;
    boolean is_finish_draw;
    IO netUser;
    ConfigurationPartie config;


    /**
     * Initialise la fenêtre de jeu et charge la police et les images en mémoire.
     *
     * @see PanelPlateau#initialiserPanel()
     */
    public PanelPlateau(Dimension _taille_fenetre, ConfigurationPartie config) {
        this.taille_fenetre = _taille_fenetre;
        this.config = config;
        is_finish_draw = false;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.PLAIN, 40);
        initialiserPanel();

        colonne_rouge = Utile.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_rouge.png");
        colonne_bleu = Utile.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_bleu.png");
        colonne_fin = Utile.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_berger.png");
        arriere_plan = Utile.readImage(CHEMIN_RESSOURCE + "/artwork/fond_de_jeu.png");
    }

    public PanelPlateau(Dimension _taille_fenetre, String filename) {
        this(_taille_fenetre, new ConfigurationPartie(0, 0));
        jeu.charger(filename);
    }

    public PanelPlateau(Dimension _taille_fenetre, IO netUser) {
        this(_taille_fenetre, new ConfigurationPartie(0, 0));
        this.netUser = netUser;
        jeu.setNetUser(netUser);
        if (netUser.getNumJoueur() == JOUEUR1) jt.setText("C'est au tour de " + netUser.getNomJoueur());
        else jt.setText("C'est au tour de " + netUser.getNomAdversaire());
    }

    /**
     * Ajoute tous les composants au panel.
     *
     * @see TopPanel
     * @see JGamePanel
     */
    public void initialiserPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // layered pane pour la superposition des panels
        JLayeredPane main_panel = new JLayeredPane();
        main_panel.setOpaque(false);
        main_panel.setLayout(new OverlayLayout(main_panel));

        // panel de base avec le jeu
        JPanel game = new JPanel();
        game.setOpaque(false);
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        game.setMaximumSize(taille_fenetre);

        TopPanel tp = new TopPanel(0.20f);
        JGamePanel jgame = new JGamePanel(0.80f);
        game.add(tp);
        game.add(jgame);

        pp = new ParametrePanel();
        pp.setVisible(false);

        victoire_panel = new VictoirePanel();
        victoire_panel.setVisible(false);

        main_panel.add(game, JLayeredPane.DEFAULT_LAYER);
        main_panel.add(pp, JLayeredPane.POPUP_LAYER);
        main_panel.add(victoire_panel, JLayeredPane.POPUP_LAYER);
        add(main_panel);
        is_finish_draw = true;
    }

    private class ActionEchap extends AbstractAction {
        public ActionEchap() {
            super();
            putValue(SHORT_DESCRIPTION, "Afficher les paramètres");
            putValue(MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!victoire_panel.isVisible()) pp.setVisible(!pp.isVisible());
        }
    }

    /**
     * Crée un JPanel modifié qui génère deux zones de boutons de 20% de la taille de la fenêtre.
     * Génère la grille de jeu.
     *
     * @see JeuGraphique
     * @see Jeu
     */
    public class JGamePanel extends JPanel {
        int taille_margin;
        float taille_h;

        /**
         * Constructeur pour JGamePanel. Rajoute des components au JPanel.
         */
        public JGamePanel(float _taille_h) {
            this.taille_h = _taille_h - 0.05f;
            setLayout(new GridBagLayout());


            JPanel container = new JPanel();

            container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
            container.setOpaque(false);
            setOpaque(false);
            setPreferredSize(new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * taille_h)));

            JPanel parametres = new JPanel();

            Dimension size = new Dimension((int) (taille_fenetre.width * 0.2), (int) (taille_fenetre.height * taille_h));
            parametres.setOpaque(false);
            parametres.setPreferredSize(size);
            parametres.setMaximumSize(size);

            int bouton_height = (int) (size.height * 0.1);
            Dimension size_bouton = new Dimension((int) (bouton_height * RATIO_BOUTON_PETIT), bouton_height);
            Bouton bParametres = new Bouton(
                    CHEMIN_RESSOURCE + "/bouton/parametres.png",
                    CHEMIN_RESSOURCE + "/bouton/parametres_hover.png",
                    size_bouton
            );


            ActionEchap echap = new ActionEchap();
            PanelPlateau.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ECHAP_KEY);
            PanelPlateau.this.getActionMap().put(ECHAP_KEY, echap);

            bParametres.addActionListener(echap);
            parametres.add(bParametres);

            jeu = new Jeu(PanelPlateau.this, config);
            jg = new JeuGraphique(jeu);
            if (ia2_mode == 0) {
                jg.addMouseListener(new EcouteurDeSouris(jg, jeu, PanelPlateau.this));
            }
            jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg, PanelPlateau.this));

            SidePanelRight side_panel = new SidePanelRight(size);
            side_panel.setMaximumSize(size);
            side_panel.setPreferredSize(size);

            // Calcul de la taille de la grille selon la taille de la fenêtre

            int taille_case = ((int) (taille_fenetre.height * taille_h)) / PLATEAU_LIGNES;

            jg.setPreferredSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));
            jg.setMaximumSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));

            int taille = taille_fenetre.width;

            // place de la grille
            taille -= taille_case * PLATEAU_COLONNES;

            // place des menus
            taille -= taille_fenetre.width * 0.4;

            taille_margin = taille / 4;

            addMargin(container);
            container.add(parametres);
            addMargin(container);
            container.add(jg);
            addMargin(container);
            container.add(side_panel);
            addMargin(container);
            add(container);
        }

        /**
         * Crée un JPanel servant de marge.
         */
        private void addMargin(JPanel c) {
            JPanel j = new JPanel();
            j.setOpaque(false);
            Dimension size = new Dimension(taille_margin, (int) (taille_fenetre.height * taille_h));
            j.setPreferredSize(size);
            j.setMaximumSize(size);
            c.add(j);
        }

    }

    private class SidePanelRight extends JPanel {
        Bouton acceleration;
        ArrayList<Integer> niveauAcceleration;
        int index_acceleration;
        final int TITRE_TAILLE = 30;
        Dimension size;
        Bouton histo_annuler;
        Bouton histo_refaire;

        public SidePanelRight(Dimension size) {
            this.size = size;
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            int height = (int) (size.height * 0.1);

            Dimension size_pane = new Dimension(size.width, height);

            Dimension size_pane_button = new Dimension(size.width, (int) (height + (size.height * 0.05)));
            Dimension size_button = new Dimension((int) (height * RATIO_BOUTON_PETIT), height);

            JPanel panel_historique = creerTitre("Historique", TITRE_TAILLE, size_pane);

            JPanel histo_bouton = new JPanel();
            histo_bouton.setOpaque(false);
            histo_bouton.setPreferredSize(size_pane_button);
            histo_bouton.setMaximumSize(size_pane_button);

            histo_annuler = new Bouton(CHEMIN_RESSOURCE + "/bouton/arriere.png", CHEMIN_RESSOURCE + "/bouton/arriere_hover.png",
                    size_button, this::actionUndo);
            histo_refaire = new Bouton(CHEMIN_RESSOURCE + "/bouton/avant.png", CHEMIN_RESSOURCE + "/bouton/avant_hover.png",
                    size_button, this::actionRedo);

            histo_bouton.add(histo_annuler);
            histo_bouton.add(histo_refaire);

            JPanel panel_ia = null;
            JPanel ia_bouton = null;
            JPanel panel_vit_ia = null;
            JPanel vit_ia_bouton = null;

            if (config.getIaMode1() != 0) {
                // titre IA
                panel_ia = creerTitre("IA", TITRE_TAILLE, size_pane);

                // boutons
                // boutons
                ia_bouton = new JPanel();
                ia_bouton.setOpaque(false);
                ia_bouton.setPreferredSize(size_pane_button);
                ia_bouton.setMaximumSize(size_pane_button);

                on_off_ia = new Bouton(CHEMIN_RESSOURCE + "/bouton/running.png", CHEMIN_RESSOURCE + "/bouton/running_hover.png",
                        size_button,
                        PanelPlateau.this::switchOnOffIA);

                ia_bouton.add(on_off_ia);

                // titre
                panel_vit_ia = creerTitre("Vitesse IA", TITRE_TAILLE, size_pane);

                // boutons
                vit_ia_bouton = new JPanel();
                vit_ia_bouton.setOpaque(false);
                vit_ia_bouton.setPreferredSize(size_pane_button);
                vit_ia_bouton.setMaximumSize(size_pane_button);

                acceleration = new Bouton(CHEMIN_RESSOURCE + "/bouton/x1.png", CHEMIN_RESSOURCE + "/bouton/x1.png",
                        size_button);
                Bouton plus = new Bouton(CHEMIN_RESSOURCE + "/bouton/plus.png", CHEMIN_RESSOURCE + "/bouton/plus.png",
                        size_button,
                        this::accelerationIA);
                Bouton minus = new Bouton(CHEMIN_RESSOURCE + "/bouton/minus.png", CHEMIN_RESSOURCE + "/bouton/minus.png",
                        size_button,
                        this::ralentirIA);

                index_acceleration = 0;
                niveauAcceleration = new ArrayList<>();
                niveauAcceleration.add(1);
                niveauAcceleration.add(2);
                niveauAcceleration.add(4);
                niveauAcceleration.add(8);
                niveauAcceleration.add(16);
                niveauAcceleration.add(32);
                Font lilli_belle_tmp = new Font("Lily Script One", Font.PLAIN, 20);
                acceleration.setFont(lilli_belle_tmp);

                vit_ia_bouton.add(minus);
                vit_ia_bouton.add(acceleration);
                vit_ia_bouton.add(plus);
            }


            add(panel_historique);
            add(histo_bouton);
            if (config.getIaMode1() != 0) {
                add(panel_ia);
                add(ia_bouton);
                add(panel_vit_ia);
                add(vit_ia_bouton);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            try {
                BufferedImage bg_regles = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));
                g2d.drawImage(
                        bg_regles,
                        0,
                        0,
                        size.width,
                        size.height,
                        this
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur image de fond: " + e.getMessage());
            }

            if(jeu.getHistorique().peutAnnuler()){
                histo_annuler.setEnabled(true);
            }
            else
                histo_annuler.setEnabled(false);
            if(jeu.getHistorique().peutRefaire()){
                histo_refaire.setEnabled(true);
            }
            else
                histo_refaire.setEnabled(false);

        }

        private JPanel creerTitre(String _t, int _fs, Dimension _s) {
            JPanel _jpan = new JPanel();
            JLabel _lab = new JLabel(_t);
            _lab.setFont(new Font("Lily Script One", Font.PLAIN, _fs));
            _lab.setForeground(new Color(103, 69, 42));
            _jpan.setLayout(new GridBagLayout());
            _jpan.setOpaque(false);
            _jpan.add(_lab);
            _jpan.setPreferredSize(_s);
            _jpan.setMaximumSize(_s);
            return _jpan;
        }


        public void ralentirIA(ActionEvent e) {
            index_acceleration--;
            if (index_acceleration < 0) {
                index_acceleration = 0;
            }
            jeu.accelererIA(niveauAcceleration.get(index_acceleration));
            changeBoutonVitesse();
        }

        public void accelerationIA(ActionEvent e) {
            index_acceleration++;
            if (index_acceleration >= niveauAcceleration.size()) {
                index_acceleration = niveauAcceleration.size() - 1;
            }
            jeu.accelererIA(niveauAcceleration.get(index_acceleration));
            changeBoutonVitesse();
        }

        private void changeBoutonVitesse() {
            acceleration.changeImage(
                    CHEMIN_RESSOURCE + "/bouton/x" + niveauAcceleration.get(index_acceleration) + ".png",
                    CHEMIN_RESSOURCE + "/bouton/x" + niveauAcceleration.get(index_acceleration) + ".png"
            );
        }

        public void actionUndo(ActionEvent e) {
            jeu.annuler();
            jg.repaint();
        }

        public void actionRedo(ActionEvent e) {
            jeu.refaire();
            jg.repaint();
        }

    }

    public boolean isParametreVisible() {
        return pp.isVisible();
    }

    private class ParametrePanel extends JPanel {
        private class BackgroundPanel extends JPanel {
            public BackgroundPanel(Dimension taille) {
                super();
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setAlignmentX(CENTER_ALIGNMENT);
                setMaximumSize(taille);
                setPreferredSize(taille);
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
                    BufferedImage bg_panel = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));
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

        public ParametrePanel() {
            initialiserComposant();
            setOpaque(false);
            setLayout(new GridBagLayout());
            setMaximumSize(taille_fenetre);
        }

        private void initialiserComposant() {
            Dimension taille_panel = new Dimension((int) (taille_fenetre.width * 0.55), taille_fenetre.height * 2 / 3);
            BackgroundPanel contenu = new BackgroundPanel(taille_panel);

            JLabel parametres_texte = new JLabel("Paramètres");
            parametres_texte.setForeground(new Color(82, 60, 43));
            parametres_texte.setFont(lilly_belle);
            parametres_texte.setAlignmentX(CENTER_ALIGNMENT);

            double ratio_marge = 0.03;
            double taille_restante = taille_panel.height - (taille_panel.height * ratio_marge) * 9;
            double height = taille_restante / 6;

            Dimension taille_bouton = new Dimension((int) (height * RATIO_BOUTON_CLASSIQUE), (int) (height));

            /* Boutons*/
            Bouton bReprendre = new Bouton(CHEMIN_RESSOURCE + "/bouton/reprendre.png", CHEMIN_RESSOURCE + "/bouton/reprendre_hover.png",
                    taille_bouton.width,
                    taille_bouton.height);

            Bouton bNouvellePartie = new Bouton(CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png", CHEMIN_RESSOURCE + "/bouton/nouvelle_partie_hover.png",
                    taille_bouton.width,
                    taille_bouton.height);

            JPanel charger_sauvegarder = new JPanel();
            charger_sauvegarder.setOpaque(false);
            charger_sauvegarder.setPreferredSize(taille_bouton);
            charger_sauvegarder.setMaximumSize(taille_bouton);
            charger_sauvegarder.setLayout(new GridLayout(1, 2));
            Bouton bSauvegarder = new Bouton(CHEMIN_RESSOURCE + "/bouton/sauvegarder.png", CHEMIN_RESSOURCE + "/bouton/sauvegarder_hover.png",
                    taille_bouton.width / 2,
                    taille_bouton.height);
            Bouton bCharger = new Bouton(CHEMIN_RESSOURCE + "/bouton/charger.png", CHEMIN_RESSOURCE + "/bouton/charger_hover.png",
                    taille_bouton.width / 2,
                    taille_bouton.height);

            charger_sauvegarder.add(bSauvegarder);
            charger_sauvegarder.add(bCharger);

            Bouton bQuitter = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter_partie.png", CHEMIN_RESSOURCE + "/bouton/quitter_partie_hover.png",
                    taille_bouton.width,
                    taille_bouton.height);


            /* Evenements */
            ActionEchap echap = new ActionEchap();
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "echap");
            getActionMap().put("echap", echap);

            bQuitter.addActionListener(PanelPlateau.this::actionQuitter);
            bReprendre.addActionListener(echap);
            bNouvellePartie.addActionListener(PanelPlateau.this::actionBoutonNouvelle);
            bSauvegarder.addActionListener(PanelPlateau.this::actionBoutonSauvergarder);
            bCharger.addActionListener(this::actionCharger);

            /* Adding */
            Dimension margin_taille = new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_marge));
            addMargin(contenu, margin_taille);
            addMargin(contenu, margin_taille);
            contenu.add(parametres_texte);
            addMargin(contenu, margin_taille);
            addMargin(contenu, margin_taille);
            contenu.add(bReprendre);
            addMargin(contenu, margin_taille);
            contenu.add(bNouvellePartie);
            addMargin(contenu, margin_taille);
            contenu.add(charger_sauvegarder);
            addMargin(contenu, margin_taille);
            addMargin(contenu, taille_bouton);
            addMargin(contenu, margin_taille);
            contenu.add(bQuitter);
            addMargin(contenu, margin_taille);
            add(contenu);
        }

        private void addMargin(JPanel parent, Dimension taille) {
            parent.add(Box.createRigidArea(taille));
        }

        public void actionCharger(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(SAVES_PATH);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Sauvegardes", "sav");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                jeu.charger(chooser.getSelectedFile().getName());
                pp.setVisible(false);
            }
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color transparentColor = new Color(0, 0, 0, 0.4f);
            g2d.setColor(transparentColor);
            g2d.fillRect(0, 0, taille_fenetre.width, taille_fenetre.height);
            g2d.setComposite(AlphaComposite.SrcOver);
        }


    }

    private class VictoirePanel extends JPanel {
        private class BackgroundPanel extends JPanel {

            public BackgroundPanel(Dimension taille) {
                super();
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setAlignmentX(CENTER_ALIGNMENT);
                setMaximumSize(taille);
                setPreferredSize(taille);
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
                    BufferedImage bg_panel = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/parchemin.png"));
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

        JPanel titre_victoire;
        JPanel nb_tours;
        JPanel tmp_reflexion_j1;
        JPanel tmp_reflexion_j2;

        public VictoirePanel() {
            initialiserComposant();
            setOpaque(false);
            setLayout(new GridBagLayout());
            setMaximumSize(taille_fenetre);
        }

        private void initialiserComposant() {
            Dimension real_taille_panel = new Dimension((int) (taille_fenetre.width * 0.35), (int) (taille_fenetre.height * 0.9));
            BackgroundPanel contenu = new BackgroundPanel(real_taille_panel);
            Dimension taille_panel = new Dimension((int) (real_taille_panel.width * 0.75), (int) (real_taille_panel.height * 0.8));

            double ratio_titre = 0.1;
            double ratio_marge = 0.03;
            double ratio_texte = 0.05;
            double taille_restante = taille_panel.height - taille_panel.height * (ratio_titre + (ratio_marge * 10) + (ratio_texte * 4));
            float height_bouton = (float) taille_restante / 4;

            Dimension taille_bouton = new Dimension((int) (height_bouton * RATIO_BOUTON_CLASSIQUE), (int) (height_bouton));
            Bouton bVisualiser = new Bouton(CHEMIN_RESSOURCE + "/bouton/visualiser.png", CHEMIN_RESSOURCE + "/bouton/visualiser.png",
                    taille_bouton, this::actionVisualiser);
            Bouton bSauvegarder = new Bouton(CHEMIN_RESSOURCE + "/bouton/sauvegarder_partie.png", CHEMIN_RESSOURCE + "/bouton/sauvegarder_partie.png",
                    taille_bouton, PanelPlateau.this::actionBoutonSauvergarder);
            Bouton bNouvelle = new Bouton(CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png", CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png",
                    taille_bouton, PanelPlateau.this::actionBoutonNouvelle);
            Bouton bQuitter = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter.png", CHEMIN_RESSOURCE + "/bouton/quitter.png",
                    taille_bouton, PanelPlateau.this::actionQuitter);

            Dimension dim_texte = new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_texte));
            titre_victoire = creerTexte("Victoire du joueur %n", 35,
                    new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_titre)), SwingConstants.CENTER);
            nb_tours = creerTexte("%n tours passés", 20, dim_texte, SwingConstants.CENTER);
            JPanel tmp_reflexion_titre = creerTexte("Temps moyen de réflexion :", 20, dim_texte, SwingConstants.CENTER);
            tmp_reflexion_j1 = creerTexte("%n secondes pour le joueur 1", 20, dim_texte, SwingConstants.LEFT);
            tmp_reflexion_j2 = creerTexte("%n secondes pour le joueur 2", 20, dim_texte, SwingConstants.LEFT);
            Dimension taille_marge = new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_marge));

            addMargin(contenu, taille_marge);
            addMargin(contenu, taille_marge);
            contenu.add(titre_victoire);
            contenu.add(nb_tours);
            addMargin(contenu, taille_marge);
            addMargin(contenu, taille_marge);

            contenu.add(tmp_reflexion_titre);
            addMargin(contenu, taille_marge);
            contenu.add(tmp_reflexion_j1);
            addMargin(contenu, taille_marge);
            contenu.add(tmp_reflexion_j2);
            addMargin(contenu, taille_marge);

            addMargin(contenu, taille_marge);
            contenu.add(bVisualiser);
            addMargin(contenu, taille_marge);
            contenu.add(bSauvegarder);
            addMargin(contenu, taille_marge);
            contenu.add(bNouvelle);
            addMargin(contenu, taille_marge);
            contenu.add(bQuitter);
            addMargin(contenu, taille_marge);
            add(contenu);
        }

        private void actionVisualiser(ActionEvent e) {
            victoire_panel.setVisible(false);
        }

        public void changeTexte(JPanel _jp, String texte) {
            for (Component jc : _jp.getComponents()) {
                JLabel label = (JLabel) jc;
                label.setText(texte);
            }
        }

        private void addMargin(JPanel parent, Dimension taille) {
            parent.add(Box.createRigidArea(taille));
        }

        private JPanel creerTexte(String _t, int _fs, Dimension _s, int alignment) {
            JPanel _jpan = new JPanel();
            JLabel _lab = new JLabel(_t, alignment);
            _lab.setFont(new Font("Lily Script One", Font.PLAIN, _fs));
            _lab.setForeground(new Color(103, 69, 42));
            _lab.setPreferredSize(_s);
            _lab.setMaximumSize(_s);
            _jpan.setLayout(new BorderLayout());
            _jpan.setOpaque(false);
            _jpan.add(_lab, BorderLayout.CENTER);
            _jpan.setPreferredSize(_s);
            _jpan.setMaximumSize(_s);
            return _jpan;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color transparentColor = new Color(0, 0, 0, 0.4f);
            g2d.setColor(transparentColor);
            g2d.fillRect(0, 0, taille_fenetre.width, taille_fenetre.height);
            g2d.setComposite(AlphaComposite.SrcOver);
        }
    }


    /**
     * Crée un JPanel modifié qui ajoute le logo et le texte designant quel joueur joue.
     */
    public class TopPanel extends JPanel {
        /**
         * Constructeur de TopPanel. Ajoute les élements et définis les valeurs des propriétés de chacuns.
         */
        public TopPanel(float taille_h) {
            setOpaque(false);

            setLayout(new GridBagLayout());

            Dimension size = new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * taille_h));
            setPreferredSize(size);
            setMaximumSize(size);
            setMinimumSize(size);


            jt = new JLabel("C'est au tour du Joueur " + (config.getIndexJoueurCommence() + 1));
            jt.setAlignmentX(CENTER_ALIGNMENT);
            jt.setAlignmentY(CENTER_ALIGNMENT);
            jt.setOpaque(false);
            jt.setFont(lilly_belle);
            jt.setForeground(Color.WHITE);
            add(jt);
        }
    }

    public void actionBoutonNouvelle(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        if(netUser != null) {
            f.setPanel(new LobbyPanel(netUser));
        } else {
            f.setPanel(new PanelPlateau(taille_fenetre, new ConfigurationPartie(this.ia1_mode, this.ia2_mode)));
        }
    }

    public void actionQuitter(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        if(netUser != null) {
            netUser.deconnexion();
        }
        f.displayPanel("menu");
    }

    public void actionBoutonSauvergarder(ActionEvent e) {
        jeu.sauvegarder();
        pp.setVisible(false);
    }

    /**
     * Dessine l'image de fond et la bannière (colonne).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            g2d.drawImage(
                    arriere_plan,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
            Image colonne;
            if (jeu.estJeufini()) {
                colonne = colonne_fin;
            } else {
                colonne = (jeu.getJoueur_en_cours().getNum_joueur() == (config.isJoueur1Bleu() ? JOUEUR1 : JOUEUR2) ? colonne_bleu : colonne_rouge);
            }

            // float meme_ratio = (float) getWidth()/1232*191; //sert à garder le meme ratio hauteur/largeur au changement de largeur de la fenetre

            g2d.drawImage(
                    colonne,
                    0,
                    0,
                    getWidth(), (int) (getHeight() * 0.20),
                    this
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

    public void switchOnOffIA(ActionEvent e) {
        jeu.iaSwitch();
        if (jeu.getIa_statut()) {
            on_off_ia.changeImage(CHEMIN_RESSOURCE + "/bouton/running.png", CHEMIN_RESSOURCE + "/bouton/running_hover.png");
        } else {
            on_off_ia.changeImage(CHEMIN_RESSOURCE + "/bouton/stop.png", CHEMIN_RESSOURCE + "/bouton/stop_hover.png");
        }
    }

    private void changeVictory() {
        String nom_joueur = "";
        if(netUser != null) {
            if (netUser.getNumJoueur() == jeu.getJoueur_en_cours().getNum_joueur()) nom_joueur = netUser.getNomJoueur();
            else nom_joueur = netUser.getNomAdversaire();
        } else {
            nom_joueur = jeu.getGagnant().getNum_joueur() == JOUEUR1 ? "Joueur 1" : "Joueur 2";
        }
        jt.setText(nom_joueur + " gagne");

        victoire_panel.setVisible(true);
        victoire_panel.changeTexte(victoire_panel.titre_victoire, "Victoire de " + nom_joueur);
        victoire_panel.changeTexte(victoire_panel.nb_tours, jeu.getNb_tours() + " tours passés");
        victoire_panel.changeTexte(victoire_panel.tmp_reflexion_j1, "n secondes pour le joueur 1");
        victoire_panel.changeTexte(victoire_panel.tmp_reflexion_j2, "n secondes pour le joueur 2");
    }

    /**
     * Modifie le texte qui affiche quel joueur doit jouer.
     */
    @Override
    public void miseAjour() {
        if (jeu.estJeufini()) {
            changeVictory();
        } else {
            if(netUser != null) {
                if (netUser.getNumJoueur() == jeu.getJoueur_en_cours().getNum_joueur()) jt.setText("C'est au tour de " + netUser.getNomJoueur());
                else jt.setText("C'est au tour de " + netUser.getNomAdversaire());
            } else {
                jt.setText(jeu.getJoueur_en_cours().getNum_joueur() == JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2");
            }
        }

        repaint();
    }
}
