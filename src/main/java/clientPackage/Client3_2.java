package clientPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client3_2 {
    public static void main(String[] args) {

        // Pour tester en réseau : remplacer par l'IP du serveur, ex. "192.168.1.10"
        String adresseServeur = (args.length > 0) ? args[0] : "127.0.0.1";
        int port = 1234;

        try (Socket socket = new Socket();
             Scanner sc = new Scanner(System.in)) {

            socket.connect(new InetSocketAddress(InetAddress.getByName(adresseServeur), port), 5000);

            // Même ordre que le serveur : Output d'abord
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            System.out.println("Serveur : " + ois.readObject());

            while (true) {
                System.out.print("Opérande 1 (ou 'q' pour quitter) : ");
                String saisie = sc.nextLine();

                if (saisie.equalsIgnoreCase("q")) {
                    oos.writeObject("bye");
                    oos.flush();
                    System.out.println("Serveur : " + ois.readObject());
                    break;
                }

                try {
                    double a = Double.parseDouble(saisie);

                    System.out.print("Opérateur (+ - * /) : ");
                    char operateur = sc.nextLine().trim().charAt(0);

                    System.out.print("Opérande 2 : ");
                    double b = Double.parseDouble(sc.nextLine());

                    oos.writeObject(new Operation(a, operateur, b));
                    oos.flush();

                    System.out.println("Résultat : " + ois.readObject());
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.out.println("Saisie invalide, recommencez.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}