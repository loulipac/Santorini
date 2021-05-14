package Vue;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelRegles extends JPanel {
    private Bouton bRetour;
    private JLabel titre;
    private JPanel panel;
    private JScrollPane scroll;

    public PanelRegles(int largeur, int hauteur) {
        initialiserPanel(largeur, hauteur);
    }

    public void initialiserPanel(int largeur, int hauteur) {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Border blueline = BorderFactory.createLineBorder(Color.blue);

        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));
        add(titre);

        JLabel titreCommentJouer = new JLabel("Comment jouer");
        titreCommentJouer.setBorder(blueline);
        titreCommentJouer.setMaximumSize(new Dimension(largeur/2, hauteur/2));

        /* Boutons */
        bRetour = new Bouton("src/Ressources/bouton/retour.png", "src/Ressources/bouton/retour.png", 415, 90);

        /* Panel */
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(47, 112, 162));
        GridBagConstraints c = new GridBagConstraints();

        JTextArea texteCommentJouer = new JTextArea("En partant du premier joueur, chaque joueur réalise son tour.");

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titreCommentJouer,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(texteCommentJouer,c);
        panel.setMaximumSize(new Dimension(largeur/2,hauteur/2));

        /* Zone scrollable */
        scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);

//        scroll.setBorder(null);
        panel.setBorder(blackline);
        add(scroll);

        bRetour.addActionListener(this::actionBoutonRetourMenu);

        add(bRetour);

        setVisible(true);

    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }
}
