package pl.kurs.geometricfigures.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;
import java.util.Map;

@Getter
@Setter
@Entity
//@AllArgsConstructor
@NoArgsConstructor
public class Rectangle extends Shape {
    @Positive(message = "Width should be positive value")
    private double width;
    @Positive(message = "Height should be positive value")
    private double height;

    public Rectangle(double width,double height) {
        this.width = width;
        this.height = height;
        super.setType("RECTANGLE");
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
