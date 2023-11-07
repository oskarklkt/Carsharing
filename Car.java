package carsharing;

public class Car {
    private Integer id;
    private String name;
    private int company_id;

    public Car(Integer id, String name, int company_id) {
        this.id = id;
        this.name = name;
        this.company_id = company_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompany_id() {
        return company_id;
    }

    @Override
    public String toString() {
        System.out.println("%d. %s".formatted(id, name));
        return "a";
    }
}
