package Modele;

import java.util.Stack;

public class Historique {
    private Stack<Commande> past, future;
    private Jeu game;

    public Historique(Jeu game) {
        past = new Stack<>();
        future = new Stack<>();
        this.game = game;
    }

    public void store(Commande cmd) {
        if (cmd == null) return;
        past.push(cmd);
        future = new Stack<>();
    }

    public boolean canUndo() {
        return !past.isEmpty();
    }

    public boolean canRedo() {
        return !future.isEmpty();
    }

    public void undo() {
        Commande cmd = past.pop();
        cmd.unexecute(game);
        future.push(cmd);
    }

    public void redo() {
        Commande cmd = future.pop();
        cmd.execute(game);
        past.push(cmd);
    }
}
