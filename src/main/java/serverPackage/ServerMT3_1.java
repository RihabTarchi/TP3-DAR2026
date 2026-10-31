
package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerMT3_1 {

    public static final int PORT = 1234;

    // Compteur partagé entre les connexions
    private static final AtomicInteger compteurClients =
            new AtomicInteger(0);

    public static void main(String[] args) {

        try (ServerSocket serveur = new ServerSocket(PORT)) {

            System.out.println("Serveur démarré sur le port " + PORT);
            System.out.println("En attente de clients...");

            while (true) {

                // Attendre et accepter un nouveau client
                Socket socketClient = serveur.accept();

                // Attribuer un numéro unique
                int numero = compteurClients.incrementAndGet();

                // Afficher les informations du client
                System.out.println(
                        "Client n°" + numero
                                + " connecté depuis "
                                + socketClient.getRemoteSocketAddress()
                );

                // Créer un thread indépendant
                Thread thread = new Thread(
                        new ClientProcess3_1(socketClient, numero)
                );

                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
