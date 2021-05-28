package Modele;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Modele.Constante.*;

public class IADifficile implements IA {

    private Jeu jeu;
    private ArrayList<Coups> coups_liste;
    private Coups coups;
    private Plateau plateau;
    private float meilleurHeuristique;
    private Random random;

    public IADifficile(Jeu _jeu) {
        coups_liste = new ArrayList<>();
        jeu = _jeu;
        plateau = jeu.getPlateau();
        random = new Random();
    }

    private class Coups {
        Point batisseur;
        Point deplacement;
        Point construction;

        public Coups(Point batisseur, Point deplacement, Point construction) {
            this.batisseur = batisseur;
            this.deplacement = deplacement;
            this.construction = construction;
        }

        @Override
        public String toString() {
            return "Coup{" +
                    "batisseur=" + batisseur +
                    ", deplacement=" + deplacement +
                    ", construction=" + construction +
                    '}';
        }
    }

    public float alphabeta(Jeu jeu, int depth, float a, float b, int maximizingPlayer) {
        float value;
        if (depth == 0 || jeu.estJeufini()) {
            return heuristique(jeu);
        }

        ArrayList<Point> batisseur_copy = null;
        batisseur_copy = new ArrayList<>(jeu.getBatisseurs(maximizingPlayer));
        if (maximizingPlayer == Constante.JOUEUR2) { // alpha
            value = Float.NEGATIVE_INFINITY;
            for (Point batisseur : batisseur_copy) {
                for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                    for (Point construction : plateau.getConstructionsPossible(deplacement)) {
                        jeu.setSimulation(true);
                        int index_batisseur = batisseur_copy.indexOf(batisseur);
                        jouer(jeu, batisseur, deplacement, construction);
                        value = Math.max(value, alphabeta(jeu, depth - 1, a, b, (maximizingPlayer % 16) + 8));
                        dejouer(jeu, maximizingPlayer, index_batisseur);
                        jeu.setBatisseursJoueur(batisseur_copy);
                        jeu.setSimulation(false);
                        if (value > a) {
                            coups_liste.clear();
                            coups_liste.add(new Coups(batisseur, deplacement, construction));
                            a = value;
                        } else if (value == a) {
                            coups_liste.add(new Coups(batisseur, deplacement, construction));
                        }
                        if (b <= value) return value;
                    }
                }
            }
            return value;
        } else {
            value = Float.POSITIVE_INFINITY;
            for (Point batisseur : batisseur_copy) {
                for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                    for (Point construction : plateau.getConstructionsPossible(deplacement)) {
                        jeu.setSimulation(true);
                        int index_batisseur = batisseur_copy.indexOf(batisseur);
                        jouer(jeu, batisseur, deplacement, construction);
                        value = Math.min(value, alphabeta(jeu, depth - 1, a, b, (maximizingPlayer % 16) + 8));
                        dejouer(jeu, maximizingPlayer, index_batisseur);
                        jeu.setSimulation(false);
                        if (value < b) {
                            coups_liste.clear();
                            coups_liste.add(new Coups(batisseur, deplacement, construction));
                            b = value;
                        } else if (value == b) {
                            coups_liste.add(new Coups(batisseur, deplacement, construction));
                        }
                        if (a >= value) return value;
                    }
                }
            }
            return value;
        }
    }

    /**
     * Joue un tour complet
     */
    private void jouer(Jeu _jeu, Point batisseur, Point deplacement, Point construire) {
        // Selection
        prevPos = batisseur;
        _jeu.joueSelection(batisseur);
        // Déplacement
        this.batisseur = deplacement;
        _jeu.joueDeplacement(deplacement);
        // Construction
        prevHaut = _jeu.getPlateau().getTypeBatiments(construire);
        construction = construire;
        _jeu.joueConstruction(construire);
    }

    Point batisseur;
    Point prevPos;
    Point construction;
    int prevHaut;

    /**
     * Déjoue un tour complet (soit 2 undo)
     */
    private void dejouer(Jeu _jeu, int num_joueur, int index_batisseur) {
        _jeu.getPlateau().setFloorBis(construction, prevHaut);
        _jeu.getPlateau().removePlayer(batisseur);
        _jeu.getPlateau().ajouterJoueur(prevPos, num_joueur);
        _jeu.updateBatisseur(index_batisseur, prevPos, num_joueur);
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
        switch (jeu.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                alphabeta(jeu, 2, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, jeu.getJoueur_en_cours());
                coups = coups_liste.get(random.nextInt(coups_liste.size()));
                return coups.batisseur;
            case DEPLACEMENT:
                return coups.deplacement;
            case CONSTRUCTION:
                return coups.construction;
            default:
                break;
        }
        return null;
    }


    private Point jouePlacement() {
        int colonnes = jeu.getPlateau().getColonnes();
        int lignes = jeu.getPlateau().getLignes();
        Point case_alea = null;
        do {
            case_alea = new Point(
                    random.nextInt(colonnes),
                    random.nextInt(lignes)
            );
        } while (!jeu.getPlateau().estLibre(case_alea));
        return case_alea;
    }
}
