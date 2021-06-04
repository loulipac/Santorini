package IO;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static Modele.Constante.*;

public class Client extends IO {
    private Socket clientSocket;
    private final String ip;

    public Client(String ipHote, String username) {
        super(username, JOUEUR2);
        this.ip = ipHote;
        connexion();
    }

    @Override
    public void connexion() {
        try {
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(ip, PORT));

            thread = new Thread(new ClientThread(clientSocket));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void analyserMessage(Message _message) {
        synchronized (this) {
            switch (_message.getCode()) {
                case Message.DECO -> {
                    thread.interrupt();
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case Message.START -> demarrerPartie();
                case Message.MOVE -> jouerCoupLocal((Point) _message.getContenu());
                case Message.UNAME -> setAdversaireNom((String) _message.getContenu());
                default -> System.out.println("Unknown code operation.");
            }
        }
    }

    @Override
    public void deconnexion() {
        synchronized (this) {
            if (clientSocket != null) {
                System.out.println("Asking for deconnection.");
                Message deconnexion = new Message(Message.DECO);
                try {
                    streamEnvoie.writeObject(deconnexion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            thread.interrupt();
            try {
                clientSocket.close();
                System.out.println("Successfully disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void demarrerPartie() {
        lobby.startClientGame();
    }

    @Override
    public void envoieCoup(Point coup) {
        synchronized (this) {
            try {
                streamEnvoie.writeObject(new Message(Message.MOVE, coup));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void envoyerNomUtilisateur() {
        synchronized (this) {
            if (clientSocket != null) {
                try {
                    streamEnvoie.writeObject(new Message(Message.UNAME, nomJoueur));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setAdversaireNom(String nom) {
        if (nom != null && lobby != null) {
            nomAdversaire = nom;
            lobby.setAdversaireNom(nom);
        }
    }

    /**
     * Envoie au serveur, que le client est prêt ou non.
     * @param status si le client est prêt ou non
     */
    public void sendReady(boolean status) {
        synchronized (this) {
            if (clientSocket != null) {
                try {
                    streamEnvoie.writeObject(new Message(Message.RDY, status));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Classe gérant la réception des messages venant du serveur.
     */
    private class ClientThread implements Runnable {

        private final Socket socket;
        ObjectInputStream reader_object;

        public ClientThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                streamEnvoie = new ObjectOutputStream(socket.getOutputStream());
                reader_object = new ObjectInputStream(socket.getInputStream());
                Message result;
                while ((result = (Message) reader_object.readObject()) != null) {
                    analyserMessage(result);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
