package IO;

import Modele.Jeu;
import Vue.NetworkPanel;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static Modele.Constante.*;

public class Client extends IO {
    String ip;
    int num_player = JOUEUR2;
    private Socket clientSocket;
    Thread thread;
    ObjectOutputStream send_object;
    NetworkPanel np;
    Jeu jeu;

    private boolean set_local_change = false;

    public boolean isSet_local_change() {
        return set_local_change;
    }

    @Override
    public int getNum_player() {
        return num_player;
    }

    public Client(String ip, NetworkPanel np) {
        this.np = np;
        this.ip = ip;
        connect();
    }

    @Override
    public void connect() {
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
    public void sendAction(Point action) {
        synchronized (this) {
            try {
                Message m = new Message(Message.MOVE, action);
                send_object.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
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
                    send_object.writeObject(deconnexion);
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
                case Message.START -> startPartie();
                case Message.MOVE -> setActionLocal(_message);
                default -> System.out.println("Unknown code operation.");
            }
        }
    }

    public void setActionLocal(Message _message) {
        set_local_change = true;
        jeu.jouer((Point) _message.getContenu());
        set_local_change = false;
    }

    @Override
    public void startPartie() {
        np.startGame();
    }

    @Override
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    private class ClientThread implements Runnable {

        private Socket socket;
        ObjectInputStream reader_object;

        public ClientThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("Client " + ip + " connected to server.");
                send_object = new ObjectOutputStream(socket.getOutputStream());
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
