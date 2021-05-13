package Vue;

import Modele.BoutonMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelRegles extends JPanel {
    private BoutonMenu bRetour;
    private JLabel titre;
    private JPanel panel;
    private JScrollPane scroll;

    public PanelRegles() {
        initialiserPanel();
    }

    public void initialiserPanel() {
        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));
        add(titre);

        /* Zone scrollable */
        panel = new JPanel();
        panel.setBackground(new Color(47, 112, 162));

        JTextArea textArea = new JTextArea("Test");

        panel.add(textArea);
        scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // scroll.setMaximumSize(new Dimension(500,500));
        scroll.setOpaque(false);
        scroll.setBorder(null);
        add(scroll);

        bRetour = new BoutonMenu("src/Ressources/bouton_menu/retour.png", "src/Ressources/bouton_menu/retour.png", 415, 90);
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        add(bRetour);

        setVisible(true);

    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }
}
