package Modele;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

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

            ArrayList<Commande> futureArray = new ArrayList<>(future);
            Collections.reverse(futureArray);
            String futureStr = futureArray.toString();
            futureStr = futureStr.substring(1, futureStr.length() - 1);

            FileWriter file = new FileWriter(SAVES_PATH + "save.txt");
            file.write(pastStr + ", " + futureStr + "\n" + future.size());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            File file = new File(SAVES_PATH + "save.txt");
            Scanner reader = new Scanner(file);

            String line = reader.nextLine();
            String[] points = line.split(", ");
            for (int i = 0; i < points.length; i++) {
                String[] coord = points[i].split(" ");
                game.jouer(new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])));
            }

            line = reader.nextLine();
            for (int i = 0; i < Integer.parseInt(line); i++) {
                undo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
