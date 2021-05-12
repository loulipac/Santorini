package Vue;

import Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class PanelPlateau extends JPanel {
    private JLabel titre;
    Jeu jeu;
    JeuGraphique jg;

    public PanelPlateau() {
        initialiserPanel();
        lancerJeu();
    }


    public void initialiserPanel() {
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
//        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(47, 112, 162));

        /* Label */
        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));
        add(titre);
    }

    public void lancerJeu() {
        this.jeu = new Jeu(5, 5);
        jg = new JeuGraphique(jeu);
        jg.setAlignmentX(CENTER_ALIGNMENT);
        jg.addMouseListener(new EcouteurDeSouris(jg));

        add(jg);
    }
}
