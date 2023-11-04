package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ManagerInterface {
    public static void start() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
        CompanyDAOImpl companyDAO = new CompanyDAOImpl();

        int option = scanner.nextInt();
        System.out.println();
        switch (option) {
            case 0 -> Menu.start();
            case 1 -> {
                List<Company> companyList = companyDAO.getAll();
                if (!companyList.isEmpty()) {
                    System.out.println("Company list:");
                    companyDAO.getAll().forEach(Company::toString);
                    ManagerInterface.start();
                } else {
                    System.out.println("The company list is empty!");
                    System.out.println();
                    ManagerInterface.start();
                }
            }
            case 2 -> {
                System.out.println("Enter the company name:");
                scanner.nextLine();
                String companyName = scanner.nextLine();
                companyDAO.insert(new Company(companyDAO.getId(), companyName));
                System.out.println("The company was created!");
                System.out.println();
                ManagerInterface.start();
            }
        }
    }
}
