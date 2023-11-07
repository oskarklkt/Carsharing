package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerInterface {
    public static void start(int customerId) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        CompanyDAOImpl companyDAOImpl = new CompanyDAOImpl();
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        CarDAOImpl carDAOImpl = new CarDAOImpl();
        System.out.println( "1. Rent a car\n" +
                            "2. Return a rented car\n" +
                            "3. My rented car\n" +
                            "0. Back");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> customerDAOImpl.rentACar(customerId);
            case 2 -> customerDAOImpl.returnCar(customerId);
            case 0 -> Menu.start();
            case 3 -> customerDAOImpl.showRentedCar(customerId);
        }
    }
}
