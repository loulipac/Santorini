import Vue.FenetreMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Santorini implements Runnable, ActionListener {

    FenetreMenu fenetreMenu;

    public void run() {
        fenetreMenu = new FenetreMenu();
        fenetreMenu.getbPlay().addActionListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Santorini());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fenetreMenu.getPlayerVsPlayer().isSelected()) {
            // A remplir :
            // Lancer une FenetreJeu à partir de FenetreMenu
            System.out.println("A FAIRE : Lancer une FenetreJeu");
        }
    }

}
