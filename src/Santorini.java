import Vue.Fenetre;

import javax.swing.*;

public class Santorini implements Runnable {

    Fenetre fenetre;

    public void run() {
        fenetre = new Fenetre();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Santorini());
    }
}
