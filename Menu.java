package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    public static void start() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
        int option = scanner.nextInt();
        switch (option) {
            case 0 ->
                System.exit(0);
            case 1 -> ManagerInterface.start();
            case 3 -> {
                System.out.println("Enter the customer name:");
                scanner.nextLine();
                String customerName = scanner.nextLine();
                customerDAOImpl.insert(new Customer(customerDAOImpl.getId(), customerName, null));
                System.out.println("The customer was added!");
                System.out.println();
                Menu.start();
            }
            case 2 -> {
                List<Customer> customerList = customerDAOImpl.getAll();
                if (customerList.isEmpty()) {
                    System.out.println("The customer list is empty!");
                    Menu.start();
                } else {
                    System.out.println("Choose a customer:");
                    customerList.stream()
                            .forEach(Customer::toString);
                    int customerId = scanner.nextInt();
                    CustomerInterface.start(customerId);
                }
            }
        }


    }
}
