package Modele;

import Structure.Pile;

public class Historique {
    Pile<Commande> past, future;

    public Historique() {
        past = new Pile<>();
        future = new Pile<>();
    }

    public void store(Commande cmd) {
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
        cmd.unexecute();
        future.insert(cmd);
    }

    public void redo() {
        Commande cmd = future.extract();
        cmd.execute();
        past.insert(cmd);
    }
}
