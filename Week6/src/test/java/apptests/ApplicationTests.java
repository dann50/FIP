package apptests;

import beans.Cat;
import beans.Parrot;
import beans.Person;
import config.ProjectConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ContextConfiguration(classes = { ProjectConfig.class })
@ExtendWith(SpringExtension.class)
class ApplicationTests {

    private final String[] personNames = {"Alice", "Bob", "Charlie", "Damian", "Ella"};
    private final String[] parrotNames = {"Koko", "Joe", null};
    private final String[] catNames = {"Riki", "Moyo", "Dane"};

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Test that a Parrot instance is in the context, " +
        "and its name is in the list of names")
    void testParrotIsInContext() {
        Parrot p = context.getBean(Parrot.class);
        Assertions.assertTrue(contains(parrotNames, p.getName()));
    }

    @Test
    @DisplayName("Test that a Cat instance is in the context, " +
        "and its name is in the list of names")
    void testCatIsInContext() {
        Cat c = context.getBean(Cat.class);
        Assertions.assertTrue(contains(catNames, c.getName()));
    }

    @Test
    @DisplayName("Test that a Person instance is in the context, " +
        "and its name is in the list of names")
    void testPersonIsInContext() {
        Person p = context.getBean(Person.class);
        Assertions.assertTrue(contains(personNames, p.getName()));
    }

    boolean contains(String[] names, String name) {
        for (String n : names) {
            if (n == null && name == null) {
                return true;
            } else if (n.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
