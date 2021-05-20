package Modele;

import Structure.Pile;

public class Historique {
    private Pile<Commande> past, future;
    private Plateau level;

    public Historique(Plateau level) {
        past = new Pile<>();
        future = new Pile<>();
        this.level = level;
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

    public Commande undo() {
        Commande cmd = past.extract();
        future.insert(cmd);
        return cmd.unexecute(level);
    }

    public Commande redo() {
        Commande cmd = future.extract();
        past.insert(cmd);
        return cmd.execute(level);
    }
}
