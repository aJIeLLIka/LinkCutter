package generics;

public class GenericMain {
    public static void main(String[] args) {

        Apple apl1 = new Apple("apple1", 1);
        Apple apl2 = new Apple("apple2", 1);
        Apple apl3 = new Apple("apple3", 1);
        Apple apl4 = new Apple("apple4", 1);
        Orange orange1 = new Orange("orange1", 1.5f);
        Orange orange2 = new Orange("orange2", 1.5f);

        Box<Apple> appleBox = new Box();
        Box<Apple> appleBox2 = new Box();
        Box<Orange> orangeBox = new Box();

        appleBox.addFruit(apl1);
        appleBox.addFruit(apl2);
        appleBox2.addFruit(apl3);
        appleBox2.addFruit(apl4);

        orangeBox.addFruit(orange1);
        orangeBox.addFruit(orange2);

        System.out.println(appleBox.getBoxWeight());
        System.out.println(appleBox2.getBoxWeight());
        appleBox.addFruitsFromOtherBox(appleBox2);

        System.out.println(appleBox.getBoxWeight());
        System.out.println(appleBox2.getBoxWeight());
        System.out.println(appleBox.compare(orangeBox));
    }
}
