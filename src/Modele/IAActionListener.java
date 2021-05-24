package Modele;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Modele.Constante.PLACEMENT;

public class IAActionListener implements ActionListener {
    Timer timer;
    Jeu jeu;
    public IAActionListener(Jeu _jeu) {
        jeu = _jeu;
        timer = new Timer(800, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
