package Vue;

import Modele.Plateau;

import javax.swing.*;

public class PanelPlateau extends JPanel {

    JeuGraphique ng;
    Plateau plateau;

    public PanelPlateau(Plateau p) {
        this.plateau = p;
    }

    public PanelPlateau() {
        
    }
}
