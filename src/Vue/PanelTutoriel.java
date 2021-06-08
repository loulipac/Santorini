package Vue;

import static Utile.Constante.*;

import Listener.EcouteurDeMouvementDeSouris;
import Listener.EcouteurDeSourisTuto;
import Utile.Constante;
import Modele.JeuTuto;
import Patterns.Observateur;
import Utile.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe générant la fenêtre Tutoriel.
 */
public class PanelTutoriel extends Panels implements Observateur {
    private final LecteurSon son_bouton;
    private JeuTuto jeu_tuto;
    private JeuGraphiqueTuto jg;
    private Font lilly_belle;
    private JLabel jt;
    private final Dimension taille_fenetre;
    private PanelJeu panel_jeu;
    private int num_etape;

    /**
     * Initialise la fenêtre Tutoriel et charge la police et les images en mémoire.
     *
     * @see PanelTutoriel#initialiserPanel()
     */
    public PanelTutoriel(Dimension _taille_fenetre) {

        is_finish_draw = false;

        this.taille_fenetre = _taille_fenetre;
        son_bouton = new LecteurSon("menu_click.wav");

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/Lora-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : Un fichier de police est introuvable ou non valide.");
        }

        lilly_belle = new Font(LILY_SCRIPT, Font.PLAIN, 40);
        initialiserPanel();

        setCursor(EcouteurDeMouvementDeSouris.creerCurseurGenerique("sword", new Point(0, 0)));

        jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu_tuto, jg, PanelTutoriel.this));
        is_finish_draw = true;
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

        // panel de base avec le jeu_tuto
        JPanel game = new JPanel();
        game.setOpaque(false);
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        game.setMaximumSize(taille_fenetre);

        TopPanel tp = new TopPanel(0.20f);
        panel_jeu = new PanelJeu(0.80f);
        game.add(tp);
        game.add(panel_jeu);
        main_panel.add(game, JLayeredPane.DEFAULT_LAYER);
        add(main_panel);
    }

    public void actionBoutonSuivant(ActionEvent e) {
        jg.getJeu_tuto().setClic_etape(num_etape,0);
        this.num_etape += 1;
        if (this.num_etape < Constante.TEXTE_ETAPES.length) {
            changerEtape();
        } else {
            this.num_etape = Constante.TEXTE_ETAPES.length;
        }
    }

    public void actionBoutonPrecedent(ActionEvent e) {
        jg.getJeu_tuto().setClic_etape(num_etape,0);
        this.num_etape -= 1;
        if (this.num_etape >= 0) {
            changerEtape();
        } else this.num_etape = 0;
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    public void changerEtape() {
        panel_jeu.panel_gauche.panel_info.changerTexte(num_etape);
        jg.chargerEtape(num_etape);
        jt.setText("Tutoriel : Etape " + (num_etape + 1) + "/" + TEXTE_ETAPES.length);
    }

    /**
     * Crée un JPanel modifié qui génère deux zones de boutons de 20% de la taille de la fenêtre.
     * Génère la grille de jeu_tuto.
     *
     * @see JeuGraphique
     * @see JeuTuto
     */
    public class PanelJeu extends JPanel {
        int taille_marge;
        float taille_h;
        PanelGauche panel_gauche;

        /**
         * Constructeur pour PanelJeu. Rajoute des components au JPanel.
         */
        public PanelJeu(float _taille_h) {
            this.taille_h = _taille_h - 0.05f;
            setLayout(new BorderLayout());
            setOpaque(false);
            setPreferredSize(new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * taille_h)));

            Dimension size = new Dimension((int) (taille_fenetre.width * 0.2), (int) (taille_fenetre.height * taille_h));

            jeu_tuto = new JeuTuto(PanelTutoriel.this);
            jg = new JeuGraphiqueTuto(jeu_tuto, num_etape, PanelTutoriel.this);
            jg.addMouseListener(new EcouteurDeSourisTuto(PanelTutoriel.this));

            size = new Dimension((int)(size.width * 1.1f), (int)(size.height * 1.05f));
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

            taille_marge = taille / 4;


            int bouton_height = (int) (size.height * 0.1);
            Dimension size_bouton = new Dimension((int) ((bouton_height/1.5) * RATIO_BOUTON_CLASSIQUE), (int)(bouton_height/1.5));

            JPanel panel_parametres = new JPanel();
            panel_parametres.setOpaque(false);
            panel_parametres.setPreferredSize(new Dimension(size.width, size_bouton.height));
            panel_parametres.setMaximumSize(new Dimension(size.width, size_bouton.height));

            Bouton bRetour = new Bouton(
                    CHEMIN_RESSOURCE + "/bouton/quitter_partie.png",
                    CHEMIN_RESSOURCE + "/bouton/quitter_partie_hover.png",
                    size_bouton,
                    PanelTutoriel.this::actionBoutonRetourMenu
            );

            panel_parametres.add(bRetour);

            JPanel jp_jg = new JPanel();

            Bouton suivant = new Bouton(CHEMIN_RESSOURCE + "/bouton/suivant.png", CHEMIN_RESSOURCE + "/bouton/suivant_hover.png",
                    size_bouton, PanelTutoriel.this::actionBoutonSuivant);
            Bouton precedent = new Bouton(CHEMIN_RESSOURCE + "/bouton/precedent.png", CHEMIN_RESSOURCE + "/bouton/precedent_hover.png",
                    size_bouton, PanelTutoriel.this::actionBoutonPrecedent);

            JPanel panel_bouton = new JPanel();

            panel_bouton.setOpaque(false);
            panel_bouton.add(precedent);
           panel_bouton.add(Box.createRigidArea(new Dimension(size_bouton.width/2, size_bouton.height/2)));
            panel_bouton.add(suivant);
            panel_bouton.setPreferredSize(new Dimension(size_bouton.width*4, size_bouton.height));
            panel_bouton.setMaximumSize(new Dimension(size_bouton.width*4, size_bouton.height));
            panel_bouton.setMinimumSize(new Dimension(size_bouton.width*4, size_bouton.height));
            panel_bouton.setAlignmentX(CENTER_ALIGNMENT);


            jp_jg.add(jg);

            jp_jg.add(panel_bouton);
            jp_jg.add(Box.createRigidArea(new Dimension(size_bouton.width*3, size_bouton.height)));
            jp_jg.setMinimumSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));
            jp_jg.setMaximumSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));
            jp_jg.setPreferredSize(new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));
            jp_jg.setOpaque(false);

            add(panel_gauche, BorderLayout.WEST);
            add(jp_jg, BorderLayout.CENTER);
            add(panel_parametres, BorderLayout.EAST);

        }

    }

    private class PanelGauche extends JPanel {
        Dimension size;
        PanelInfo panel_info;

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
                lilly_belle = new Font(LILY_SCRIPT, Font.PLAIN, 20);

                // Dimensions

                int bouton_height = (int) (size.height * 0.1);


                taille_personnage = new Dimension(size.height / 6, size.height / 6);
                taille_parchemin = new Dimension(size.width, size.height * 2 / 3 - taille_personnage.height / 2);

                pos_parchemin = new Dimension(0, (int)(size.height*0.2f));
                pos_personnage = new Dimension(0, pos_parchemin.height + taille_parchemin.height - taille_personnage.height * 3 / 4);
                panel_texte_taille = new Dimension(size.width * 2 / 3, taille_parchemin.height);
                size_bouton = new Dimension((int) (bouton_height * RATIO_BOUTON_PETIT), bouton_height);
                texte_bulle_taille = new Dimension(panel_texte_taille.width, panel_texte_taille.height - 2 * size_bouton.height);

                setOpaque(false);
                setMaximumSize(size);

                JPanel panel_texte = new JPanel();
                panel_texte.setOpaque(false);
                panel_texte.setMaximumSize(panel_texte_taille);
                panel_texte.setMinimumSize(panel_texte_taille);
                panel_texte.setPreferredSize(panel_texte_taille);
                panel_texte.setAlignmentY(CENTER_ALIGNMENT);
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

                add(Box.createRigidArea(new Dimension(size.width, (int)(pos_parchemin.height*1.2f))));

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
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            panel_info = new PanelInfo(size);
            add(panel_info);
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

            jt = new JLabel("Tutoriel : Etape " + (num_etape + 1) + "/" + TEXTE_ETAPES.length);
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
        ConfigurationPartie emptyConfig = new ConfigurationPartie(0, 0);
        emptyConfig.setJoueur1Bleu(true);
        Utile.dessineDecorationPlateau(g, getSize(), this, false, emptyConfig, JOUEUR1);
    }

    /**
     * Modifie le numéro d'étape et met à jour le jeu et le texte
     */
    @Override
    public void miseAjour() {
        num_etape++;
        changerEtape();
        jg.chargerEtape(num_etape);
        panel_jeu.panel_gauche.panel_info.changerTexte(num_etape);

    }

    public JeuGraphiqueTuto getJg() {
        return jg;
    }

    public int getNum_etape() {
        return num_etape;
    }

    public void setNum_etape(int num_etape) {
        this.num_etape = num_etape;
    }
}
