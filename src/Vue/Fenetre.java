package Vue;

import javax.swing.*;
import java.awt.*;

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
    PanelParametres parametres;

    public Fenetre() {
        setTitle("Santorini");
        setMinimumSize(new Dimension(1600, 900));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pileCarte = new CardLayout();
        panelPrincipal = new JPanel(pileCarte);
        menu = new PanelMenu(getSize().width, getSize().height);
        options = new PanelOptions(getSize().width, getSize().height);
        regles = new PanelRegles(getSize().width, getSize().height);
        plateau = new PanelPlateau(getSize().width, getSize().height);
        parametres = new PanelParametres(getSize().width, getSize().height);

        panelPrincipal.add(menu, "menu");
        panelPrincipal.add(options, "options");
        panelPrincipal.add(regles, "regles");
        panelPrincipal.add(plateau, "plateau");
        panelPrincipal.add(parametres, "parametres");

        add(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public CardLayout getPileCarte() {
        return pileCarte;
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
