package IO;

import Modele.Jeu;
import Vue.HostPanel;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static Modele.Constante.JOUEUR1;
import static Modele.Constante.JOUEUR2;

public class Server extends IO {
    static final int PORT = 2121;
    private ServerSocket serverSocket;
    int num_player = JOUEUR1;
    Thread thread;
    HostPanel hp;
    ObjectOutputStream send_object;
    Jeu jeu;
    private boolean set_local_change = false;

    public boolean isSet_local_change() {
        return set_local_change;
    }

    @Override
    public int getNum_player() {
        return num_player;
    }

    public Server(HostPanel hp) {
        this.hp = hp;
        connect();

    }

    public String getIPAddress() {
        String ip = "";
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }


    public void setActionLocal(Message _message) {
        set_local_change = true;
        jeu.jouer((Point) _message.getContenu());
        set_local_change = false;
    }
    @Override
    public void connect() {
        System.out.println("Server starting");
        try {
            serverSocket = new ServerSocket(PORT);
            thread = new Thread(new ServerThread(serverSocket));
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
        thread.interrupt();
        try {
            serverSocket.close();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void analyserMessage(Message _message) {
        synchronized (this) {
            switch (_message.getCode()) {
                case Message.DECO -> deconnexion();
                case Message.MOVE -> setActionLocal(_message);
                default -> System.out.println("Unknown code operation.");
            }
        }
    }

    @Override
    public void startPartie() {
        synchronized (this) {
            if(serverSocket != null) {
                try {
                    send_object.writeObject(new Message(Message.START));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }


    private class ServerThread implements Runnable {

        private Socket socket;
        private ServerSocket serverSocket;
        ObjectInputStream in_object;

        public ServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            try {
                socket = serverSocket.accept();
                send_object = new ObjectOutputStream(socket.getOutputStream());
                in_object = new ObjectInputStream(socket.getInputStream());
                Message result;
                while((result = (Message) in_object.readObject()) != null) {
                    analyserMessage(result);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
