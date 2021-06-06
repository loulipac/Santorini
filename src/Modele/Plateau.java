package Modele;

import java.awt.*;
import java.util.ArrayList;

import static Modele.Constante.JOUEUR1;

/**
 * Classe gérant les cases de la grille dont les constructions de bâtiment et la pose de batisseurs.
 */
public class Plateau {
    public int[][] cases;
    private int lignes;
    private int colonnes;

    public static final int VIDE = 0;
    public static final int RDC = 1;
    public static final int ETAGE = 2;
    public static final int TOIT = 3;
    public static final int COUPOLE = 4;

    /**
     * Instantie une classe Plateau depuis une taille de grille passé en paramètre du constructeur.
     *
     * @param nombre_lignes
     * @param nombre_colonnes
     */
    public Plateau(int nombre_lignes, int nombre_colonnes) {
        cases = new int[nombre_lignes][nombre_colonnes];
        lignes = nombre_lignes;
        colonnes = nombre_colonnes;
    }

    public Plateau(Plateau plateau) {
        lignes = plateau.lignes;
        colonnes = plateau.colonnes;

        cases = new int[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                cases[i][j] = plateau.cases[i][j];
            }
        }
    }

    /**
     * Vérifie que la case (c, l) est vide.
     *
     * @param position la position sur la grille
     * @return vrai si la case de la grille est vide
     */
    public boolean estVide(Point position) {
        return getTypeBatiments(position) == VIDE;
    }

    /**
     * Vérifie que la case (c, l) est un bâtiment de hauteur 1.
     *
     * @param position la position sur la grille
     * @return vrai si la case de la grille est un rez-de-chaussée (bâtiment de hauteur 1)
     */
    public boolean estRDC(Point position) {
        return getTypeBatiments(position) == RDC;
    }

    /**
     * Vérifie que la case (c, l) est un bâtiment de hauteur 2.
     *
     * @param position la position sur la grille
     * @return vrai si la case de la grille est un étage (bâtiment de hauteur 2)
     */
    public boolean estEtage(Point position) {
        return getTypeBatiments(position) == ETAGE;
    }

    /**
     * Vérifie que la case (c, l) est un bâtiment de hauteur 3.
     *
     * @param position la position sur la grille
     * @return boolean si la case de la grille est un toit (bâtiment de hauteur 3)
     */
    public boolean estToit(Point position) {
        return getTypeBatiments(position) == TOIT;
    }

    /**
     * Vérifie que la case (c, l) est un bâtiment de hauteur 4 (une coupole).
     *
     * @param position la position sur la grille
     * @return boolean si la case de la grille est une coupole (bâtiment de hauteur 4)
     */
    public boolean estCoupole(Point position) {
        return getTypeBatiments(position) == COUPOLE;
    }

    /**
     * Ajoute un rez-de-chaussée (bâtiment de hauteur 1) sur une case de la grille.
     *
     * @param position la position sur la grille
     */
    public void ajouterRDC(Point position) {
        setCase(position, getTypeBatisseurs(position) | RDC);
    }

    /**
     * Ajoute un étage (bâtiment de hauteur 2) sur une case de la grille.
     *
     * @param position la position sur la grille
     */
    public void ajouterEtage(Point position) {
        setCase(position, getTypeBatisseurs(position) | ETAGE);
    }

    /**
     * Ajoute un toit (bâtiment de hauteur 3) sur une case de la grille.
     *
     * @param position la position sur la grille
     */
    public void ajouterToit(Point position) {
        setCase(position, getTypeBatisseurs(position) | TOIT);
    }

    /**
     * Ajoute une coupole (bâtiment de hauteur 4) sur une case de la grille.
     *
     * @param position la position sur la grille
     */
    public void ajouterCoupole(Point position) {
        setCase(position, getTypeBatisseurs(position) | COUPOLE);
    }

    /**
     * Ameliore le batiment à la position l c si c'est possible, considère qu'il n'y pas de batisseurs dessus.
     *
     * @param position la position sur la grille
     * @return vrai si l'amélioration a marché
     */
    public boolean ameliorerBatiment(Point position) {
        setCase(position, getCase(position) + 1);
        return true;
    }
    public boolean degraderBatiment(Point position) {
        setCase(position, getCase(position) - 1);
        return true;
    }

    /**
     * Vérifie que l'on peut sélectionner le batisseur aux coordonnées (position) appartient au joueur.
     *
     * @param position la position sur la grille
     * @param joueur   le numéro de joueur (joueur en cours)
     */
    public boolean estBatisseur(Point position, int joueur) {
        return getTypeBatisseurs(position) == joueur;
    }


    public ArrayList<Point> rechercherBatisseurs(int joueur) {
        ArrayList<Point> batisseurs = new ArrayList<Point>();
        int nb_batisseurs = 0;
        for (int i = 0; i < lignes && nb_batisseurs < 2; i++) {
            for (int j = 0; j < colonnes && nb_batisseurs < 2; j++) {
                Point point = new Point(j, i);
                if (estBatisseur(point, joueur)) {
                    batisseurs.add(point);
                    nb_batisseurs++;
                }
            }
        }
        return batisseurs;
    }



    /**
     * Vérifie que la case de la grille ne contient pas de joueur.
     *
     * @param position la position sur la grille
     * @return vrai si la case ne contient pas de joueur
     */
    public boolean estLibre(Point position) {
        return getTypeBatisseurs(position) == 0;
    }

    /**
     * Vérifie que la case de la grille visé peut acceuillir le batisseur (deplacement vers le haut de uniquement 1 de hauteur).
     *
     * @param position  la position sur la grille
     * @param batisseur position (x;y) d'un batisseur
     * @return si la case peut acceuillir un batisseur
     */
    public boolean deplacementPossible(Point position, Point batisseur) {
        return estLibre(position) && atteignable(position, batisseur) &&
                getTypeBatiments(position) - getTypeBatiments(batisseur) <= 1 && !estCoupole(position);
    }

    /**
     * Ajoute un joueur sur la case de la grille.
     *
     * @param position la position sur la grille
     * @param joueur   le numéro de joueur (joueur en cours)
     */
    public void ajouterJoueur(Point position, int joueur) {
        setCase(position, (getTypeBatiments(position) | joueur));
    }

    /**
     * Vérifie que la distance entre le batisseur et le case cliqué est inférieur à 2 (en x et en y)
     *
     * @param position  la position sur la grille
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai s'il est possible d'atteindre la case
     */
    public boolean atteignable(Point position, Point batisseur) {
        int a = Math.abs(position.x - batisseur.x);
        int b = Math.abs(position.y - batisseur.y);
        return (a + b > 0) && (a < 2) && (b < 2);
    }

    /**
     * Construit un tableau de cases accessible depuis la position d'un batisseur.
     *
     * @param batisseurs position (x;y) d'un batisseur
     * @return un ensemble de case accessible
     */
    public ArrayList<Point> getCasesAccessibles(Point batisseurs) {
        ArrayList<Point> cases_acessibles = new ArrayList<>();

        int l = batisseurs.x;
        int c = batisseurs.y;

        boolean case_existe_haut = l - 1 >= 0;
        boolean case_existe_bas = l + 1 < lignes;
        boolean case_existe_droite = c + 1 < colonnes;
        boolean case_existe_gauche = c - 1 >= 0;

        if (case_existe_haut) {
            if (case_existe_gauche && deplacementPossible(new Point(batisseurs.x - 1, batisseurs.y - 1), batisseurs)) {
                cases_acessibles.add(new Point(l - 1, c - 1));
            }
            if (case_existe_droite && deplacementPossible(new Point(batisseurs.x - 1, batisseurs.y + 1), batisseurs)) {
                cases_acessibles.add(new Point(l - 1, c + 1));
            }
            if (deplacementPossible(new Point(batisseurs.x - 1, batisseurs.y), batisseurs))
                cases_acessibles.add(new Point(l - 1, c));
        }
        if (case_existe_bas) {
            if (case_existe_gauche && deplacementPossible(new Point(batisseurs.x + 1, batisseurs.y - 1), batisseurs)) {
                cases_acessibles.add(new Point(l + 1, c - 1));
            }
            if (case_existe_droite && deplacementPossible(new Point(batisseurs.x + 1, batisseurs.y + 1), batisseurs)) {
                cases_acessibles.add(new Point(l + 1, c + 1));
            }
            if (deplacementPossible(new Point(batisseurs.x + 1, batisseurs.y), batisseurs))
                cases_acessibles.add(new Point(l + 1, c));
        }
        if (case_existe_gauche && deplacementPossible(new Point(batisseurs.x, batisseurs.y - 1), batisseurs)) {
            cases_acessibles.add(new Point(batisseurs.x, batisseurs.y - 1));
        }
        if (case_existe_droite && deplacementPossible(new Point(batisseurs.x, batisseurs.y + 1), batisseurs)) {
            cases_acessibles.add(new Point(batisseurs.x, batisseurs.y + 1));
        }
        return cases_acessibles;
    }

    /**
     * Construit un tableau de case où une construction peut-être faite depuis la position d'un batisseur.
     *
     * @param batisseurs position (x;y) d'un batisseur
     * @return une ensemble de case où la construction est possible
     */
    public ArrayList<Point> getConstructionsPossible(Point batisseurs) {
        ArrayList<Point> constructions_possibles = new ArrayList<>();

        int l = batisseurs.x;
        int c = batisseurs.y;

        boolean case_existe_haut = l - 1 >= 0;
        boolean case_existe_bas = l + 1 < lignes;
        boolean case_existe_droite = c + 1 < colonnes;
        boolean case_existe_gauche = c - 1 >= 0;

        if (case_existe_haut) {
            if (case_existe_gauche && peutConstruire(new Point(batisseurs.x - 1, batisseurs.y - 1), batisseurs)) {
                constructions_possibles.add(new Point(l - 1, c - 1));
            }
            if (case_existe_droite && peutConstruire(new Point(batisseurs.x - 1, batisseurs.y + 1), batisseurs)) {
                constructions_possibles.add(new Point(l - 1, c + 1));
            }
            if (peutConstruire(new Point(batisseurs.x - 1, batisseurs.y), batisseurs)) {
                constructions_possibles.add(new Point(l - 1, c));
            }

        }
        if (case_existe_bas) {
            if (case_existe_gauche && peutConstruire(new Point(batisseurs.x + 1, batisseurs.y - 1), batisseurs)) {
                constructions_possibles.add(new Point(l + 1, c - 1));
            }
            if (case_existe_droite && peutConstruire(new Point(batisseurs.x + 1, batisseurs.y + 1), batisseurs)) {
                constructions_possibles.add(new Point(l + 1, c + 1));
            }
            if (peutConstruire(new Point(batisseurs.x + 1, batisseurs.y), batisseurs)) {
                constructions_possibles.add(new Point(l + 1, c));
            }

        }

        if (case_existe_gauche && peutConstruire(new Point(batisseurs.x, batisseurs.y - 1), batisseurs)) {
            constructions_possibles.add(new Point(batisseurs.x, batisseurs.y - 1));
        }
        if (case_existe_droite && peutConstruire(new Point(batisseurs.x, batisseurs.y + 1), batisseurs)) {
            constructions_possibles.add(new Point(batisseurs.x, batisseurs.y + 1));
        }
        return constructions_possibles;
    }

    public ArrayList<Point> getCasesVoisines(Point batisseur) {
        ArrayList<Point> cases_acessibles = new ArrayList<>();

        int l = batisseur.x;
        int c = batisseur.y;

        boolean case_existe_haut = l - 1 >= 0;
        boolean case_existe_bas = l + 1 < lignes;
        boolean case_existe_droite = c + 1 < colonnes;
        boolean case_existe_gauche = c - 1 >= 0;

        if (case_existe_haut) {
            if (case_existe_gauche) {
                cases_acessibles.add(new Point(l - 1, c - 1));
            }
            if (case_existe_droite) {
                cases_acessibles.add(new Point(l - 1, c + 1));
            }
        }
        if (case_existe_bas) {
            if (case_existe_gauche) {
                cases_acessibles.add(new Point(l + 1, c - 1));
            }
            if (case_existe_droite) {
                cases_acessibles.add(new Point(l + 1, c + 1));
            }
        }
        if (case_existe_gauche) {
            cases_acessibles.add(new Point(batisseur.x, batisseur.y - 1));
        }
        if (case_existe_droite) {
            cases_acessibles.add(new Point(batisseur.x, batisseur.y + 1));
        }
        return cases_acessibles;
    }

    /**
     * Vérifie si un ouvrier peut construire sur la case de la grille en l et c
     *
     * @param position
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai si le batisseur peut construire ici.
     */
    public boolean peutConstruire(Point position, Point batisseur) {
        return (atteignable(position, batisseur) && !estCoupole(position) && estLibre(position));
    }

    private void setCase(Point position, int valeur) {
        cases[position.x][position.y] = valeur;
    }

    private int getCase(Point position) {
        return cases[position.x][position.y];
    }

    public int getLignes() {
        return lignes;
    }

    public int getColonnes() {
        return colonnes;
    }

    public int getTypeBatiments(Point position) {
        return getCase(position) & 7;
    }

    public int getTypeBatisseurs(Point position) {
        return getCase(position) & (~7);
    }

    public void supprimerJoueur(Point position) {
        setCase(position, getTypeBatiments(position));
    }
    

    public void setFloor(Point position, int value) {
        setCase(position, getCase(position) + value);
    }

    public void setFloorBis(Point position, int value) {
        setCase(position, value);
    }

    public void RAZ() {
        cases = new int[lignes][colonnes];
    }

    @Override
    public String toString() {
        String value = "";
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                Point pos = new Point(i, j);
                value += "| " + getTypeBatisseurs(pos) / JOUEUR1 + " : " + getTypeBatiments(pos) + " ";
            }
            value += "\n";
        }
        return value;
    }
}