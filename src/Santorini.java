import Vue.Fenetre;

import javax.swing.*;

public class Santorini implements Runnable {

    Fenetre fenetre;

    public void run() {
        fenetre = new Fenetre();
        //fenetreMenu.getbPlay().addActionListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Santorini());
    }

    /* @Override
    public void actionPerformed(ActionEvent e) {
        if (fenetreMenu.getPlayerVsPlayer().isSelected()) {
            // A remplir :
            // Lancer une FenetreJeu à partir de FenetreMenu
            System.out.println("A FAIRE : Lancer une FenetreJeu");
        }
    } */

}
