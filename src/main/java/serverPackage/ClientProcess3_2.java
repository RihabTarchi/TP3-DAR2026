package serverPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.Socket;

public class ClientProcess3_2 extends Thread {
    private Socket socket;
    private int numero;

    public ClientProcess3_2(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try {
            // Ordre important : OutputStream + flush, puis InputStream
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Envoi du numéro d'ordre au client
            oos.writeObject("Bienvenue ! Vous êtes le client n°" + numero);
            oos.flush();

            while (true) {
                Object obj = ois.readObject();

                // Demande de fin de session
                if (obj instanceof String && ((String) obj).equalsIgnoreCase("bye")) {
                    oos.writeObject("Au revoir client n°" + numero);
                    oos.flush();
                    break;
                }

                if (obj instanceof Operation) {
                    Operation op = (Operation) obj;
                    String resultat = calculer(op);

                    // Accès synchronisé au compteur global
                    int n = Compteur.incrementer();
                    System.out.println("[Client n°" + numero + "] " + op + " = " + resultat
                            + "  | Opérations traitées au total : " + n);

                    oos.writeObject(resultat);
                    oos.flush();
                }
            }
        } catch (EOFException e) {
            System.out.println("Client n°" + numero + " a fermé la connexion.");
        } catch (Exception e) {
            System.out.println("Erreur client n°" + numero + " : " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Client n°" + numero + " déconnecté.");
        }
    }

    private String calculer(Operation op) {
        double a = op.getOperande1();
        double b = op.getOperande2();
        switch (op.getOperateur()) {
            case '+': return String.valueOf(a + b);
            case '-': return String.valueOf(a - b);
            case '*': return String.valueOf(a * b);
            case '/':
                if (b == 0) return "Erreur : division par zéro";
                return String.valueOf(a / b);
            default:
                return "Erreur : opérateur inconnu";
        }
    }
}