package Vue.PanelPartie.PanelTutoriel;

import static Utile.Constante.*;

import Listener.EcouteurDeMouvementDeSouris;
import Listener.EcouteurDeSourisTuto;
import Utile.Constante;
import Modele.JeuTuto;
import Utile.*;
import Vue.Bouton;
import Vue.Fenetre;
import Vue.JeuGraphique;
import Vue.JeuGraphiqueTuto;
import Vue.PanelPartie.ConfigurationPartie;
import Vue.PanelPartie.PanelPartie;
import Vue.PanelPartie.PanelPlateau.TopPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Classe générant la fenêtre Tutoriel.
 */
public class PanelTutoriel extends PanelPartie {
    private final LecteurSon son_bouton;
    private JeuTuto jeu_tuto;
    private PanelJeu panel_jeu;
    private JeuGraphiqueTuto jg;
    private int num_etape;
    private Bouton suivant;
    private Bouton precedent;


    /**
     * Initialise la fenêtre Tutoriel et charge la police et les images en mémoire.
     *
     * @see PanelTutoriel#initialiserPanel()
     */
    public PanelTutoriel(Dimension _taille_fenetre) {

        super(_taille_fenetre,new Font(LILY_SCRIPT, Font.PLAIN, 40));
        is_finish_draw = false;
        son_bouton = new LecteurSon("menu_click.wav");

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/Lora-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : Un fichier de police est introuvable ou non valide.");
        }
        initialiserPanel();

        setCursor(EcouteurDeMouvementDeSouris.creerCurseurGenerique("defaut_gris", new Point(0, 0)));

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

        TopPanel tp = new TopPanel(0.20f,this);
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
            precedent.setEnabled(true);
            changerEtape();
        } else {
            suivant.setEnabled(false);
            this.num_etape = Constante.TEXTE_ETAPES.length - 1;
        }
    }

    public void actionBoutonPrecedent(ActionEvent e) {
        jg.getJeu_tuto().setClic_etape(num_etape,0);
        this.num_etape -= 1;
        if (this.num_etape >= 0) {
            suivant.setEnabled(true);
            changerEtape();
        } else{
            this.num_etape = 0;
            precedent.setEnabled(false);
        }
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    public void changerEtape() {
        panel_jeu.panel_gauche.getPanel_info().changerTexte(num_etape);
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

            jeu_tuto = new JeuTuto(PanelTutoriel.this);
            jg = new JeuGraphiqueTuto(jeu_tuto, num_etape, PanelTutoriel.this);
            jg.addMouseListener(new EcouteurDeSourisTuto(PanelTutoriel.this));

            Dimension dimension_panel_gauche = new Dimension((int)(taille_fenetre.width * 0.22f), (int)(taille_fenetre.height * taille_h * 1.05f));
            panel_gauche = new PanelGauche(dimension_panel_gauche, PanelTutoriel.this);
            definirTaille(panel_gauche, dimension_panel_gauche);

            // Calcul de la taille de la grille selon la taille de la fenêtre
            int taille_case = ((int) (taille_fenetre.height * (taille_h - 0.1f))) / PLATEAU_LIGNES;

            definirTaille(jg, new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));

            // place de la grille : taille_case * PLATEAU_COLONNE, taille // place des menus : taille_fenetre.width *0.4
            int taille = (int)(taille_fenetre.width * 0.6)-(taille_case * PLATEAU_COLONNES);
            taille_marge = taille / 4;

            int bouton_height = (int) (dimension_panel_gauche.height * 0.1);
            Dimension size_bouton = new Dimension((int) ((bouton_height/1.5) * RATIO_BOUTON_CLASSIQUE), (int)(bouton_height/1.5));

            JPanel panel_parametres = new JPanel();
            panel_parametres.setOpaque(false);
            panel_parametres.setPreferredSize(new Dimension(dimension_panel_gauche.width, size_bouton.height));
            panel_parametres.setMaximumSize(new Dimension(dimension_panel_gauche.width, size_bouton.height));

            Bouton bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter_partie.png",CHEMIN_RESSOURCE + "/bouton/quitter_partie_hover.png",size_bouton,PanelTutoriel.this::actionBoutonRetourMenu);
            suivant = new Bouton(CHEMIN_RESSOURCE + "/bouton/suivant.png", CHEMIN_RESSOURCE + "/bouton/suivant_hover.png",size_bouton, PanelTutoriel.this::actionBoutonSuivant);
            precedent = new Bouton(CHEMIN_RESSOURCE + "/bouton/precedent.png", CHEMIN_RESSOURCE + "/bouton/precedent_hover.png", size_bouton, PanelTutoriel.this::actionBoutonPrecedent);

            panel_parametres.add(bRetour);
            precedent.setEnabled(false);
            JPanel panel_bouton = new JPanel();
            panel_bouton.setOpaque(false);
            panel_bouton.add(precedent);
            panel_bouton.add(Box.createRigidArea(new Dimension(size_bouton.width/2, size_bouton.height/2)));
            panel_bouton.add(suivant);
            definirTaille(panel_bouton, new Dimension(size_bouton.width*4, size_bouton.height));
            panel_bouton.setAlignmentX(CENTER_ALIGNMENT);

            JPanel jp_jg = new JPanel();
            jp_jg.add(jg);

            Dimension taille_pannel = new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES);

            jp_jg.add(panel_bouton);
            jp_jg.add(Box.createRigidArea(taille_pannel));
            definirTaille(jp_jg, taille_pannel);
            jp_jg.setOpaque(false);

            add(panel_gauche, BorderLayout.WEST);
            add(jp_jg, BorderLayout.CENTER);
            add(panel_parametres, BorderLayout.EAST);

        }

    }

    public void definirTaille(JComponent panel, Dimension taille_panel){
        panel.setMinimumSize(taille_panel);
        panel.setMaximumSize(taille_panel);
        panel.setPreferredSize(taille_panel);
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
        panel_jeu.panel_gauche.getPanel_info().changerTexte(num_etape);

    }

    public JeuGraphiqueTuto getJg() {
        return jg;
    }

    public int getNumEtape() {
        return num_etape;
    }

    public void setNumEtape(int num_etape) {
        this.num_etape = num_etape;
    }

}
