package Vue.PanelPartie;
import Vue.PanelPartie.PanelPlateau.PanelPlateau;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Classe gérant les actions "ECHAP" pour afficher les paramètres
 */
public class ActionEchap extends AbstractAction {

    private final PanelPlateau panel_plateau;
    public ActionEchap(PanelPlateau panel_plateau) {
        super();
        this.panel_plateau = panel_plateau;
        putValue(SHORT_DESCRIPTION, "Afficher les paramètres");
        putValue(MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!panel_plateau.getVictoire_panel().isVisible()) panel_plateau.getPp().setVisible(!panel_plateau.getPp().isVisible());
    }
}