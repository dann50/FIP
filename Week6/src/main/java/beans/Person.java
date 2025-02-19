package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Person {

    private String name;
    private Cat cat;
    @Autowired
    private Parrot parrot;  // Field injection

    @Autowired   // Constructor injection
    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    @Autowired // Setter injection
    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Parrot getParrot() {
        return parrot;
    }

    public void setParrot(Parrot parrot) {
        this.parrot = parrot;
    }

    @Override
    public String toString() {
        return "Person(name=" + name + ", parrot=" + parrot + ", cat=" + cat + ")";
    }
}
