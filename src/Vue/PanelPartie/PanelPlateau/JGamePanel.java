package Vue.PanelPartie.PanelPlateau;

import Listener.EcouteurDeMouvementDeSouris;
import Listener.EcouteurDeSouris;
import Modele.Jeu;
import Vue.PanelPartie.ActionEchap;
import Vue.Bouton;
import Vue.JeuGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static Utile.Constante.*;
import static Utile.Constante.PLATEAU_COLONNES;

/**
 * Crée un JPanel modifié qui génère deux zones de boutons de 20% de la taille de la fenêtre.
 * Génère la grille de jeu.
 *
 * @see JeuGraphique
 * @see Jeu
 */
public class JGamePanel extends JPanel {
    private final int taille_margin;
    private final float taille_h;
    Dimension taille_fenetre;

    /**
     * Constructeur pour JGamePanel. Rajoute des components au JPanel.
     */
    public JGamePanel(float _taille_h, PanelPlateau panel_plateau) {
        this.taille_fenetre = panel_plateau.getTailleFenetre();
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


        ActionEchap echap = new ActionEchap(panel_plateau);
        panel_plateau.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ECHAP_KEY);
        panel_plateau.getActionMap().put(ECHAP_KEY, echap);

        bParametres.addActionListener(echap);
        parametres.add(bParametres);


        panel_plateau.setJeu(new Jeu(panel_plateau, panel_plateau.getConfig()));
        panel_plateau.setJg(new JeuGraphique(panel_plateau.getJeu()));
        JeuGraphique jg = panel_plateau.getJg();
        Jeu jeu = panel_plateau.getJeu();
        jg.addMouseListener(new EcouteurDeSouris(jg, jeu, panel_plateau));
        jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg, panel_plateau));

        SidePanelRight side_panel = new SidePanelRight(size,panel_plateau);
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

        ajouterMarge(container);
        container.add(parametres);
        ajouterMarge(container);
        container.add(jg);
        ajouterMarge(container);
        container.add(side_panel);
        ajouterMarge(container);
        add(container);
    }

    /**
     * Crée un JPanel servant de marge.
     */
    private void ajouterMarge(JPanel c) {
        JPanel j = new JPanel();
        j.setOpaque(false);
        Dimension size = new Dimension(taille_margin, (int) (taille_fenetre.height * taille_h));
        j.setPreferredSize(size);
        j.setMaximumSize(size);
        c.add(j);
    }

}