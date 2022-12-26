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
public class Rectangle extends Shape {
    @Positive
    private double width;
    @Positive
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

}
