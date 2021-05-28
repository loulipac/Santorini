package Vue;

import static Modele.Constante.*;

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
    int ia1_mode, ia2_mode;
    Bouton on_off_ia;
    Image colonne_rouge, colonne_bleu, arriere_plan, colonne_fin;
    ParametrePanel pp;

    /**
     * Initialise la fenêtre de jeu et charge la police et les images en mémoire.
     *
     * @param _taille_fenetre
     * @see PanelPlateau#initialiserPanel()
     */
    public PanelPlateau(Dimension _taille_fenetre, int ia1_mode, int ia2_mode) {
        this.taille_fenetre = _taille_fenetre;
        this.ia1_mode = ia1_mode;
        this.ia2_mode = ia2_mode;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 40);
        initialiserPanel();

        colonne_rouge = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_rouge.png");
        colonne_bleu = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_bleu.png");
        colonne_fin = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_berger.png");
        arriere_plan = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/artwork/fond_de_jeu.png");
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
        main_panel.add(game, JLayeredPane.DEFAULT_LAYER);
        main_panel.add(pp, JLayeredPane.POPUP_LAYER);
        add(main_panel);
    }

    private class ActionEchap extends AbstractAction {
        public ActionEchap() {
            super();
            putValue(SHORT_DESCRIPTION, "Afficher les paramètres");
            putValue(MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (pp.isVisible()) pp.setVisible(false);
            else pp.setVisible(true);
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
        Bouton acceleration;
        ArrayList<Integer> niveauAcceleration;
        int index_acceleration;

        /**
         * Constructeur pour JGamePanel. Rajoute des components au JPanel.
         *
         * @param _taille_h
         */
        public JGamePanel(float _taille_h) {
            this.taille_h = _taille_h - 0.05f;
            setLayout(new GridBagLayout());

            JPanel container = new JPanel();

            container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
            container.setOpaque(false);
            setOpaque(false);
            setPreferredSize(new Dimension((int) (taille_fenetre.width), (int) (taille_fenetre.height * taille_h)));

            JPanel parametres = new JPanel();

            Dimension size = new Dimension((int) (taille_fenetre.width * 0.2), (int) (taille_fenetre.height * taille_h));
            parametres.setOpaque(false);
            parametres.setPreferredSize(size);
            parametres.setMaximumSize(size);

            Bouton bParametres = new Bouton(
                    CHEMIN_RESSOURCE + "/bouton/parametres.png",
                    CHEMIN_RESSOURCE + "/bouton/parametres_hover.png",
                    taille_fenetre.height / 19,
                    taille_fenetre.height / 19
            );


            ActionEchap echap = new ActionEchap();
            PanelPlateau.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "echap");
            PanelPlateau.this.getActionMap().put("echap", echap);

            bParametres.addActionListener(echap);
            parametres.add(bParametres);
            if (ia2_mode != 0) {
                jeu = new Jeu(5, 5, PanelPlateau.this, ia1_mode, ia2_mode);
                jg = new JeuGraphique(jeu);
            } else if (ia1_mode != 0) {
                jeu = new Jeu(5, 5, PanelPlateau.this, ia1_mode, 0);
                jg = new JeuGraphique(jeu);
                jg.addMouseListener(new EcouteurDeSouris(jg, jeu));
            } else {
                jeu = new Jeu(5, 5, PanelPlateau.this, 0, 0);
                jg = new JeuGraphique(jeu);
                jg.addMouseListener(new EcouteurDeSouris(jg, jeu));
            }
            jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg));

            JPanel histo_bouton = new JPanel();
            histo_bouton.setOpaque(false);
            histo_bouton.setPreferredSize(size);
            histo_bouton.setMaximumSize(size);

            Bouton histo_annuler = new Bouton(CHEMIN_RESSOURCE + "/bouton/arriere.png", CHEMIN_RESSOURCE + "/bouton/arriere_hover.png", taille_fenetre.height / 19, taille_fenetre.height / 19);

            Bouton histo_refaire = new Bouton(CHEMIN_RESSOURCE + "/bouton/avant.png", CHEMIN_RESSOURCE + "/bouton/avant_hover.png", taille_fenetre.height / 19, taille_fenetre.height / 19);

            if(ia1_mode != 0) {
                on_off_ia = new Bouton(CHEMIN_RESSOURCE + "/bouton/running.png", CHEMIN_RESSOURCE + "/bouton/running_hover.png", taille_fenetre.height / 19, taille_fenetre.height / 19);
                index_acceleration = 0;
                niveauAcceleration = new ArrayList<>();
                niveauAcceleration.add(1);
                niveauAcceleration.add(2);
                niveauAcceleration.add(4);
                niveauAcceleration.add(8);
                niveauAcceleration.add(16);
                niveauAcceleration.add(32);
                niveauAcceleration.add(64);
                acceleration = new Bouton(CHEMIN_RESSOURCE + "/bouton/vide.png", CHEMIN_RESSOURCE + "/bouton/vide.png", taille_fenetre.height / 20, taille_fenetre.height / 20);
                acceleration.setText("x" + niveauAcceleration.get(index_acceleration));
                Font lilli_belle_tmp = new Font("Lily Script One", Font.TRUETYPE_FONT, 20);
                acceleration.setFont(lilli_belle_tmp);
                on_off_ia.addActionListener(PanelPlateau.this::switchOnOffIA);
                acceleration.addActionListener(this::accelerationIA);
            }


            histo_annuler.addActionListener(PanelPlateau.this::actionUndo);
            histo_refaire.addActionListener(PanelPlateau.this::actionRedo);
            histo_bouton.add(histo_annuler);
            if(ia1_mode != 0) histo_bouton.add(on_off_ia);
            histo_bouton.add(histo_refaire);
            if(ia1_mode != 0) histo_bouton.add(acceleration);

            // Calcul de la taille de la grille selon la taille de la fenêtre

            //int taille_case_largeur = largeur / PLATEAU_COLONNES;
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
            container.add(histo_bouton);
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

        public void accelerationIA(ActionEvent e) {
            index_acceleration++;
            if (index_acceleration >= niveauAcceleration.size()) {
                index_acceleration = 0;
            }
            acceleration.setText("x" + niveauAcceleration.get(index_acceleration));
            jeu.accelererIA(niveauAcceleration.get(index_acceleration));
        }

    }

    private class ParametrePanel extends JPanel {
        private Bouton bAbandonner, bNouvellePartie, bSauvegarder, bReprendre;
        private LecteurSon son_bouton;

        private class BackgroundPanel extends JPanel {
            public BackgroundPanel() {
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
            super();
            setOpaque(false);
            setLayout(new GridBagLayout());
            setMaximumSize(taille_fenetre);


            /* JPanel */
            BackgroundPanel contenu = new BackgroundPanel();
            contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

            contenu.setAlignmentX(CENTER_ALIGNMENT);
            contenu.setMaximumSize(new Dimension((int) (taille_fenetre.width * 0.55), taille_fenetre.height * 2 / 3));
            contenu.setPreferredSize(new Dimension((int) (taille_fenetre.width * 0.55), taille_fenetre.height * 2 / 3));

            JLabel parametres_texte = new JLabel("Paramètres");
            parametres_texte.setForeground(new Color(82, 60, 43));
            parametres_texte.setFont(lilly_belle);
            parametres_texte.setAlignmentX(CENTER_ALIGNMENT);

            /* Boutons*/
            JButton bCharger = new JButton("CHARGER");
            bAbandonner = new Bouton(CHEMIN_RESSOURCE + "/bouton/abandonner.png", CHEMIN_RESSOURCE + "/bouton/abandonner_hover.png", taille_fenetre.width / 6, taille_fenetre.width / 30);
            bNouvellePartie = new Bouton(CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png", CHEMIN_RESSOURCE + "/bouton/nouvelle_partie_hover.png", taille_fenetre.width / 6, taille_fenetre.width / 30);
            bSauvegarder = new Bouton(CHEMIN_RESSOURCE + "/bouton/sauvegarder.png", CHEMIN_RESSOURCE + "/bouton/sauvegarder_hover.png", taille_fenetre.width / 6, taille_fenetre.width / 30);
            bReprendre = new Bouton(CHEMIN_RESSOURCE + "/bouton/reprendre.png", CHEMIN_RESSOURCE + "/bouton/reprendre_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);

            /* Evenements */
            ActionEchap echap = new ActionEchap();
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "echap");
            getActionMap().put("echap", echap);

            bAbandonner.addActionListener(this::actionBoutonAbandonner);
            bReprendre.addActionListener(echap);
            bNouvellePartie.addActionListener(this::actionBoutonNouvelle);
            bSauvegarder.addActionListener(this::actionBoutonSauvergarder);
            bCharger.addActionListener(this::actionCharger);

            /* Adding */
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(parametres_texte);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(bAbandonner);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(bNouvellePartie);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(bSauvegarder);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(bCharger);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(bReprendre);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            add(contenu);
        }

        public void actionBoutonAbandonner(ActionEvent e) {
            Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
            f.setPanel(new PanelMenu(taille_fenetre));
        }

        public void actionBoutonSauvergarder(ActionEvent e) {
            jeu.sauvegarder();
            pp.setVisible(false);
        }

        public void actionBoutonNouvelle(ActionEvent e) {
            Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
            f.setPanel(new PanelPlateau(taille_fenetre, ia1_mode, ia2_mode));
        }


        public void actionCharger(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(SAVES_PATH);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Sauvegardes", "sav");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
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

    /**
     * Crée un JPanel modifié qui ajoute le logo et le texte designant quel joueur joue.
     */
    public class TopPanel extends JPanel {
        /**
         * Constructeur de TopPanel. Ajoute les élements et définis les valeurs des propriétés de chacuns.
         *
         * @param taille_h
         */
        public TopPanel(float taille_h) {
            setOpaque(false);

            //BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            //setLayout(boxlayout);
            setLayout(new GridBagLayout());

            Dimension size = new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * taille_h));
            setPreferredSize(size);
            setMaximumSize(size);
            setMinimumSize(size);

            /*JLabel logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo.png"));
            logo.setAlignmentX(CENTER_ALIGNMENT);
            add(logo);*/

            jt = new JLabel("C'est au tour du Joueur 1");
            jt.setAlignmentX(CENTER_ALIGNMENT);
            jt.setAlignmentY(CENTER_ALIGNMENT);
            jt.setOpaque(false);
            jt.setFont(lilly_belle);
            jt.setForeground(Color.WHITE);
            add(jt);
        }
    }

    /**
     * Dessine l'image de fond et la bannière (colonne).
     *
     * @param g
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
            Image colonne = null;
            if (jeu.estJeufini()) {
                colonne = colonne_fin;
            } else {
                colonne = (jeu.getJoueur_en_cours() == JOUEUR1 ? colonne_bleu : colonne_rouge);
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

    public void actionUndo(ActionEvent e) {
        jeu.annuler();
        jg.repaint();
    }

    public void actionRedo(ActionEvent e) {
        jeu.refaire();
        jg.repaint();
    }

    private void changeVictory() {
        jt.setText("Joueur " + (jeu.getGagnant().getNum_joueur() / JOUEUR1) + " gagne");
    }

    /**
     * Modifie le texte qui affiche quel joueur doit jouer.
     */
    @Override
    public void miseAjour() {
        if (jeu.estJeufini()) {
            changeVictory();
        } else {
            jt.setText(jeu.getJoueur_en_cours() == JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2");
        }
        repaint();
    }
}
