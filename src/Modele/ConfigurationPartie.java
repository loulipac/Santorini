package Modele;

public class ConfigurationPartie {
    int iaMode1;
    int iaMode2;

    int indexJoueurCommence;

    boolean joueur1Bleu;

    public ConfigurationPartie(int iaMode1, int iaMode2) {
        this.iaMode1 = iaMode1;
        this.iaMode2 = iaMode2;
        this.indexJoueurCommence = 0;
        this.joueur1Bleu = true;
    }

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
