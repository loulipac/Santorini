package Modele;

import static Modele.Constante.*;

import java.awt.Point;

public class CoupConstruire extends Commande {
    private Point position, builder;

    public CoupConstruire(int player, Point position, Point builder) {
        super(player);
        this.position = position;
        this.builder = builder;
    }

    @Override
    public void action(Jeu game, int type) {
        int value = type == REDO ? 1 : -1;
        game.getPlateau().setFloor(position, value);

        int situation = CONSTRUCTION;
        Point selected_builder = builder;
        if (type == REDO) {
            situation = SELECTION;
            selected_builder = null;
        }
        game.setSituation(situation);
        game.switchPlayer();
        game.setBatisseur_en_cours(selected_builder);
        game.MAJObservateur();
    }

    @Override
    public String toString() {
        String str = position.x + " " + position.y;
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CoupConstruire)) return false;

        CoupConstruire c = (CoupConstruire) o;

        return player == c.player &&
                position.equals(c.position) &&
                builder.equals(c.builder);
    }
}
