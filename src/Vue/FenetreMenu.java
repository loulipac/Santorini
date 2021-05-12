package Vue;

import javax.swing.*;
import java.awt.*;

/**
 * Crée une fenêtre pour le menu principal du jeu.
 * Cette fenêtre permet de démarrer le jeu, de lire les règles, de suivre un tutoriel.
 * @TODO : renommer les instances de @PanelMenu et @PanelOptions, clean code la fonction @FenetreMenu.
 */
public class FenetreMenu extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;
    PanelMenu menu;
    PanelOptions options;
    PanelPlateau plateau;

    public FenetreMenu() {
        setTitle("Santorini");
        setMinimumSize(new Dimension(1500, 900));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menu = new PanelMenu();
        options = new PanelOptions();
        plateau = new PanelPlateau();
        mainPanel.add(menu, "menu");
        mainPanel.add(options, "options");
        mainPanel.add(plateau, "plateau");

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                FenetreMenu fenetreMenu = new FenetreMenu();
            }
        });
    }

}
