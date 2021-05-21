package Modele;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IAActionListener implements ActionListener {

    Timer timer;
    IA ia;

    public IAActionListener(IA ia) {
        this.ia = ia;
        timer = new Timer(800, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ia.joue();
    }
}
