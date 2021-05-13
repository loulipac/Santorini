package Vue;

import Modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelPlateau extends JPanel {
    private JLabel titre;
    private JButton bRetour;
    private Jeu jeu;
    private JeuGraphique jg;

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

        /* Boutons */
        bRetour = new JButton("Retour au menu");
        bRetour.setAlignmentX(CENTER_ALIGNMENT);
        bRetour.setMaximumSize(new Dimension(300, 40));
        bRetour.addActionListener(this::actionBoutonRetourMenu);
        add(bRetour);
    }

    public void lancerJeu() {
        this.jeu = new Jeu(5, 5);
        jg = new JeuGraphique(jeu);
        jg.setAlignmentX(CENTER_ALIGNMENT);
        jg.addMouseListener(new EcouteurDeSouris(jg));
        jg.setMaximumSize(new Dimension(500,500));
        add(jg);
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }
}
