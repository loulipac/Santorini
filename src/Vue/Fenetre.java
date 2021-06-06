package Vue;

import static Modele.Constante.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Crée une fenêtre pour le menu principal du jeu.
 * Cette fenêtre permet de démarrer le jeu, de lire les règles, de suivre un tutoriel.
 */
public class Fenetre extends JFrame {

    CardLayout pileCarte;
    JPanel panelPrincipal;
    PanelMenu menu;
    PanelOptions options;
    PanelRegles regles;
    PanelTutoriel tutoriel;
    PanelMultijoueur multi;
    LecteurSon musique;
    JPanel shown;

    public Fenetre() {
        setTitle("Santorini");
        last_size = DEFAULT_FENETRE_TAILLE;
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
        multi = new PanelMultijoueur(this);

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

        setVisible(true);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if(!last_size.equals(getSize())) {
                    should_resize = true;
                } else {
                    should_resize = false;
                }
            }
        });

        // musique.joueSon(true);
    }

    Dimension last_size;
    public boolean should_resize = false;

    public void setPanel(JPanel p) {
        JPanel tmp_shown = shown;
        shown = p;
        panelPrincipal.add(p);
        pileCarte.show(panelPrincipal, "");
        if(tmp_shown != null) panelPrincipal.remove(tmp_shown);
    }

    public void displayPanel(String name) {
        pileCarte.show(panelPrincipal, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Fenetre::new);
    }

}
