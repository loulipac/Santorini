package Modele;

import java.io.FileWriter;
import java.util.Stack;

import static Modele.Constante.*;

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

    public void save() {
        try {
            String pastStr = past.toString();
            pastStr = pastStr.substring(1, pastStr.length() - 1);
            String futureStr = future.toString();
            futureStr = futureStr.substring(1, futureStr.length() - 1);

            FileWriter file = new FileWriter(SAVES_PATH + "save.txt");
            file.write(pastStr + ", " + futureStr + "\n" + future.size());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
