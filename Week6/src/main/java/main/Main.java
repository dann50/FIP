package main;

import beans.Person;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Person[] persons = new Person[3];
        for (int i = 0; i < persons.length; i++) {
            Person person = context.getBean(Person.class);
            System.out.println(person);
        }
    }
}
