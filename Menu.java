package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static void start() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
        int option = scanner.nextInt();
        switch (option) {
            case 0 ->
                System.exit(0);
            case 1 -> ManagerInterface.start();
        }


    }
}
