package Reseau;

import java.io.Serializable;

/**
 * Classe Message envoyé entre client et serveur, composé d'un code et d'un contenu variable (boolean, string ou même objets divers)
 */
public class Message implements Serializable {
    /**
     * Code de déconnexion
     */
    static final int DECO = -1;

    /**
     * Code de coup à envoyer
     */
    static final int MOVE = 1;

    /**
     * Code de jeu à démarrer
     */
    static final int START = 0;

    /**
     * Code d'envoie du nom du joueur
     */
    static final int UNAME = 2;

    /**
     * Code d'envoie du statut du client (prêt ou non)
     */
    static final int RDY = 3;

    private final int code;
    private Object contenu;

    /**
     * Code d'envoie de commande complexe ayant du contenu (MOVE avec position du coup)
     * @param code code de la commande
     * @param contenu contenu du message à envoyer
     */
    public Message(int code, Object contenu) {
        this.code = code;
        this.contenu = contenu;
    }

    /**
     * Envoie de commande simple (DECO, START)
     * @param code code de la commande
     */
    public Message(int code) {
        this.code = code;
    }

    // GETTER

    public int getCode() {
        return code;
    }

    public Object getContenu() {
        return contenu;
    }
}
