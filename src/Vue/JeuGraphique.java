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
    protected int taille_case;
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
    private final Image batisseur_bleu_transparent;
    private final Image batisseur_rouge_transparent;
    private final Image pas_rouge;
    private final Image pas_rouge_hover;
    private final Image pas_bleu;
    private final Image pas_bleu_hover;
    private final Image etage_1_tmp;
    private final Image etage_1_tmp_transparent;
    private final Image etage_2_tmp;
    private final Image etage_2_tmp_transparent;
    private final Image etage_3_tmp;
    private final Image etage_3_tmp_transparent;
    private final Image coupole_tmp;
    private final Image coupole_tmp_transparent;
    private Point case_sous_souris;
    int numJoueurBleu;

    /**
     * Constructeur de JeuGraphique, charge les images en mémoire.
     */
    public JeuGraphique(Jeu j) {
        this.jeu = j;
        this.numJoueurBleu = (j.getConfigurationPartie().isJoueur1Bleu() ? JOUEUR1 : JOUEUR2);
        case_claire = Utile.readImage(CHEMIN_RESSOURCE + "/cases/case_claire.png");
        case_fonce = Utile.readImage(CHEMIN_RESSOURCE + "/cases/case_fonce.png");
        coupole = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/coupole.png");
        coupole_tmp = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/coupole_tmp.png");
        coupole_tmp_transparent = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/coupole_tmp_transparent.png");
        etage_1 = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_1.png");
        etage_1_tmp = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_1_tmp.png");
        etage_1_tmp_transparent = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_1_tmp_transparent.png");
        etage_2 = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_2.png");
        etage_2_tmp = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_2_tmp.png");
        etage_2_tmp_transparent = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_2_tmp_transparent.png");
        etage_3 = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_3.png");
        etage_3_tmp = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_3_tmp.png");
        etage_3_tmp_transparent = Utile.readImage(CHEMIN_RESSOURCE + "/Etages/etage_3_tmp_transparent.png");
        batisseur_bleu = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_bleu.png");
        batisseur_rouge = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_rouge.png");
        batisseur_rouge_selectionne = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_rouge_selectionne.png");
        batisseur_bleu_selectionne = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_bleu_selectionne.png");
        batisseur_rouge_transparent = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_rouge_transparent.png");
        batisseur_bleu_transparent = Utile.readImage(CHEMIN_RESSOURCE + "/batisseur/batisseur_bleu_transparent.png");
        pas_rouge = Utile.readImage(CHEMIN_RESSOURCE + "/icone/pas_rouge.png");
        pas_rouge_hover = Utile.readImage(CHEMIN_RESSOURCE + "/icone/pas_rouge_hover.png");
        pas_bleu = Utile.readImage(CHEMIN_RESSOURCE + "/icone/pas_bleu.png");
        pas_bleu_hover = Utile.readImage(CHEMIN_RESSOURCE + "/icone/pas_bleu_hover.png");
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

                    if (plateau.getTypeBatisseurs(position) == numJoueurBleu) {
                        image_batisseurs = batisseur_selectionne ? batisseur_bleu_selectionne : batisseur_bleu;
                    } else {
                        image_batisseurs = batisseur_selectionne ? batisseur_rouge_selectionne : batisseur_rouge;
                    }

                    drawable.drawImage(image_batisseurs, position_case.x, position_case.y, taille_case, taille_case, null);
                }
            }
        }
        if(jeu.getSituation() == PLACEMENT && case_sous_souris != null) {
            Image batisseur = jeu.getJoueur_en_cours().getNum_joueur() == numJoueurBleu ? batisseur_bleu_transparent : batisseur_rouge_transparent;
            if(case_sous_souris != null && plateau.estLibre(new Point(case_sous_souris.y, case_sous_souris.x))) {
                drawable.drawImage(batisseur, case_sous_souris.x * taille_case, case_sous_souris.y * taille_case, taille_case, taille_case, null);
            }
        } else if (jeu.getSituation() == DEPLACEMENT) {
            Image pas_joueur = jeu.getJoueur_en_cours().getNum_joueur() == numJoueurBleu ? pas_bleu : pas_rouge;
            Image pas_joueur_hover = jeu.getJoueur_en_cours().getNum_joueur() == numJoueurBleu ? pas_bleu_hover : pas_rouge_hover;

            for (Point case_autour : plateau.getCasesAccessibles(jeu.getBatisseur_en_cours())) {
                if (case_sous_souris != null && new Point(case_sous_souris.y, case_sous_souris.x).equals(case_autour)) {
                    drawable.drawImage(pas_joueur_hover, case_autour.y * taille_case, case_autour.x * taille_case, taille_case, taille_case, null);
                } else {
                    drawable.drawImage(pas_joueur, case_autour.y * taille_case, case_autour.x * taille_case, taille_case, taille_case, null);
                }
            }
        } else if (jeu.getSituation() == CONSTRUCTION && !jeu.estJeufini()) {
            for (Point constructions_autour : plateau.getConstructionsPossible(jeu.getBatisseur_en_cours())) {
                if (case_sous_souris != null && new Point(case_sous_souris.y, case_sous_souris.x).equals(constructions_autour)) {
                    setPreviewBatiment(drawable, constructions_autour, etage_1_tmp, etage_2_tmp, etage_3_tmp, coupole_tmp);
                } else {
                    setPreviewBatiment(drawable, constructions_autour, etage_1_tmp_transparent, etage_2_tmp_transparent, etage_3_tmp_transparent, coupole_tmp_transparent);
                }
            }
        }
    }

    /**
     * Affiche sur la grille la preview du bâtiment à construire.
     * @param constructions_autour position sur la grille où dessiner la preview
     */
    private void setPreviewBatiment(Graphics2D drawable, Point constructions_autour, Image etage_1_tmp_transparent, Image etage_2_tmp_transparent, Image etage_3_tmp_transparent, Image coupole_tmp_transparent) {
        Image batiment;
        switch (plateau.getTypeBatiments(constructions_autour)) {
            case VIDE -> batiment = etage_1_tmp_transparent;
            case RDC -> batiment = etage_2_tmp_transparent;
            case ETAGE -> batiment = etage_3_tmp_transparent;
            case TOIT -> batiment = coupole_tmp_transparent;
            default -> batiment = null;
        }
        drawable.drawImage(batiment, constructions_autour.y * taille_case, constructions_autour.x * taille_case, taille_case, taille_case, null);
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

    public void setCase_sous_souris(Point case_sous_souris) {
        this.case_sous_souris = case_sous_souris;
    }

}
