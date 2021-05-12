package Vue;

import Modele.Jeu;
import Modele.Plateau;

import javax.swing.*;
import java.awt.*;

public class PanelPlateau extends JPanel {

    Jeu jeu;
    JeuGraphique ng;

    public PanelPlateau() {
        this.jeu = new Jeu(5,5);
        ng = new JeuGraphique(jeu.getPlateau());
        lancerJeu();
    }

    public void lancerJeu(){
        ng.setSize(new Dimension(1500,900));
        System.out.println("lancerJeu();");
        add(ng);
    }
}
