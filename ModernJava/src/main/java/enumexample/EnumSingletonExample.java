package enumexample;

public enum EnumSingletonExample {
    INSTANCE;

    int num;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
