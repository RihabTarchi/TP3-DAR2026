package serverPackage;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMT3_2 {
    public static void main(String[] args) {
        int port = 1234;
        int numero = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur calculatrice démarré sur le port " + port);
            System.out.println("En attente de clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                numero++;
                System.out.println("Client n°" + numero + " connecté depuis "
                        + socket.getRemoteSocketAddress());
                new ClientProcess3_2(socket, numero).start();   // un thread par client
            }
        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
