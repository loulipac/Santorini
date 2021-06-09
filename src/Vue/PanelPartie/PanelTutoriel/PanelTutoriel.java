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
import Utile.ConfigurationPartie;
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
            e.printStackTrace();
        }
        initialiserPanel();

        setCursor(EcouteurDeMouvementDeSouris.creerCurseurGenerique("defaut_gris", new Point(0, 0)));

        jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu_tuto, jg));
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
        panel_jeu = new PanelJeu(0.80f, this);
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
        panel_jeu.getPanel_gauche().getPanel_info().changerTexte(num_etape);
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
    public static void definirTaille(JComponent panel, Dimension taille_panel){
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
        panel_jeu.getPanel_gauche().getPanel_info().changerTexte(num_etape);

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

    public void setJeu_tuto(JeuTuto jeu_tuto) {
        this.jeu_tuto = jeu_tuto;
    }

    public JeuTuto getJeuTuto() {
        return jeu_tuto;
    }

    public void setJg(JeuGraphiqueTuto jg) {
        this.jg = jg;
    }

    public void setSuivant(Bouton suivant) {
        this.suivant = suivant;
    }

    public void setPrecedent(Bouton precedent) {
        this.precedent = precedent;
    }

    public PanelJeu getPanelJeu() {
        return panel_jeu;
    }

    public Bouton getSuivant() {
        return suivant;
    }

    public Bouton getPrecedent() {
        return precedent;
    }

}
