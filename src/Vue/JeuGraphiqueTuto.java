package Vue;

import Modele.JeuTuto;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static Utile.Constante.*;

public class JeuGraphiqueTuto extends JeuGraphique {
    JeuTuto jeu_tuto;
    int num_etape;
    Color c_fond = creerCouleur(67, 204, 212, 0.5f);
    Color c_bordure = creerCouleur(80, 186, 245, 1f);
    Point clic_prec;
    Timer timer;
    static final int VITESSE_BASE = 1000;
    Observateur o;

    /**
     * Constructeur de JeuGraphiqueTuto.
     *
     * @param j         un JeuTuto
     * @param num_etape le numéro de l'étape
     */
    public JeuGraphiqueTuto(JeuTuto j, int num_etape, Observateur o) {
        super(j);
        this.jeu_tuto = j;
        this.num_etape = num_etape;
        this.o = o;
        timer = new Timer(VITESSE_BASE, e -> animationEtapes());

    }

    /**
     * Affiche le JeuTuto, affiche également par dessus les zones cliquables, et démarre les animations suivant le numéro d'étape
     *
     * @param g le Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        drawable.clearRect(0, 0, getTailleCase() * PLATEAU_COLONNES, getTailleCase() * PLATEAU_LIGNES);
        super.paintComponent(g);
        jeu_tuto.setSituation(ATTENTE);
        switch (num_etape) {
            case 2:
                jeu_tuto.setSituation(PLACEMENT);
                dessinerRectangle(drawable, new Point(1, 1), c_fond, c_bordure);
                dessinerRectangle(drawable, new Point(3, 2), c_fond, c_bordure);
                break;
            case 3, 9, 13, 16:
                timerSet(true);
                break;

            case 5:
                ActionsInitialisation(drawable, SELECTION, new Point(1,1),new Point(1,1));
                break;
            case 6:
                ActionsInitialisation(drawable, DEPLACEMENT, new Point(1,1),new Point(1,2));
                break;
            case 8 :
                ActionsInitialisation(drawable, CONSTRUCTION, new Point(1,2),new Point(2,2));
                break;
            case 10:
                ActionsInitialisation(drawable, DEPLACEMENT, new Point(1,2),new Point(2,2));
                break;
            case 12:
                ActionsInitialisation(drawable, CONSTRUCTION, new Point(2,2),new Point(3,3));
                break;
            case 15:
                ActionsInitialisation(drawable, CONSTRUCTION, new Point(4,2),new Point(3,3));
                break;
            case 18:
                ActionsInitialisation(drawable, SELECTION, new Point(1,2),new Point(1,2));
                break;
            case 19:
                ActionsInitialisation(drawable, DEPLACEMENT, new Point(1,2),new Point(2,2));
                break;
            default:
                break;
        }
        repaint();
    }

    /**
     * Initialise la position et la situation du batisseur qui va jouer, puis dessine la case bleu (objectif de l'étape)
     */
    private void ActionsInitialisation(Graphics2D drawable, int typeAction, Point pointDepart, Point pointArrivee){
        jeu_tuto.setBatisseurEnCours(pointDepart);
        jeu_tuto.setSituation(typeAction);
        dessinerRectangle(drawable, pointArrivee, c_fond, c_bordure);

    }


    /**
     * Redirige vers des sous-fonction d'animation en fonction du numéro d'étape
     */
    public void animationEtapes() {
        switch (num_etape) {
            case 3 -> animationEtape3();
            case 9 -> animationEtape9();
            case 13 -> animationEtape13();
            case 17 -> animationEtape17();
            default -> timerSet(false);
        }
        repaint();
    }

    /**
     * Effectue l'animation pour une étape donnée
     */
    public void animationEtape3() {
        Point pos_pion1 = new Point(1, 3);
        Point pos_pion2 = new Point(4, 2);

        if (jeu_tuto.getPlateau().estBatisseur(pos_pion1, jeu_tuto.getJ2())
                && jeu_tuto.getPlateau().estBatisseur(pos_pion2, jeu_tuto.getJ2())) {
            timerSet(false);
            o.miseAjour();

        } else if (jeu_tuto.getPlateau().estBatisseur(pos_pion1, jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterJoueur(pos_pion2, jeu_tuto.getJ2());

        } else {
            jeu_tuto.getPlateau().ajouterJoueur(pos_pion1, jeu_tuto.getJ2());
        }
    }

    /**
     * Effectue l'animation pour une étape donnée
     */
    public void animationEtape9() {
        Point pos_batiment = new Point(3, 3);
        Point nouv_pos_J2 = new Point(4, 3);

        if (jeu_tuto.getPlateau().estBatisseur(nouv_pos_J2, jeu_tuto.getJ2())
                && jeu_tuto.getPlateau().estRDC(pos_batiment)) {
            timerSet(false);
            o.miseAjour();

        } else if (jeu_tuto.getPlateau().estBatisseur(nouv_pos_J2, jeu_tuto.getJ2())) {
            jeu_tuto.construireBatiment(pos_batiment, 1);

        } else {
            jeu_tuto.getPlateau().enleverJoueur(new Point(4, 2));
            jeu_tuto.getPlateau().ajouterJoueur(nouv_pos_J2, jeu_tuto.getJ2());
        }
    }

    /**
     * Effectue l'animation pour une étape donnée
     */
    public void animationEtape13() {
        Point[] pos_J2 = {
                new Point(1, 3),
                new Point(1, 2),
                new Point(2, 3),
                new Point(2, 2)
        };

        Point[] pos_J1 = {
                new Point(2, 2),
                new Point(3, 1),
                new Point(2, 1),
                new Point(3, 2),
                new Point(4, 2)
        };

        Point[] construction = {
                new Point(2, 3),
                new Point(2, 2),
                new Point(1, 2),
                new Point(1, 1),
                new Point(3, 3)
        };

        if (jeu_tuto.getPlateau().estBatisseur(pos_J1[4], jeu_tuto.getJ1())) {
            timerSet(false);
            o.miseAjour();
        } else if (jeu_tuto.getPlateau().estToit(construction[4])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J1[3]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J1[4], jeu_tuto.getJ1());
        } else if (jeu_tuto.getPlateau().estBatisseur(pos_J2[3], jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterToit(construction[4]);
        } else if (jeu_tuto.getPlateau().estRDC(construction[3])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J2[2]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J2[3], jeu_tuto.getJ2());
        } else if (jeu_tuto.getPlateau().estBatisseur(pos_J1[2], jeu_tuto.getJ1())) {
            jeu_tuto.getPlateau().ajouterRDC(construction[3]);
        } else if (jeu_tuto.getPlateau().estRDC(construction[2])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J1[1]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J1[2], jeu_tuto.getJ1());
        } else if (jeu_tuto.getPlateau().estBatisseur(pos_J2[2], jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterRDC(construction[2]);
        } else if (jeu_tuto.getPlateau().estEtage(construction[1])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J2[1]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J2[2], jeu_tuto.getJ2());
        } else if (jeu_tuto.getPlateau().estBatisseur(pos_J1[1], jeu_tuto.getJ1())) {
            jeu_tuto.getPlateau().ajouterEtage(construction[1]);
        } else if (jeu_tuto.getPlateau().estRDC(construction[0])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J1[0]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J1[1], jeu_tuto.getJ1());
        } else if (jeu_tuto.getPlateau().estBatisseur(pos_J2[1], jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterRDC(construction[0]);
        } else {
            jeu_tuto.getPlateau().enleverJoueur(pos_J2[0]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J2[1], jeu_tuto.getJ2());
        }
    }

    /**
     * Effectue l'animation pour une étape donnée
     */
    public void animationEtape17() {
        Point[] pos_J2 = {
                new Point(2, 2),
                new Point(1,3),
                new Point(2,4),
                new Point(1,4)
        };

        Point[] pos_J1 = {
                new Point(2, 1),
                new Point(1, 1),
                new Point(1,2)
        };

        Point[] construction = {
                new Point(1, 2),
                new Point(2, 2),
                new Point(2,3),
                new Point(1,1),
                new Point(1,3)
        };

        if (jeu_tuto.getPlateau().estRDC(construction[4])) {
            timerSet(false);
            o.miseAjour();
        }
        else if (jeu_tuto.getPlateau().estBatisseur(pos_J2[3], jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterRDC(construction[4]);
        }
        else if (jeu_tuto.getPlateau().estEtage(construction[3])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J2[2]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J2[3], jeu_tuto.getJ2());
        }
        else if (jeu_tuto.getPlateau().estBatisseur(pos_J1[2], jeu_tuto.getJ1())) {
            jeu_tuto.getPlateau().ajouterEtage(construction[3]);
        }
        else if (jeu_tuto.getPlateau().estEtage(construction[2])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J1[1]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J1[2], jeu_tuto.getJ1());
        }
        else if (jeu_tuto.getPlateau().estBatisseur(pos_J2[2], jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterEtage(construction[2]);
        }
        else if (jeu_tuto.getPlateau().estToit(construction[1])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J2[1]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J2[2], jeu_tuto.getJ2());
        }
        else if (jeu_tuto.getPlateau().estBatisseur(pos_J1[1], jeu_tuto.getJ1())) {
            jeu_tuto.getPlateau().ajouterToit(construction[1]);
        }
        else if (jeu_tuto.getPlateau().estEtage(construction[0])) {
            jeu_tuto.getPlateau().enleverJoueur(pos_J1[0]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J1[1], jeu_tuto.getJ1());
        }
        else if (jeu_tuto.getPlateau().estBatisseur(pos_J2[1], jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterEtage(construction[0]);
        } else {
            jeu_tuto.getPlateau().enleverJoueur(pos_J2[0]);
            jeu_tuto.getPlateau().ajouterJoueur(pos_J2[1], jeu_tuto.getJ2());
        }

    }

    /**
     * Modifie le mode du timer (activé ou désactivé)
     *
     * @param statut un booléen qui active ou désactive le timer
     */
    public void timerSet(boolean statut) {
        if (statut) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    /**
     * dessine un rectangle à une position donnée
     *
     * @param drawable  le Graphics2D
     * @param position  la position où dessiner le rectangle
     * @param c_fond    la couleur de fond du rectangle
     * @param c_bordure la couleur de la bordure du rectangle
     */
    public void dessinerRectangle(Graphics2D drawable, Point position, Color c_fond, Color c_bordure) {
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float((float) position.y * getTailleCase(), (float) position.x * getTailleCase(), getTailleCase(), getTailleCase(), 10, 10);

        drawable.setColor(c_fond);
        drawable.fillRoundRect(position.y * getTailleCase(), position.x * getTailleCase(), getTailleCase(), getTailleCase(), 10, 10);

        drawable.setStroke(new BasicStroke(4));
        drawable.setColor(c_bordure);
        drawable.draw(roundedRectangle);
    }

    /**
     * Créer une couleur RGBA
     *
     * @param r     le taux de rouge
     * @param g     le taux de rouge
     * @param b     le taux de rouge
     * @param alpha le niveau de transparence (entre 0 et 1, 0 = invisible, 1 = visible)
     */
    public Color creerCouleur(int r, int g, int b, float alpha) {
        return new Color(
                (float) r / 255,
                (float) g / 255,
                (float) b / 255,
                alpha
        );
    }

    /**
     * Réinitialise la valeur du clic précédent
     */
    public void viderClic_prec() {
        clic_prec = new Point(PLATEAU_LIGNES, PLATEAU_COLONNES);
    }

    /**
     * Charge une étape donnée
     *
     * @param num_etape le numéro d'étape
     */
    public void chargerEtape(int num_etape) {
        this.num_etape = num_etape;
        jeu_tuto.chargerEtape(num_etape);
        repaint();
    }

    /**
     * Getter de jeu_tuto
     */
    public JeuTuto getJeu_tuto() {
        return jeu_tuto;
    }
}
