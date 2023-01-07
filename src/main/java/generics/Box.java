package generics;

import java.util.ArrayList;
import java.util.Optional;

public class Box<T extends Fruit> {

    public ArrayList<T> content = new ArrayList<>();

    public void addFruit(T fruit) {
        this.content.add(fruit);
    }

    public float getBoxWeight() {
        return content.stream()
                .map(Fruit::getWeight)
                .reduce(Float::sum)
                .orElse(Float.NaN);
    }

    public boolean compare(Box compairedBox) {
        return this.getBoxWeight() == compairedBox.getBoxWeight();
    }

    public void addFruitsFromOtherBox(Box<T> box) {
        this.content.addAll(box.content);
        box.content.clear();
    }

}
