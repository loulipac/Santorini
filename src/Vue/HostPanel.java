package Vue;

import IO.Client;
import IO.Player;
import IO.Server;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class HostPanel extends JPanel{
    Dimension taille_fenetre;
    Server serveur;


    public HostPanel(Dimension _taille_fenetre) {
        taille_fenetre = _taille_fenetre;
        initialiserServeur();
        initialiserComposant();
    }

    private void initialiserServeur() {
        serveur = new Server(this);
    }

    private void initialiserComposant() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



        JLabel my_ip = new JLabel(serveur.getIPAddress());

        JButton valider = new JButton("Se connecter");
        valider.addActionListener(this::actionStart);


        add(my_ip);
        add(valider);
    }

    public void actionStart(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        serveur.startPartie();
        f.setPanel(new PanelPlateau(getSize(), serveur));
    }
}

