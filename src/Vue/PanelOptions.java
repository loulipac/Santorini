package Vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

class PanelOptions extends JPanel {

    private JButton bRetour;

    public PanelOptions() {


        bRetour = new JButton("Retour");
        bRetour.setAlignmentX(CENTER_ALIGNMENT);
        bRetour.setMaximumSize(new Dimension(300, 40));
        bRetour.addActionListener(this::actionPerformed);

        add(bRetour);

        setBackground(new Color(47, 112, 162));

        add(new JLabel("Game"));
    }

    public void actionPerformed(ActionEvent e) {
        FenetreMenu f2 = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
}