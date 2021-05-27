package Modele;

import java.awt.*;
import java.util.ArrayList;

import static Modele.Constante.*;

public class IADifficile implements IA {

    private Jeu jeu;
    private Point[] coup;
    private Plateau plateau;
    private float meilleurHeuristique;

    public IADifficile(Jeu _jeu) {
        coup = new Point[3];
        jeu = _jeu;
        plateau = jeu.getPlateau();
    }

    public float alphabeta(Jeu jeu, int depth, float a, float b, int maximizingPlayer) {
        float value;

        if (depth == 0 || jeu.estJeufini()) {
            return heuristique(jeu);
        }
        if (maximizingPlayer == Constante.JOUEUR2) { // alpha
            value = Float.NEGATIVE_INFINITY;
            for (Point batisseur : jeu.getBatisseurs(maximizingPlayer)) {
                for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                    for (Point construction : plateau.getConstructionsPossible(deplacement)) {

                    }
                }

                //value = Math.max(value, alphabeta(child, (depth-1), a, b, (maximizingPlayer % 16) + 8 )));
                a = Math.max(a, value);
                if (a >= b)
                    break;
            }
            return value;
        } else { //beta
            value = Float.POSITIVE_INFINITY;
            for (Point p : coup) {
                //value = Math.min(value, alphabeta(child, (depth-1), a, b, (maximizingPlayer % 16) + 8));
                b = Math.min(b, value);
                if (b <= a)
                    break;
            }
            return value;
        }
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Menace du niveau 2 :
     * Le nombre de cases adjacentes au niveau + 1 où le bâtisseur se trouve au niveau 2, ce qui signifie une victoire imminente.
     * La valeur sera positive pour le joueur actuel et négative pour l’adversaire.
     */
    private float menaceDuNiveau2(Jeu _jeu) {
        float heuristique = 0;
        ArrayList<Joueur> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (Joueur j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                ArrayList<Point> cases = _jeu.getPlateau().getCasesAccessibles(batisseur);
                int etageBatisseur = _jeu.getPlateau().getTypeBatiments(batisseur);

                for (Point caseAcc : cases) {
                    if ((_jeu.getPlateau().getTypeBatiments(caseAcc) == TOIT) && (etageBatisseur == ETAGE)) {
                        heuristique += index;
                    }
                }
            }
        }
        return clamp(heuristique, -1, 1);
    }

    /**
     * Combinaison des hauteurs des bâtisseurs :
     * Somme des niveaux des bâtisseurs du joueur moins la somme des niveaux des bâtisseurs de l’adversaire.
     */
    private float combinaisonHauteur(Jeu _jeu) {
        float heuristique = 0;
        ArrayList<Joueur> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (Joueur j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                heuristique += index * _jeu.getPlateau().getTypeBatiments(batisseur);
            }
        }
        return heuristique;
    }


    /**
     * Contrôle de la  case centrale :
     * Si le joueur actuel possède un bâtisseur sur la case centrale.
     */
    private float caseCentrale(Jeu _jeu) {
        ArrayList<Joueur> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (Joueur j : js) {
            for (Point batisseur : j.getBatisseurs()) {
                if (batisseur == CASE_CENTRALE) {
                    return (js.indexOf(j) == 0) ? 1 : -1;
                }
            }
        }
        return 0;
    }

    /**
     * Mobilité verticale du joueur :
     * Nombre de cases vers lesquelles le joueur peut se déplacer avec les cases au niveau du joueur + 1
     * qui sont plus désirables et les cases étant au niveau + 2 et au niveau + 3 qui sont indésirables.
     * La mobilité de l’adversaire est soustraite à celle du joueur actuel
     */
    private float mobiliteVerticale(Jeu _jeu) {
        float heuristique = 0;
        ArrayList<Joueur> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (Joueur j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                ArrayList<Point> _cases = _jeu.getPlateau().getCasesAccessibles(batisseur);
                int etageBatisseur = _jeu.getPlateau().getTypeBatiments(batisseur);
                for (Point _case : _cases) {
                    int etageCase = _jeu.getPlateau().getTypeBatiments(_case);
                    if (etageCase == etageBatisseur + 1) {
                        heuristique += index;
                    } else if (etageCase > etageBatisseur + 1) {
                        // TODO:  niveau + 2 et niveau + 3 indésirable ? Que faire ?
                    }
                }
            }
        }
        return heuristique;
    }

    private float heuristique(Jeu _jeu) {
        return POIDS_BASE * caseCentrale(_jeu) +
                POIDS_BASE * combinaisonHauteur(_jeu) +
                POIDS_BASE * menaceDuNiveau2(_jeu) +
                POIDS_BASE * mobiliteVerticale(_jeu);
    }

    @Override
    public Point joue() {
        return null;
    }
}
