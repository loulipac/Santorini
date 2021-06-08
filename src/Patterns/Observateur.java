package Patterns;

/**
 * Interface pour mettre à jour différentes vues depuis le modèle
 */
public interface Observateur {
    /**
     * L'appel de cette fonction met à jour les vues qui implémente l'interface lorsqu'elle est appellée
     */
    void miseAjour();
}
