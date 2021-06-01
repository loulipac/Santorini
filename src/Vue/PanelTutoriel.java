package Vue;

import static Modele.Constante.*;

import Modele.JeuTuto;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe générant la fenêtre Tutoriel.
 */
public class PanelTutoriel extends JPanel implements Observer {

    JeuTuto jeu;
    JeuGraphique jg;
    Font lilly_belle;
    JLabel jt;
    Dimension taille_fenetre;
    Image colonne_rouge;
    Image colonne_bleu;
    Image arriere_plan;
    Image colonne_fin;
    ParametrePanel pp;
    PanelJeu jgame;
    int num_etape;

    /**
     * Initialise la fenêtre Tutoriel et charge la police et les images en mémoire.
     *
     * @see PanelTutoriel#initialiserPanel()
     */
    public PanelTutoriel(Dimension _taille_fenetre) {
        this.taille_fenetre = _taille_fenetre;
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

    /**
     * Ajoute tous les composants au panel.
     *
     * @see TopPanel
     * @see PanelJeu
     */
    public void initialiserPanel() {
        this.num_etape = 0;
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
        jgame = new PanelJeu(0.80f);
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
            pp.setVisible(!pp.isVisible());
        }
    }

    public void actionBoutonSuivant(ActionEvent e) {
        this.num_etape+=1;
        if(this.num_etape < jgame.panel_gauche.TEXTE_ETAPES.length) {
            changerEtape();
        }
        else {
            this.num_etape = jgame.panel_gauche.TEXTE_ETAPES.length;
        }
    }

    public void actionBoutonPrecedent(ActionEvent e) {
        this.num_etape-=1;
        if(this.num_etape >= 0) {
            changerEtape();
        }
        else this.num_etape = 0;
    }

    public void changerEtape() {
        jgame.panel_gauche.panel_info.changerTexte(num_etape);
        jeu.chargerEtape(num_etape);
        jg.repaint();
    }

    /**
     * Crée un JPanel modifié qui génère deux zones de boutons de 20% de la taille de la fenêtre.
     * Génère la grille de jeu.
     *
     * @see JeuGraphique
     * @see JeuTuto
     */
    public class PanelJeu extends JPanel {
        int taille_margin;
        float taille_h;
        PanelGauche panel_gauche;

        /**
         * Constructeur pour PanelJeu. Rajoute des components au JPanel.
         */
        public PanelJeu(float _taille_h) {
            this.taille_h = _taille_h - 0.05f;
            setLayout(new GridBagLayout());
            setOpaque(false);

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
            container.setOpaque(false);
            setPreferredSize(new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * taille_h)));

            Dimension size = new Dimension((int) (taille_fenetre.width * 0.2), (int) (taille_fenetre.height * taille_h));

            jeu = new JeuTuto(PanelTutoriel.this);
            jg = new JeuGraphique(jeu);
            jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg, null));

            panel_gauche = new PanelGauche(size);
            panel_gauche.setMaximumSize(size);
            panel_gauche.setPreferredSize(size);

            // Calcul de la taille de la grille selon la taille de la fenêtre

            int taille_case = ((int) (taille_fenetre.height * (taille_h - 0.1f))) / PLATEAU_LIGNES;

            jg.setPreferredSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));
            jg.setMaximumSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));
            jg.setMinimumSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));

            int taille = taille_fenetre.width;

            // place de la grille
            taille -= taille_case * PLATEAU_COLONNES;

            // place des menus
            taille -= taille_fenetre.width * 0.4;

            taille_margin = taille / 4;


            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setPreferredSize(size);
            panel.setMaximumSize(size);
            panel.setMinimumSize(size);

            addMargin(container);
            container.add(panel_gauche);
            addMargin(container);
            container.add(jg);
            addMargin(container);
            container.add(panel);
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

    private class PanelGauche extends JPanel {
        Dimension size;
        PanelInfo panel_info;

        private final String[] TEXTE_ETAPES = {
                """
                Bienvenue dans le tutoriel de Santorini !
                C’est ici que tu vas apprendre à devenir un pro de ce jeu.
                Mais avant de devenir un pro commençons par voir les bases.
                On y va ?""",
                """
                Commençons à jouer !
                Au début du jeu, chaque joueur doit placer ses deux pions où il veut sur le plateau.""",
                "Tu es le joueur bleu, positionne par exemple tes 2 personnages sur la grille sur les cases indiquées.",
                """
                C’est au tour de ton adversaire, le joueur rouge, de placer ses deux bâtisseurs !
                Voyons où il les place.""",
                """
                A chaque tour, tu dois bouger un de tes pions, pour cela :
                Clic sur le personnage que tu veux déplacer, puis sélectionne la case accessible (indiqué par les traces de pas) sur laquelle tu veux aller.
                """,
                "C’est à toi de jouer, clic sur le personnage. ",
                "Va sur la case en haut à gauche du personnage pour le déplacer.",
                """
                Super ! Tu dois ensuite construire un étage d’un bâtiment sur une des cases autour du personnage.
                Seul le personnage déplacé précédemment peut construire un étage durant ce tour.
                """,
                "A toi de jouer, construis le rez-de-chaussée sur la case à droite du personnage.",
                """
                Génial! Tu as effectué tes deux actions, c’est donc la fin de ton tour.
                C’est maintenant à ton adversaire de jouer.""",
                "C’est de nouveau ton tour, essaie de monter sur le premier étage que tu as construit au tour d’avant",
                """
                Trop bien !
                N’oublie pas, tu ne peux monter que d’un étage à la fois et descendre de n’importe quel étage.""",
                """
                Tu peux voir qu’il n'est pas possible de construire sur ta propre case.
                Construit un étage sur la case de gauche.""",
                """
                Génial !
                Maintenant avançons un peu dans la partie.""",
                """
                Pour bloquer ton adversaire,
                tu peux construire un dôme sur une tour à 3 étages pour l’empêcher de monter dessus.
                Mais méfie toi, car toi non plus tu ne peux plus monter dessus !
                """,
                """
                Attention ! Ton adversaire va gagner !
                Pour éviter cela, place un dôme au-dessus de la tour. Il ne gagnera pas ce tour là !
                """,
                "C’est maintenant au tour de ton adversaire de jouer !",
                """
                Tu as la possibilité de gagner, profites-en !
                Déplace ton pion sur la tour à 3 étage.
                """,
                """
                Félicitations, tu as gagné(e) !
                Le tutoriel est fini, tu es maintenant un pro de ce jeu.
                Amuse toi bien !
                """
        };

        private class PanelInfo extends JPanel {
            Dimension taille_personnage;
            Dimension taille_parchemin;
            Dimension pos_parchemin;
            Dimension pos_personnage;
            Dimension panel_texte_taille;
            Dimension size_bouton;
            Dimension texte_bulle_taille;
            JTextArea texte_bulle;

            public PanelInfo(Dimension size) {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                // Polices
                lilly_belle = new Font("Lily Script One", Font.PLAIN, 20);

                // Dimensions
                int bouton_height = (int) (size.height * 0.1);

                taille_personnage = new Dimension(size.height / 6, size.height / 6);
                taille_parchemin = new Dimension(size.width, size.height * 2 / 3 - taille_personnage.height / 2);

                pos_parchemin = new Dimension(0, 0);
                pos_personnage = new Dimension(0, pos_parchemin.height + taille_parchemin.height - taille_personnage.height * 3 / 4);
                panel_texte_taille = new Dimension(size.width*2/3, taille_parchemin.height);
                size_bouton = new Dimension((int) (bouton_height * RATIO_BOUTON_PETIT), bouton_height);
                texte_bulle_taille = new Dimension(panel_texte_taille.width, panel_texte_taille.height - 2 * size_bouton.height);

                setOpaque(false);
                setMaximumSize(size);
                JPanel panel_texte = new JPanel();
                panel_texte.setOpaque(false);
                panel_texte.setMaximumSize(panel_texte_taille);
                panel_texte.setMinimumSize(panel_texte_taille);
                panel_texte.setPreferredSize(panel_texte_taille);

                texte_bulle = new JTextArea(TEXTE_ETAPES[0]);
                texte_bulle.setOpaque(false);
                texte_bulle.setEditable(false);

                texte_bulle.setFont(lilly_belle);
                texte_bulle.setForeground(new Color(82, 60, 43));

                texte_bulle.setMaximumSize(texte_bulle_taille);
                texte_bulle.setMinimumSize(texte_bulle_taille);
                texte_bulle.setPreferredSize(texte_bulle_taille);

                texte_bulle.setLineWrap(true);
                texte_bulle.setWrapStyleWord(true);

                panel_texte.add(texte_bulle);

                Bouton suivant = new Bouton(CHEMIN_RESSOURCE + "/bouton/avant.png", CHEMIN_RESSOURCE + "/bouton/avant_hover.png",
                        size_bouton, PanelTutoriel.this::actionBoutonSuivant);
                Bouton precedent = new Bouton(CHEMIN_RESSOURCE + "/bouton/arriere.png", CHEMIN_RESSOURCE + "/bouton/arriere_hover.png",
                        size_bouton, PanelTutoriel.this::actionBoutonPrecedent);

                JPanel panel_bouton = new JPanel();

                panel_bouton.setOpaque(false);
                panel_bouton.add(precedent);
                panel_bouton.add(suivant);
                panel_bouton.setPreferredSize(new Dimension(panel_texte_taille.width,size_bouton.height));
                panel_bouton.setMaximumSize(new Dimension(panel_texte_taille.width,size_bouton.height));
                panel_bouton.setMinimumSize(new Dimension(panel_texte_taille.width,size_bouton.height));

                add(Box.createRigidArea(new Dimension(size.width, 10)));

                panel_texte.add(panel_bouton);

                add(panel_texte);

                changerTexte(num_etape);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                try {
                    BufferedImage bg_parchemin = ImageIO.read(new File(CHEMIN_RESSOURCE + "/assets_recurrents/parchemin.png"));
                    BufferedImage personnage = ImageIO.read(new File(CHEMIN_RESSOURCE + "/carte_dieu/aphrodite.png"));

                    g2d.drawImage(
                            bg_parchemin,
                            pos_parchemin.width,
                            pos_parchemin.height,
                            taille_parchemin.width,
                            taille_parchemin.height,
                            this
                    );
                    g2d.drawImage(
                            personnage,
                            pos_personnage.width,
                            pos_personnage.height,
                            taille_personnage.width,
                            taille_personnage.height,
                            this
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur image de fond: " + e.getMessage());
                }
            }

            public void changerTexte(int num_etape) {
                texte_bulle.setText(TEXTE_ETAPES[num_etape]);
            }
        }

        public PanelGauche(Dimension size) {
            this.size = size;
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            int bouton_height = (int) (size.height * 0.1);
            Dimension size_bouton = new Dimension((int) (bouton_height * RATIO_BOUTON_PETIT), bouton_height);

            JPanel parametres = new JPanel();
            parametres.setOpaque(false);
            parametres.setPreferredSize(new Dimension(size.width, size_bouton.height));
            parametres.setMaximumSize(new Dimension(size.width, size_bouton.height));

            Bouton bParametres = new Bouton(
                    CHEMIN_RESSOURCE + "/bouton/parametres.png",
                    CHEMIN_RESSOURCE + "/bouton/parametres_hover.png",
                    size_bouton
            );
            ActionEchap echap = new ActionEchap();
            PanelTutoriel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ECHAP_KEY);
            PanelTutoriel.this.getActionMap().put(ECHAP_KEY, echap);

            bParametres.addActionListener(echap);
            parametres.add(bParametres);

            panel_info = new PanelInfo(size);
            add(parametres);
            add(Box.createRigidArea(new Dimension(size.width, size.height/20)));
            add(panel_info);
        }
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
            lilly_belle = new Font("Lily Script One", Font.PLAIN, 40);
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

            bQuitter.addActionListener(this::actionBoutonAbandonner);
            bReprendre.addActionListener(echap);
            bSauvegarder.addActionListener(this::actionBoutonSauvergarder);
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

        public void actionBoutonAbandonner(ActionEvent e) {
            Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
            f.displayPanel("menu");
        }

        public void actionBoutonSauvergarder(ActionEvent e) {
            jeu.sauvegarder();
            pp.setVisible(false);
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
                colonne = (jeu.getJoueur_en_cours().getNum_joueur() == JOUEUR1 ? colonne_bleu : colonne_rouge);
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
            jt.setText(jeu.getJoueur_en_cours().getNum_joueur() == JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2");
        }
        repaint();
    }
}
