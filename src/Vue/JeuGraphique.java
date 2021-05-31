package Vue;

import static Modele.Constante.*;

import Modele.Plateau;
import Modele.Jeu;
import javax.swing.*;
import java.awt.*;

/**
 * Classe dessinant les cases du plateau.
 */
public class JeuGraphique extends JComponent {
    private final Plateau plateau;
    private Jeu jeu;
    private int taille_case;
    private final Image case_claire;
    private final Image case_fonce;
    private final Image coupole;
    private final Image etage_1;
    private final Image etage_2;
    private final Image etage_3;
    private final Image batisseur_bleu;
    private final Image batisseur_rouge;
    private final Image batisseur_rouge_selectionne;
    private final Image batisseur_bleu_selectionne;
    private final Image pas_rouge;
    private final Image pas_bleu;
    private final Image outil_bleu;
    private final Image outil_rouge;

    /**
     * Constructeur de JeuGraphique, charge les images en mémoire.
     *
     */
    public JeuGraphique(Jeu j) {
        this.jeu = j;
        case_claire = Utile.readImage(CHEMIN_RESSOURCE + "/cases/case_claire.png");
        case_fonce = Utile.readImage(CHEMIN_RESSOURCE + "/cases/case_fonce.png");
        coupole = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/coupole.png");
        etage_1 = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_1.png");
        etage_2 = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_2.png");
        etage_3 = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_3.png");
        batisseur_bleu = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_bleu.png");
        batisseur_rouge = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_rouge.png");
        batisseur_rouge_selectionne = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_rouge_selectionne.png");
        batisseur_bleu_selectionne = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_bleu_selectionne.png");
        pas_rouge = Utile.readImage(CHEMIN_RESSOURCE + "/icone/pas_rouge.png");
        pas_bleu = Utile.readImage(CHEMIN_RESSOURCE + "/icone/pas_bleu.png");
        outil_bleu = Utile.readImage(CHEMIN_RESSOURCE + "/icone/outil_bleu.png");
        outil_rouge = Utile.readImage(CHEMIN_RESSOURCE + "/icone/outil_rouge.png");
        plateau = j.getPlateau();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        Graphics2D drawable = (Graphics2D) g;

        taille_case = getSize().height / PLATEAU_LIGNES;

        // On efface tout
        int nouvelle_origine = 0;
        drawable.clearRect(
                nouvelle_origine,
                0,
                taille_case * PLATEAU_COLONNES,
                taille_case * PLATEAU_LIGNES + nouvelle_origine
        );


        Point batisseur_en_cours = jeu.getBatisseur_en_cours();
        int batisseurs_ligne = batisseur_en_cours != null ? batisseur_en_cours.x : -1;
        int batisseurs_colonne = batisseur_en_cours != null ? batisseur_en_cours.y : -1;

        Image image_batisseurs;
        Image image_case;
        Image image_batiment;

        for (int l = 0; l < PLATEAU_LIGNES; l++) {
            for (int c = 0; c < PLATEAU_COLONNES; c++) {
                Point position = new Point(l, c);
                Point position_case = new Point(
                        c * taille_case + nouvelle_origine,
                        l * taille_case
                );
                image_case = ((l + c) % 2 == 0) ? case_claire : case_fonce;
                drawable.drawImage(image_case, position_case.x, position_case.y, taille_case, taille_case, null);


                switch (plateau.getTypeBatiments(position)) {
                    case RDC -> image_batiment = etage_1;
                    case ETAGE -> image_batiment = etage_2;
                    case TOIT -> image_batiment = etage_3;
                    case COUPOLE -> image_batiment = coupole;
                    default -> image_batiment = null;
                }

                if (image_batiment != null)
                    drawable.drawImage(image_batiment, position_case.x, position_case.y, taille_case, taille_case, null);

                if (plateau.getTypeBatisseurs(position) > 0) {

                    boolean batisseur_selectionne = (batisseurs_ligne == l && batisseurs_colonne == c);

                    if (plateau.getTypeBatisseurs(position) == JOUEUR1) {
                        image_batisseurs = batisseur_selectionne ? batisseur_bleu_selectionne : batisseur_bleu;
                    } else {
                        image_batisseurs = batisseur_selectionne ? batisseur_rouge_selectionne : batisseur_rouge;
                    }

                    drawable.drawImage(image_batisseurs, position_case.x, position_case.y, taille_case, taille_case, null);
                }
            }
        }
        if (jeu.getSituation() == DEPLACEMENT) {
            Image pas_joueur = jeu.getJoueur_en_cours().getNum_joueur() == JOUEUR1 ? pas_bleu : pas_rouge;

            for (Point case_autour : plateau.getCasesAccessibles(jeu.getBatisseur_en_cours())) {
                drawable.drawImage(pas_joueur, case_autour.y * taille_case, case_autour.x * taille_case, taille_case, taille_case, null);
            }
        } else if (jeu.getSituation() == CONSTRUCTION && !jeu.estJeufini()) {
            Image outil_joueur = jeu.getJoueur_en_cours().getNum_joueur() == JOUEUR1 ? outil_bleu : outil_rouge;

            for (Point constructions_autour : plateau.getConstructionsPossible(jeu.getBatisseur_en_cours())) {
                drawable.drawImage(outil_joueur, constructions_autour.y * taille_case, constructions_autour.x * taille_case, taille_case, taille_case, null);
            }
        }
    }

    public int getTailleCase() {
        return taille_case;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

}
