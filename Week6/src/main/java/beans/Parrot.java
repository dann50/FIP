package beans;

import java.util.Random;

public class Parrot {

    private String name;
    static final String[] names = {"Koko", "Joe", null};

    public String getName() {
        return name;
    }

    public void setRandomName() {
        int i = new Random().nextInt(names.length);
        this.name = names[i];
    }

    @Override
    public String toString() {
        return "Parrot(name=" + name + ")";
    }
}
