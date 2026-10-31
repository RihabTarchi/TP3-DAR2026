package serverPackage;

public class Compteur {
    private static int total = 0;

    // synchronized : un seul thread à la fois entre dans cette méthode
    public static synchronized int incrementer() {
        total++;
        return total;
    }

    public static synchronized int getTotal() {
        return total;
    }
}