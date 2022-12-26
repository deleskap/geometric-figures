package pl.kurs.geometricfigures.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Circle extends Shape {
    @Positive
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public Double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

}
