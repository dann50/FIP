package beans;

import java.util.Random;

public class Cat {

    private String name;
    static final String[] names = {"Riki", "Moyo", "Dane"};

    public String getName() {
        return name;
    }

    public void setRandomName() {
        int i = new Random().nextInt(names.length);
        this.name = names[i];
    }

    @Override
    public String toString() {
        return "Cat(name=" + name + ")";
    }
}
