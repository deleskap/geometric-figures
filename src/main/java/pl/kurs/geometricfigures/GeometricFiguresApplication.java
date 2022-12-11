package pl.kurs.geometricfigures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.kurs.geometricfigures.controller.ShapeController;

@SpringBootApplication
public class GeometricFiguresApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeometricFiguresApplication.class, args);

    }

}
