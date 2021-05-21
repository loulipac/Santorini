package Modele;

import Structure.Pile;

public class Historique {
    private Pile<Commande> past, future;
    private Jeu game;

    public Historique(Jeu game) {
        past = new Pile<>();
        future = new Pile<>();
        this.game = game;
    }

    public void store(Commande cmd) {
        if (cmd == null) return;
        past.insert(cmd);
        future = new Pile<>();
    }

    public boolean canUndo() {
        return !past.isEmpty();
    }

    public boolean canRedo() {
        return !future.isEmpty();
    }

    public void undo() {
        Commande cmd = past.extract();
        cmd.unexecute(game);
        future.insert(cmd);
    }

    public void redo() {
        Commande cmd = future.extract();
        cmd.execute(game);
        past.insert(cmd);
    }
}
