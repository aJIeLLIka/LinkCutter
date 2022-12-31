package generics;

public class Apple extends Fruit {

    private final String name;

    public Apple(String name, float weight) {
        super(weight);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
