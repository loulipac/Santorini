package IO;

import Modele.Jeu;

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
    Jeu jeu;
    private boolean set_local_change = false;

    @Override
    public int getNum_player() {
        return num_player;
    }

    public Client(String ip, String username) {
        this.username = username;
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
                case Message.UNAME -> setAdversaireNom((String) _message.getContenu());
                default -> System.out.println("Unknown code operation.");
            }
        }
    }

    public void sendReady(boolean status) {
        System.out.println("Sending rdy");
        synchronized (this) {
            if (clientSocket != null) {
                try {
                    send_object.writeObject(new Message(Message.RDY, status));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void sendName() {
        System.out.println("Sending name");
        synchronized (this) {
            if (clientSocket != null) {
                Message uname = new Message(Message.UNAME, username);
                System.out.println(username);
                try {
                    send_object.writeObject(uname);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        lobby.startClientGame();
    }

    @Override
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public boolean isSet_local_change() {
        return set_local_change;
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
