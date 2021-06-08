package Utile;

/**
 * Classe de configuration partie pour pouvoir garder des données en une seule classe entre différentes vues et modèles
 * et ainsi alléger les constructeurs.
 * @see Vue.PanelOptions
 * @see Vue.PanelChoix
 * @see Vue.PanelPlateau
 * @see Modele.Jeu
 */
public class ConfigurationPartie {
    private final int iaMode1;
    private final int iaMode2;
    private int indexJoueurCommence;
    private boolean joueur1Bleu;

    public ConfigurationPartie(int iaMode1, int iaMode2) {
        this.iaMode1 = iaMode1;
        this.iaMode2 = iaMode2;
        this.indexJoueurCommence = 0;
        this.joueur1Bleu = true;
    }

    // GETTER / SETTER

    public boolean isJoueur1Bleu() {
        return joueur1Bleu;
    }

    public void setJoueur1Bleu(boolean joueur1Bleu) {
        this.joueur1Bleu = joueur1Bleu;
    }

    public void setIndexJoueurCommence(int indexJoueurCommence) {
        this.indexJoueurCommence = indexJoueurCommence;
    }

    public int getIaMode1() {
        return iaMode1;
    }

    public int getIaMode2() {
        return iaMode2;
    }

    public int getIndexJoueurCommence() {
        return indexJoueurCommence;
    }
}
