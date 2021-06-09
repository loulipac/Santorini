package Vue.PanelPartie;

import Utile.Utile;

import javax.swing.*;
import java.awt.*;

public class VictoireBackgroundPanel extends JPanel {

    public VictoireBackgroundPanel(Dimension taille) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(taille);
        setPreferredSize(taille);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Utile.dessineParcheminVictoire(g, getSize(), null);
    }
}