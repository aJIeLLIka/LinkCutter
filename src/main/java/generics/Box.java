package generics;

import java.util.ArrayList;
import java.util.Optional;

public class Box <T extends Fruit>{

    public ArrayList<T> content;
    public Box() {
        content = new ArrayList();
    }

    public void addFruit(T fruit){
        this.content.add(fruit);
    }

    public float getBoxWeight(){
        Optional<Float> weight = content.stream()
                .map(fruit -> fruit.getWeight())
                .reduce((a, b) -> a + b);
        if(weight.isPresent()) {
            return weight.get();
        }
        else {
            return 0;
        }
    }

    public boolean compare(Box box){
        return getBoxWeight() == box.getBoxWeight();
    }

    public void addOtherBox(Box<T> box){
        this.content.addAll(box.content);
        box.content.clear();
    }

    public void swapFruits(int firstFruitindex, int secondFruitIndex){
//        T fruit = new T();
    }

}
