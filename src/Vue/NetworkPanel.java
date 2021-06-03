package Vue;

import IO.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkPanel extends JPanel {
    Dimension taille_fenetre;
    JTextField ip;
    Client client;

    final String HOSTNAME = "localhost";
    final int PORT = 2121;



    public NetworkPanel(Dimension _taille_fenetre) {
        taille_fenetre = _taille_fenetre;
        initialiserComposant();
    }

    private void initialiserComposant() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel name_panel = new JPanel();
        Dimension taille_panel = new Dimension((int) (taille_fenetre.width * 0.40), (int) (taille_fenetre.height * 0.20));
        name_panel.setPreferredSize(taille_panel);
        name_panel.setMaximumSize(taille_panel);
        name_panel.setLayout(new BoxLayout(name_panel, BoxLayout.LINE_AXIS));

        JLabel name_ind = new JLabel("IP : ");
        Dimension taille_name_ind = new Dimension((int) (taille_panel.width * 0.20), taille_panel.height);
        name_ind.setPreferredSize(taille_name_ind);
        name_ind.setMaximumSize(taille_name_ind);

        ip = new JTextField();
        Dimension taille_textfield = new Dimension((int) (taille_panel.width * 0.80), (int) (taille_panel.height * 0.10));
        ip.setPreferredSize(taille_textfield);
        ip.setMaximumSize(taille_textfield);

        JButton valider = new JButton("Se connecter");
        valider.addActionListener(this::actionConnect);

        name_panel.add(name_ind);
        name_panel.add(ip);
        add(name_panel);
        add(valider);
    }

    public void actionConnect(ActionEvent e) {
        client = new Client(ip.getText(), this);
    }

    public void startGame() {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.setPanel(new PanelPlateau(getSize(), client));
    }
}

