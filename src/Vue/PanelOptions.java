package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class PanelOptions extends JPanel {

    private JButton bRetour;

    public PanelOptions() {
        initialiserPanel();
    }

    public void initialiserPanel() {
        setBackground(new Color(47, 112, 162));

        bRetour = new JButton("Retour au menu");
        bRetour.setAlignmentX(CENTER_ALIGNMENT);
        bRetour.setMaximumSize(new Dimension(300, 40));
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        add(bRetour);
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
}