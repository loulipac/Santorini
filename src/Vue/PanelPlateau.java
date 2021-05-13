package Vue;

import Modele.Jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelPlateau extends JPanel {
    private Jeu jeu;
    private JeuGraphique jg;

    public PanelPlateau(int largeur, int hauteur) {
        initialiserPanel();
        lancerJeu(largeur, hauteur);
    }


    public void initialiserPanel() {
        /* BoxLayout */
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(47, 112, 162));

        /* Label */
        JLabel titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        /* Boutons */
        JButton bRetour = new JButton("Retour au menu");
        bRetour.setAlignmentX(CENTER_ALIGNMENT);
        bRetour.setMaximumSize(new Dimension(300, 40));
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        /* Adding */
        add(Box.createRigidArea(new Dimension(40, 40)));
        add(titre);
        add(Box.createRigidArea(new Dimension(40, 20)));
        add(bRetour);
        add(Box.createRigidArea(new Dimension(40, 40)));
    }

    public void lancerJeu(int largeur, int hauteur) {
        this.jeu = new Jeu(5, 5);
        this.jg = new JeuGraphique(jeu);
        jg.setAlignmentX(CENTER_ALIGNMENT);
        jg.addMouseListener(new EcouteurDeSouris(jg));
        int min = Math.min(largeur, hauteur);
        jg.setMaximumSize(new Dimension(min/2,min/2));
        add(jg);

        System.out.println(getSize());
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "menu");
    }
}
