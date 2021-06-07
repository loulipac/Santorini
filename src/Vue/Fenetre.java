package Vue;

import Listener.EcouteurDeMouvementDeSouris;
import Utile.*;

import static Utile.Constante.*;

import javax.swing.*;
import java.awt.*;

/**
 * Crée une fenêtre pour le menu principal du jeu.
 * Cette fenêtre permet de démarrer le jeu, de lire les règles, de suivre un tutoriel.
 */
public class Fenetre extends JFrame {

    CardLayout pileCarte;
    JPanel panelPrincipal,shown;
    PanelMenu menu;
    PanelOptions options;
    PanelRegles regles;
    PanelTutoriel tutoriel;
    PanelMultijoueur multi;
    LecteurSon musique;

    /**
     * Initialise les JPanels ne nécessitant pas d'être créer selon des options.
     */
    public Fenetre() {
        setTitle("Santorini");
        setMinimumSize(DEFAULT_FENETRE_TAILLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pileCarte = new CardLayout();
        panelPrincipal = new JPanel(pileCarte);

        musique = new LecteurSon("musiqueBGtest.wav");
        menu = new PanelMenu(getSize());
        options = new PanelOptions(getSize());
        regles = new PanelRegles(getSize());
        tutoriel = new PanelTutoriel(getSize());
        multi = new PanelMultijoueur();

        panelPrincipal.add(menu, "menu");
        panelPrincipal.add(options, "options");
        panelPrincipal.add(regles, "regles");
        panelPrincipal.add(tutoriel, "tutoriel");
        panelPrincipal.add(multi, "multi");

        add(panelPrincipal);
        pileCarte.show(panelPrincipal, "menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Change le curseur de la fenêtre
        setCursor(EcouteurDeMouvementDeSouris.creerCurseurGenerique("sword", new Point(0, 0)));
        Utile.chargerFontLily();

        setVisible(true);

        // musique.joueSon(true);
    }

    /**
     * Affiche un JPanel passé en paramètre sur la fenêtre.
     * @param p le JPanel à afficher
     */
    public void setPanel(JPanel p) {
        JPanel tmp_shown = shown;
        shown = p;
        panelPrincipal.add(p);
        pileCarte.show(panelPrincipal, "");
        if(tmp_shown != null) panelPrincipal.remove(tmp_shown);
    }

    /**
     * Affiche un JPanel déjà présent dans le CardLayout.
     * @param name nom du JPanel
     */
    public void displayPanel(String name) {
        pileCarte.show(panelPrincipal, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Fenetre::new);
    }

}
