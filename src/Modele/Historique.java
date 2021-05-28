package Modele;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static Modele.Constante.*;

public class Historique {
    private Stack<Commande> passe, futur;
    private Jeu jeu;

    public Historique(Jeu jeu) {
        passe = new Stack<>();
        futur = new Stack<>();
        this.jeu = jeu;
    }

    public void stocker(Commande cmd) {
        if (cmd == null) return;
        passe.push(cmd);
        futur = new Stack<>();
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }

    public void annuler() {
        Commande cmd = passe.pop();
        cmd.desexecute(jeu);
        futur.push(cmd);
    }

    public void refaire() {
        Commande cmd = futur.pop();
        cmd.execute(jeu);
        passe.push(cmd);
    }

    public String sauvegarder() {
        try {
            String passeStr = passe.toString();
            passeStr = passeStr.substring(1, passeStr.length() - 1);

            ArrayList<Commande> futurArray = new ArrayList<>(futur);
            Collections.reverse(futurArray);
            String futurStr = futurArray.toString();
            futurStr = futurStr.substring(1, futurStr.length() - 1);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String nom_fichier = "save_" + formatter.format(new Date()) + ".sav";
            FileWriter fichier = new FileWriter(SAVES_PATH + nom_fichier);
            fichier.write(passeStr + ", " + futurStr + "\n" + futur.size());
            fichier.close();
            return nom_fichier;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void charger(String nom_fichier) {
        try {
            File fichier = new File(SAVES_PATH + nom_fichier);
            Scanner lecteur = new Scanner(fichier);

            String[] points = lecteur.nextLine().split(", ");
            for (int i = 0; i < points.length; i++) {
                String[] coord = points[i].split(" ");
                jeu.jouer(new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])));
            }

            int nbAnnuler = Integer.parseInt(lecteur.nextLine());
            for (int i = 0; i < nbAnnuler; i++) annuler();

        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + nom_fichier + " n'existe pas");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Le fichier n'a pas le bon format");
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Historique)) return false;

        Historique h = (Historique) o;

        return passe.equals(h.passe) && futur.equals(h.futur);
    }
}
