package Vue.PanelPartie.PanelPlateau;

import static Utile.Constante.*;
import Utile.ConfigurationPartie;
import Reseau.Reseau;
import Utile.Utile;
import Vue.Bouton;
import Vue.Fenetre;
import Vue.JeuGraphique;
import Vue.LobbyPanel;
import Vue.PanelPartie.PanelPartie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

/**
 * Classe générant la fenêtre de jeu.
 */
public class PanelPlateau extends PanelPartie {

    private Reseau netUser;
    private final ConfigurationPartie config;
    private ParametrePanel pp;
    private VictoirePanel victoire_panel;
    private Bouton on_off_ia;
    private JeuGraphique jg;

    /**
     * Initialise la fenêtre de jeu et charge la police et les images en mémoire.
     *
     * @param _taille_fenetre taille de la fenêtre
     * @param config          classe de configuration de la partie
     */
    public PanelPlateau(Dimension _taille_fenetre, ConfigurationPartie config) {
        super(_taille_fenetre,new Font(LILY_SCRIPT, Font.PLAIN, 40));
        this.config = config;
        initialiserPanel();
    }

    /**
     * Constructeur de Vue.PanelPartie.PanelPlateau.PanelPlateau chargeant une partie déjà existante.
     *
     * @param _taille_fenetre taille de la fenêtre
     * @param lecteur        nom du fichier à charger
     */
    public PanelPlateau(Dimension _taille_fenetre, Scanner lecteur) {
        super(_taille_fenetre,new Font(LILY_SCRIPT, Font.PLAIN, 40));
        String[] param = lecteur.nextLine().split(" ");
        int ia1_mode = Integer.parseInt(param[0]);
        int ia2_mode = Integer.parseInt(param[1]);
        int index_start = Integer.parseInt(param[2]);
        boolean j1_blue = Boolean.parseBoolean(param[3]);

        this.config = new ConfigurationPartie(ia1_mode, ia2_mode);
        this.config.setIndexJoueurCommence(index_start);
        this.config.setJoueur1Bleu(j1_blue);

        initialiserPanel();

        jeu.charger(lecteur);
    }

    /**
     * Constructeur de Vue.PanelPartie.PanelPlateau.PanelPlateau prenant en paramètre un netUser, donc soit un client ou un serveur.
     *
     * @param _taille_fenetre taille de la fenêtre
     * @param netUser         client ou serveur
     */
    public PanelPlateau(Dimension _taille_fenetre, Reseau netUser) {
        this(_taille_fenetre, new ConfigurationPartie(0, 0));
        this.netUser = netUser;
        jeu.setNetUser(netUser);
        if (netUser.getNumJoueur() == JOUEUR1) jt.setText(AU_TOUR_DE + netUser.getNomJoueur());
        else jt.setText(AU_TOUR_DE + netUser.getNomAdversaire());
    }

    /**
     * Ajoute tous les composants au panel.
     *
     * @see TopPanel
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

        TopPanel tp = new TopPanel(0.20f,this);
        JGamePanel jgame = new JGamePanel(0.80f,this);
        game.add(tp);
        game.add(jgame);

        pp = new ParametrePanel(this);
        pp.setVisible(false);

        victoire_panel = new VictoirePanel(this);
        victoire_panel.setVisible(false);

        main_panel.add(game, JLayeredPane.DEFAULT_LAYER);
        main_panel.add(pp, JLayeredPane.POPUP_LAYER);
        main_panel.add(victoire_panel, JLayeredPane.POPUP_LAYER);
        add(main_panel);
    }

    public boolean isParametreVisible() {
        return pp.isVisible();
    }

    /**
     * Action d'un bouton pour recréer une nouvelle partie. Ramène au lobby dans le cas d'une partie en réseau.
     */
    public void actionBoutonNouvelle(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        if (netUser != null) {
            f.setPanel(new LobbyPanel(netUser));
        } else {
            f.setPanel(new PanelPlateau(taille_fenetre, config));
        }
    }

    @Override
    public JLabel getJt() {
        return jt;
    }

    @Override
    public void setJt(JLabel jt) {
        this.jt = jt;
    }

    /**
     * Action d'un bouton pour revenir au menu. Déconnecte l'utilisateur du réseau.
     */
    public void actionQuitter(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        jeu.desactiverIA();
        if (netUser != null) {
            netUser.deconnexion();
        }
        f.displayPanel("menu");
    }

    /**
     * Action d'un bouton pour sauvegarder dans un fichier la partie en cours, ferme les paramètres.
     */
    public void actionBoutonSauvergarder(ActionEvent e) {
        jeu.sauvegarder();
        pp.setVisible(false);
    }

    /**
     * Change le statut de l'IA, à savoir si elle doit être en pause ou en marche. Change aussi le bouton pause/play.
     */
    public void switchOnOffIA(ActionEvent e) {
        jeu.iaSwitch();
        if (jeu.getIaStatut()) {
            on_off_ia.changeImage(CHEMIN_RESSOURCE + "/bouton/running.png", CHEMIN_RESSOURCE + "/bouton/running_hover.png");
        } else {
            on_off_ia.changeImage(CHEMIN_RESSOURCE + "/bouton/stop.png", CHEMIN_RESSOURCE + "/bouton/stop_hover.png");
        }
    }

    /**
     * Affiche le panel de victoire avec les textes correspondant au gagnant
     */
    private void changeVictory() {
        String nom_joueur;
        if (netUser != null) {
            if (netUser.getNumJoueur() == jeu.getJoueurEnCours().getNum_joueur()) nom_joueur = netUser.getNomJoueur();
            else nom_joueur = netUser.getNomAdversaire();
        } else {
            nom_joueur = jeu.getGagnant().getNum_joueur() == JOUEUR1 ? "Joueur 1" : "Joueur 2";
        }
        jt.setText(nom_joueur + " gagne");

        victoire_panel.setVisible(true);
        victoire_panel.changeTexte(victoire_panel.getTitre_victoire(), "Victoire de " + nom_joueur);
        victoire_panel.changeTexte(victoire_panel.getNb_tours(), jeu.getNbTours() + " tours passés");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utile.dessineDecorationPlateau(g, getSize(), this, jeu.estJeufini(), config, jeu.getJoueurEnCours().getNum_joueur());
    }
    /**
     * Modifie le texte qui affiche quel joueur doit jouer.
     */
    @Override
    public void miseAjour() {
        if (jeu.estJeufini()) {
            changeVictory();
        } else {
            if (netUser != null) {
                if (netUser.getNumJoueur() == jeu.getJoueurEnCours().getNum_joueur())
                    jt.setText("C'est au tour de " + netUser.getNomJoueur());
                else jt.setText("C'est au tour de " + netUser.getNomAdversaire());
            } else {
                jt.setText(jeu.getJoueurEnCours().getNum_joueur() == JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2");
            }
        }
        repaint();
    }

    public ConfigurationPartie getConfig() {
        return config;
    }

    public Bouton getOn_off_ia() {
        return on_off_ia;
    }

    public void setOn_off_ia(Bouton on_off_ia) {
        this.on_off_ia = on_off_ia;
    }

    public ParametrePanel getPp() {
        return pp;
    }

    public VictoirePanel getVictoire_panel() {
        return victoire_panel;
    }

    public void setJg(JeuGraphique jg) {
        this.jg = jg;
    }
    public JeuGraphique getJg() {
        return this.jg;
    }
}
