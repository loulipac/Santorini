import Vue.Fenetre;

import javax.swing.*;

/**
 * Classe démarrant l'interface graphique.
 */
public class Santorini implements Runnable {

    @Override
    public void run() {
        new Fenetre();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Santorini());
    }
}
