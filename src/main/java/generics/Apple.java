package generics;

public class Apple extends Fruit{

    private String name;

    public Apple(String name, float weight) {
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
