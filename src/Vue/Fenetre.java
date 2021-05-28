package Vue;

import static Modele.Constante.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Crée une fenêtre pour le menu principal du jeu.
 * Cette fenêtre permet de démarrer le jeu, de lire les règles, de suivre un tutoriel.
 * @TODO : renommer les instances de @PanelMenu et @PanelOptions, clean code la fonction @Fenetre.
 */
public class Fenetre extends JFrame {

    CardLayout pileCarte;
    JPanel panelPrincipal;
    PanelMenu menu;
    PanelOptions options;
    PanelRegles regles;
    PanelPlateau plateau;
    PanelTutoriel tutoriel;
    LecteurSon musique;

    public Fenetre() {
        setTitle("Santorini");
        setMinimumSize(DEFAULT_FENETRE_TAILLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pileCarte = new CardLayout();
        panelPrincipal = new JPanel(pileCarte);
        menu = new PanelMenu(getSize().width, getSize().height, this);
        options = new PanelOptions(getSize().width, getSize().height);
        regles = new PanelRegles(getSize().width, getSize().height);
        tutoriel = new PanelTutoriel(getSize(), 0, 0);
        //plateau = new PanelPlateau(getSize().width, getSize().height);
        musique = new LecteurSon("musiqueBGtest.wav");

        panelPrincipal.add(menu, "menu");
        panelPrincipal.add(options, "options");
        panelPrincipal.add(regles, "regles");
        panelPrincipal.add(tutoriel, "tutoriel");
        //panelPrincipal.add(plateau, "plateau");

        // Pour borderless window
        //setUndecorated(true);

        add(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Change le curseur de la fenêtre
        setCursor(EcouteurDeMouvementDeSouris.creerCurseurGenerique("sword", new Point(16, 16)));

        setVisible(true);
//        musique.joueSon(true);
    }

    public CardLayout getPileCarte() {
        return pileCarte;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPlateau(PanelPlateau p) {
        this.plateau = p;
        panelPrincipal.add(p, "plateau");
    }

    public void removePlateau() {
        panelPrincipal.remove(plateau);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Fenetre fenetre = new Fenetre();
            }
        });
    }

}
