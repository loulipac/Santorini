package Modele;

import Structure.Pile;

public class Historique {
    Pile<Commande> past, future;

    public Historique() {
        past = new Pile<>();
        future = new Pile<>();
    }

    public boolean canUndo() {
        return !past.isEmpty();
    }

    public boolean canRedo() {
        return !future.isEmpty();
    }

    public void undo() {
        Commande comm = past.extract();
        comm.unexecute();
        future.insert(comm);
    }

    public void redo() {
        Commande comm = future.extract();
        comm.execute();
        past.insert(comm);
    }
}
