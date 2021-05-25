package Vue;

import static Modele.Constante.*;

import Modele.Jeu;

import static Modele.Constante.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe générant la fenêtre de jeu.
 */
public class PanelPlateau extends JPanel implements Observer {

    private Jeu jeu;
    private JeuGraphique jg;
    Font lilly_belle;
    JLabel jt;
    Dimension taille_fenetre;
    Image colonne_rouge, colonne_bleu, arriere_plan;
    ParametrePanel pp;

    /**
     * Initialise la fenêtre de jeu et charge la police et les images en mémoire.
     *
     * @param _taille_fenetre
     * @see PanelPlateau#initialiserPanel()
     */
    public PanelPlateau(Dimension _taille_fenetre) {
        this.taille_fenetre = _taille_fenetre;
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

        TopPanel tp = new TopPanel(0.25f);
        JGamePanel jgame = new JGamePanel(0.75f);
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
            if(pp.isVisible()) pp.setVisible(false);
            else pp.setVisible(true);
            /*Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(PanelPlateau.this);
            f.getPileCarte().show(f.panelPrincipal, "parametres");*/
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
         *
         * @param _taille_h
         */
        public JGamePanel(float _taille_h) {
            this.taille_h = _taille_h - 0.05f;

            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setOpaque(false);
            setPreferredSize(new Dimension((int) (taille_fenetre.width), (int) (taille_fenetre.height * taille_h)));

            JPanel parametres = new JPanel();

            Dimension size = new Dimension((int) (taille_fenetre.width * 0.2), (int) (taille_fenetre.height * taille_h));
            parametres.setOpaque(false);
            parametres.setPreferredSize(size);
            parametres.setMaximumSize(size);


            //parametres.setBorder(new LineBorder(Color.GREEN));
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

            jeu = new Jeu(5, 5, PanelPlateau.this);
            jg = new JeuGraphique(jeu);
            jg.addMouseListener(new EcouteurDeSouris(jg));
            jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg));

            JPanel histo_bouton = new JPanel();
            histo_bouton.setOpaque(false);
            histo_bouton.setPreferredSize(size);
            histo_bouton.setMaximumSize(size);

            Bouton histo_annuler = new Bouton(CHEMIN_RESSOURCE + "/bouton/arriere.png", CHEMIN_RESSOURCE + "/bouton/arriere_hover.png", taille_fenetre.height / 19, taille_fenetre.height / 19);
            Bouton histo_refaire = new Bouton(CHEMIN_RESSOURCE + "/bouton/avant.png", CHEMIN_RESSOURCE + "/bouton/avant_hover.png", taille_fenetre.height / 19, taille_fenetre.height / 19);
            histo_annuler.addActionListener(PanelPlateau.this::actionUndo);
            histo_refaire.addActionListener(PanelPlateau.this::actionRedo);
            histo_bouton.add(histo_annuler);
            histo_bouton.add(histo_refaire);

            // Calcul de la taille de la grille selon la taille de la fenêtre

            //int taille_case_largeur = largeur / jeu.getPlateau().getColonnes();
            int taille_case = ((int) (taille_fenetre.height * taille_h)) / jeu.getPlateau().getLignes();

            jg.setPreferredSize(new Dimension(taille_case * jeu.getPlateau().getColonnes(), taille_case * jeu.getPlateau().getLignes()));
            jg.setMaximumSize(new Dimension(taille_case * jeu.getPlateau().getColonnes(), taille_case * jeu.getPlateau().getLignes()));

            int taille = taille_fenetre.width;

            // place de la grille
            taille -= taille_case * jeu.getPlateau().getColonnes();

            // place des menus
            taille -= taille_fenetre.width * 0.4;

            taille_margin = taille / 4;

            addMargin();
            add(parametres);
            addMargin();
            add(jg);
            addMargin();
            add(histo_bouton);
            addMargin();
        }

        /**
         * Crée un JPanel servant de marge.
         */
        private void addMargin() {
            JPanel j = new JPanel();
            j.setOpaque(false);
            //j.setBorder(new LineBorder(Color.BLUE));
            Dimension size = new Dimension(taille_margin, (int) (taille_fenetre.height * taille_h));
            j.setPreferredSize(size);
            j.setMaximumSize(size);
            add(j);
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
                    BufferedImage bg_panel = ImageIO.read(new File(CHEMIN_RESSOURCE+"/artwork/bg_regles.png"));
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
            setOpaque(false);
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxlayout);


            JLabel parametres_texte = new JLabel("Paramètres");

            parametres_texte.setForeground(new Color(82,60,43));
            parametres_texte.setFont(lilly_belle);

            /* JPanel */
            BackgroundPanel contenu = new BackgroundPanel();

            contenu.setAlignmentX(CENTER_ALIGNMENT);
            contenu.setMaximumSize(new Dimension((int) (taille_fenetre.width * 0.55), taille_fenetre.height * 2/3));

            /* Boutons*/
            bAbandonner = new Bouton(CHEMIN_RESSOURCE+"/bouton/abandonner.png", CHEMIN_RESSOURCE+"/bouton/abandonner_hover.png", taille_fenetre.width / 6, taille_fenetre.width / 30);
            bNouvellePartie = new Bouton(CHEMIN_RESSOURCE+"/bouton/nouvelle_partie.png", CHEMIN_RESSOURCE+"/bouton/nouvelle_partie_hover.png", taille_fenetre.width / 6, taille_fenetre.width / 30);
            bSauvegarder = new Bouton(CHEMIN_RESSOURCE+"/bouton/sauvegarder.png", CHEMIN_RESSOURCE+"/bouton/sauvegarder_hover.png", taille_fenetre.width / 6, taille_fenetre.width / 30);
            bReprendre = new Bouton(CHEMIN_RESSOURCE+"/bouton/reprendre.png", CHEMIN_RESSOURCE+"/bouton/reprendre_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);

            /* Evenements */
            ActionEchap echap = new ActionEchap();
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "echap");
            getActionMap().put("echap", echap);

            bAbandonner.addActionListener(this::actionBoutonAbandonner);
            bReprendre.addActionListener(echap);
            bNouvellePartie.addActionListener(this::actionBoutonNouvelle);

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
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            contenu.add(bReprendre);
            contenu.add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
            add(contenu);
        }

        public void actionBoutonAbandonner(ActionEvent e) {
            Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
            f2.removePlateau();
            f2.getPileCarte().show(f2.panelPrincipal, "menu");
        }

        public void actionBoutonNouvelle(ActionEvent e) {
            Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
            f2.removePlateau();
            f2.setPlateau(new PanelPlateau(taille_fenetre));
            f2.getPileCarte().show(f2.panelPrincipal, "plateau");
        }

        @Override
        public void paintComponents(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Chargement de l"image de fond
            try {
                BufferedImage img_colonnes = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/columns.png"));
                g2d.drawImage(
                        img_colonnes,
                        0,
                        0,
                        (int) (taille_fenetre.width * 0.55),
                        taille_fenetre.height * 2 / 3,
                        this
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erreur image de fond: " + e.getMessage());
            }
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
            
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxlayout);

            Dimension size = new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * taille_h));
            setPreferredSize(size);
            setMaximumSize(size);

            JLabel logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo.png"));
            logo.setAlignmentX(CENTER_ALIGNMENT);
            add(logo);

            jt = new JLabel("C'est au tour du Joueur 1");
            jt.setAlignmentX(CENTER_ALIGNMENT);
            jt.setOpaque(false);
            jt.setFont(lilly_belle);
            jt.setForeground(Color.WHITE);
            jt.setBorder(new LineBorder(Color.orange));
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

            Image colonne = (jeu.getJoueur_en_cours() == JOUEUR1 ? colonne_bleu : colonne_rouge);
            // float meme_ratio = (float) getWidth()/1232*191; //sert à garder le meme ratio hauteur/largeur au changement de largeur de la fenetre

            g2d.drawImage(
                    colonne,
                    0,
                    0,
                    getWidth(), (int) (getHeight() * 0.25),
                    this
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

    /**
     * Affiche le menu paramètre.
     *
     * @param e
     * @see PanelParametres
     */
    public void actionBoutonParametres(ActionEvent e) {
        pp.setVisible(true);
        /*Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "parametres");*/
    }

    public void actionUndo(ActionEvent e) {
        jeu.undo();
        jg.repaint();
    }

    public void actionRedo(ActionEvent e) {
        jeu.redo();
        jg.repaint();
    }

    /**
     * Modifie le texte qui affiche quel joueur doit jouer.
     */
    @Override
    public void miseAjour() {
        String annonce_tour_joueur;
        if (jeu.estJeufini())
            annonce_tour_joueur = jeu.getJoueur_en_cours() == JOUEUR1 ? "Joueur 1 gagne" : "Joueur 2 gagne";
        else {
            annonce_tour_joueur = jeu.getJoueur_en_cours() == JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2";
        }
        jt.setText(annonce_tour_joueur);
        repaint();
    }
}
