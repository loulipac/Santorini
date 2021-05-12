package Vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

class PanelMenu extends JPanel {

    private JButton bJouer,bTutoriel,bRegles,bQuitter;
    private JLabel titre;

    public PanelMenu() {

        /* BoxLayout */

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));

        /* Button */
        bJouer = new JButton("Jouer");
        bJouer.setAlignmentX(CENTER_ALIGNMENT);
        bJouer.setMaximumSize(new Dimension(300, 40));

        bTutoriel = new JButton("Tutoriel");
        bTutoriel.setAlignmentX(CENTER_ALIGNMENT);
        bTutoriel.setMaximumSize(new Dimension(300, 40));

        bRegles = new JButton("Règles du jeu");
        bRegles.setAlignmentX(CENTER_ALIGNMENT);
        bRegles.setMaximumSize(new Dimension(300, 40));

        bQuitter = new JButton("Quitter");
        bQuitter.setAlignmentX(CENTER_ALIGNMENT);
        bQuitter.setMaximumSize(new Dimension(300, 40));

        /* Label */

        titre = new JLabel("Santorini");
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setFont(new Font("Arial", Font.BOLD, 20));

        /* redirection */

        bJouer.addActionListener(this::actionPerformed);

        /* Adding */

        add(titre);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bJouer);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bTutoriel);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bRegles);
        add(Box.createRigidArea(new Dimension(40,40)));
        add(bQuitter);
        add(Box.createRigidArea(new Dimension(40,40)));



        //setBackground(Color.GREEN);
        setBackground(new Color(47,112,162));
    }

    public void actionPerformed(ActionEvent e) {
        FenetreMenu f2 = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "game");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
}