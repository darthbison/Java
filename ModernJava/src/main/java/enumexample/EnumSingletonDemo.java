package enumexample;

public class EnumSingletonDemo {
    public void runExample() {
        EnumSingletonExample ese = EnumSingletonExample.INSTANCE;
        System.out.println(ese.getNum());
        ese.setNum(5);
        System.out.println(ese.getNum());
    }
}
