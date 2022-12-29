package generics;

public class Orange extends Fruit{
    private String name;

    public Orange(String name, float weight) {
        super(weight);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
