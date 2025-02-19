package config;

import beans.Cat;
import beans.Parrot;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = {"beans"})
public class ProjectConfig {

    private final String[] names = {"Alice", "Bob", "Charlie", "Damian", "Ella"};

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Parrot parrot() {
        Parrot parrot = new Parrot();
        parrot.setRandomName();
        return parrot;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Cat cat() {
        Cat cat = new Cat();
        cat.setRandomName();
        return cat;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public String name() {
        int i = new Random().nextInt(names.length);
        return names[i];
    }
}
