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
public class Square extends Shape {
    @Positive
    private double width;

    public Square(double width) {
        this.width = width;
    }

    @Override
    public Double getArea() {
        return width * width;
    }

    @Override
    public double getPerimeter() {
        return 4 * width;
    }

}
