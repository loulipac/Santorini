package Modele;

import static Modele.Constante.*;

import java.awt.Point;

public class CoupDeplacer extends Commande {
    private Point[] positions;
    private int move;

    public CoupDeplacer(int player, Point prevPos, Point newPos) {
        super(player);
        positions = new Point[2];
        positions[1] = prevPos;
        positions[0] = newPos;
        move = prevPos == null ? PLACEMENT : DEPLACEMENT;
    }

    @Override
    public void action(Jeu game, int type) {
        if (positions[type] != null) {
            game.getPlateau().ajouterJoueur(positions[type], player);
        }
        int i = (type + 1) % 2;
        if (positions[i] != null) {
            game.getPlateau().removePlayer(positions[i]);
        }

        if (move == PLACEMENT) {
            game.setSituation(PLACEMENT);
            int value = type == REDO ? 1 : -1;
            game.setNombre_batisseurs(game.getNombre_batisseurs() + value);
            if (type == REDO) {
                game.checkBuilderNumber();
            } else { // UNDO
                if (game.getNombre_batisseurs() % 2 == 1) {
                    game.finTour();
                }
            }
        } else {
            game.setBatisseur_en_cours(positions[type]);
            if (type == UNDO && game.estJeufini()) game.setJeu_fini(false);
            else if (type == REDO) game.victoireJoueur();
            int situation = type == REDO ? CONSTRUCTION : SELECTION;
            game.setSituation(situation);
            game.MAJObservateur();
            game.iaJoue();
        }
    }

    @Override
    public String toString() {
        String str = new String();
        if (positions[1] != null) str += positions[1].x + " " + positions[1].y + ", ";
        str += positions[0].x + " " + positions[0].y;
        return str;
    }
}
