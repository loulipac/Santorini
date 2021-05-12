package Vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Crée une fenêtre pour le menu principal du jeu.
 * Cette fenêtre permet de démarrer le jeu, de lire les règles, de suivre un tutoriel.
 */
public class FenetreMenu extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;
    PanelMenu menu;
    PanelOptions game;

    public FenetreMenu() {
        new JFrame("Santorini - Menu Principal");
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menu = new PanelMenu();
        game = new PanelOptions();
        mainPanel.add(menu, "menu");
        mainPanel.add(game, "game");




        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                FenetreMenu gameFrame = new FenetreMenu();
            }
        });
    }

}
