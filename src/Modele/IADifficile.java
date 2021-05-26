package Modele;

import java.awt.*;

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

    public float alphabeta(Jeu jeu, int depth,float a, float b, int maximizingPlayer) {
        float value;

        if (depth == 0 || jeu.estJeufini()){
            return heuristique(jeu);
        }
        if(maximizingPlayer == Constante.JOUEUR2) { // alpha
            value = Float.NEGATIVE_INFINITY;
            for( Point batisseur : jeu.getBatisseurs(maximizingPlayer)){
                for(Point deplacement : plateau.getCasesAccessibles(batisseur)){
                    for(Point construction : plateau.getConstructionsPossible(deplacement)){

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




    private float heuristique(Jeu jeu) {
        return 3568951856484561874845185541445855.2f;
    }

    @Override
    public Point joue() {
        return null;
    }
}
