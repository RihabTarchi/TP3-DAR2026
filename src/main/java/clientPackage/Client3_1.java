package clientPackage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client3_1 {

    public static void main(String[] args) {

        String adresseServeur = "127.0.0.1";

        try (Socket socket = new Socket(adresseServeur, 1234);
             BufferedReader entree = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()))) {

            // Lire le message envoyé par le serveur
            String message = entree.readLine();

            System.out.println("Réponse du serveur : " + message);

        } catch (Exception e) {
            System.out.println(
                    "Erreur de connexion : " + e.getMessage()
            );
        }
    }
}
