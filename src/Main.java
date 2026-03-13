import model.Rol;
import service.AuthService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();

        while (true) {

            System.out.println("=== Caffeine Manager ===");
            System.out.print("Username: ");
            String user = sc.nextLine();

            System.out.print("Parola: ");
            String pass = sc.nextLine();

            if (auth.login(user, pass)) {
                System.out.println("Login reușit!");

                if (auth.getRol() == Rol.ADMIN) {
                    meniuAdmin(sc, auth);
                } else {
                    meniuAngajat(sc, auth);
                }
            } else {
                System.out.println("Date greșite!");
            }
        }
    }

    static void meniuAdmin(Scanner sc, AuthService auth) {
        while (auth.esteLogat()) {
            System.out.println("\n MENIU ADMIN ");
            System.out.println("1. Logout");
            System.out.print("Alege: ");

            int opt = Integer.parseInt(sc.nextLine());

            if (opt == 1) {
                auth.logout();
                System.out.println("Logout reușit.");
            }
        }
    }

    static void meniuAngajat(Scanner sc, AuthService auth) {
        while (auth.esteLogat()) {
            System.out.println("\nMENIU ANGAJAT ");
            System.out.println("1. Logout");
            System.out.print("Alege: ");

            int opt = Integer.parseInt(sc.nextLine());

            if (opt == 1) {
                auth.logout();
                System.out.println("Logout reușit.");
            }
        }
    }
}
