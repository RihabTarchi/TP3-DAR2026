package serverPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess3_1 implements Runnable {

    private final Socket socket;
    private final int numeroClient;

    public ClientProcess3_1(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    @Override
    public void run() {
        try (
                Socket s = socket;
                PrintWriter sortie = new PrintWriter(
                        s.getOutputStream(), true
                )
        ) {
            sortie.println(
                    "Bienvenue ! Vous êtes le client n°" + numeroClient
            );

            System.out.println(
                    "Message envoyé au client n°" + numeroClient
            );

        } catch (IOException e) {
            System.out.println(
                    "Erreur avec le client n°" + numeroClient
                            + " : " + e.getMessage()
            );
        }
    }
}