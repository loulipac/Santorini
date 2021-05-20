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
    }

    public boolean canUndo() {
        return !past.isEmpty();
    }

    public boolean canRedo() {
        return !future.isEmpty();
    }

    public void undo() {
        Commande cmd = past.extract();
        cmd.unexecute(level);
        future.insert(cmd);
    }

    public void redo() {
        Commande cmd = future.extract();
        cmd.execute(level);
        past.insert(cmd);
    }
}
