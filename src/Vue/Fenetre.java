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
    JPanel shown;

    public Fenetre() {
        setTitle("Santorini");
        setMinimumSize(DEFAULT_FENETRE_TAILLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pileCarte = new CardLayout();
        panelPrincipal = new JPanel(pileCarte);

        musique = new LecteurSon("musiqueBGtest.wav");

        setPanel(new PanelMenu(getSize()));

        add(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Change le curseur de la fenêtre
        setCursor(EcouteurDeMouvementDeSouris.creerCurseurGenerique("sword", new Point(0, 0)));

        setVisible(true);
//        musique.joueSon(true);
    }
    public void setPanel(JPanel p) {
        JPanel tmp_shown = shown;
        shown = p;
        panelPrincipal.add(p);
        pileCarte.show(panelPrincipal, "");
        if(tmp_shown != null) panelPrincipal.remove(tmp_shown);
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
