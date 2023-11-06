package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CompanyInterface {

    public static void start(int companyNumber) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        CompanyDAOImpl companyDAOImpl = new CompanyDAOImpl();
        CarDAOImpl carDAOImpl = new CarDAOImpl();
        Company company = companyDAOImpl.get(companyNumber);
        List<Car> companyCars = carDAOImpl.getCarListByCompanyId(companyNumber);
        System.out.println("'" + company.getName() + "'" +" company");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0 -> ManagerInterface.start();
            case 1 -> {
                if (companyCars.isEmpty()) {
                    System.out.println("The car list is empty!");
                    System.out.println();
                    CompanyInterface.start(companyNumber);
                }
                else {
                    int counter = 1;
                    for (Car car : companyCars) {
                        System.out.printf("%d. %s", counter, car.getName());
                        counter++;
                    }
                    counter = 1;
                    System.out.println();
                    CompanyInterface.start(companyNumber);
                }
            }
            case 2 -> {
                System.out.println("Enter the car name:");
                scanner.nextLine();
                String carName = scanner.nextLine();
                Car newCar = new Car(carDAOImpl.getId(), carName, companyNumber);
                carDAOImpl.insert(newCar);
                System.out.println("The car was added!");
                System.out.println();
                CompanyInterface.start(companyNumber);
            }
        }
    }
}
