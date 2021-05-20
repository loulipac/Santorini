package Vue;

import Modele.Plateau;
import Modele.Jeu;
import static Modele.Constante.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

/**
 * Classe dessinant les cases du plateau.
 */
public class JeuGraphique extends JComponent {
    private Plateau plateau;
    private Jeu jeu;
    private int largeur;
    private int hauteur;
    private int taille_case;
    private final Image case_claire, case_fonce, coupole, etage_1, etage_2, etage_3, batisseur_bleu, batisseur_rouge, batisseur_rouge_selectionne, batisseur_bleu_selectionne, pas_rouge, pas_bleu, outil_bleu, outil_rouge;

    /**
     * Constructeur de JeuGraphique, charge les images en mémoire.
     *
     * @param j
     */
    public JeuGraphique(Jeu j) {
        this.jeu = j;
        case_claire = readImage("src/Ressources/cases/case_claire.png");
        case_fonce = readImage("src/Ressources/cases/case_fonce.png");
        coupole = readImage("src/Ressources/Etages/coupole.png");
        etage_1 = readImage("src/Ressources/Etages/etage_1.png");
        etage_2 = readImage("src/Ressources/Etages/etage_2.png");
        etage_3 = readImage("src/Ressources/Etages/etage_3.png");
        batisseur_bleu = readImage("src/Ressources/batisseur/batisseur_bleu.png");
        batisseur_rouge = readImage("src/Ressources/batisseur/batisseur_rouge.png");
        batisseur_rouge_selectionne = readImage("src/Ressources/batisseur/batisseur_rouge_selectionne.png");
        batisseur_bleu_selectionne = readImage("src/Ressources/batisseur/batisseur_bleu_selectionne.png");
        pas_rouge = readImage("src/Ressources/icone/pas_rouge.png");
        pas_bleu = readImage("src/Ressources/icone/pas_bleu.png");
        outil_bleu = readImage("src/Ressources/icone/outil" +
                "_bleu.png");
        outil_rouge = readImage("src/Ressources/icone/outil_rouge.png");
        plateau = j.getPlateau();
    }

    /**
     * Change une image en mémoire depuis son nom.
     *
     * @param _name
     * @return l'image chargé.
     */
    public static Image readImage(String _name) {
        try {
            return ImageIO.read(new FileInputStream(_name));
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        Graphics2D drawable = (Graphics2D) g;
        //setBorder(new LineBorder(Color.RED));

        //largeur = getSize().width / plateau.getColonnes();
        hauteur = getSize().height / plateau.getLignes();
        //taille_case = Math.min(largeur, hauteur);
        taille_case = hauteur;

        // On efface tout
        //int nouvelle_origine = (getSize().width / 2) - (taille_case * plateau.getColonnes() / 2);
        int nouvelle_origine = 0;
        drawable.clearRect(
                nouvelle_origine,
                0,
                taille_case * plateau.getColonnes(),
                taille_case * plateau.getLignes() + nouvelle_origine
        );


        Point batisseur_en_cours = jeu.getBatisseur_en_cours();
        int batisseurs_ligne = batisseur_en_cours != null ? batisseur_en_cours.x : -1;
        int batisseurs_colonne = batisseur_en_cours != null ? batisseur_en_cours.y : -1;

        Image image_batisseurs, image_case, image_batiment;

        for (int l = 0; l < plateau.getLignes(); l++) {
            for (int c = 0; c < plateau.getColonnes(); c++) {

                Point position_case = new Point(
                        c * taille_case + nouvelle_origine,
                        l * taille_case
                );
                image_case = ((l + c) % 2 == 0) ? case_claire : case_fonce;
                drawable.drawImage(image_case, position_case.x, position_case.y, taille_case, taille_case, null);


                switch (plateau.getTypeBatiments(l, c)) {
                    case Plateau.RDC -> image_batiment = etage_1;
                    case Plateau.ETAGE -> image_batiment = etage_2;
                    case Plateau.TOIT -> image_batiment = etage_3;
                    case Plateau.COUPOLE -> image_batiment = coupole;
                    default -> image_batiment = null;
                }

                if (image_batiment != null)
                    drawable.drawImage(image_batiment, position_case.x, position_case.y, taille_case, taille_case, null);

                if (plateau.getTypeBatisseurs(l, c) > 0) {

                    boolean batisseur_selectionne = (batisseurs_ligne == l && batisseurs_colonne == c);

                    if (plateau.getTypeBatisseurs(l, c) == JOUEUR1) {
                        image_batisseurs = batisseur_selectionne ? batisseur_bleu_selectionne : batisseur_bleu;
                    } else {
                        image_batisseurs = batisseur_selectionne ? batisseur_rouge_selectionne : batisseur_rouge;
                    }

                    drawable.drawImage(image_batisseurs, position_case.x, position_case.y, taille_case, taille_case, null);
                }
            }
        }
        if (jeu.getSituation() == DEPLACEMENT) {
            Image pas_joueur = jeu.getJoueur_en_cours() == JOUEUR1 ? pas_bleu : pas_rouge;

            for (Point case_autour : plateau.getCasesAccessibles(jeu.getBatisseur_en_cours())) {
                drawable.drawImage(pas_joueur, case_autour.y * taille_case, case_autour.x * taille_case, taille_case, taille_case, null);
            }
        } else if (jeu.getSituation() == CONSTRUCTION && !jeu.estJeufini()) {
            Image outil_joueur = jeu.getJoueur_en_cours() == JOUEUR1 ? outil_bleu : outil_rouge;

            for (Point constructions_autour : plateau.getConstructionsPossible(jeu.getBatisseur_en_cours())) {
                drawable.drawImage(outil_joueur, constructions_autour.y * taille_case, constructions_autour.x * taille_case, taille_case, taille_case, null);
            }
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getTailleCase() {
        return taille_case;
    }

    public void setTailleCase(int taille_case) {
        this.taille_case = taille_case;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
}
