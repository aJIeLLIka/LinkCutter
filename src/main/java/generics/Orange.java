package generics;

public class Orange extends Fruit {
    private final String name;

    public Orange(String name, float weight) {
        super(weight);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
