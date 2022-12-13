package pl.kurs.geometricfigures.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Circle extends Shape {
    @Column(nullable = false)
    @Positive(message = "Radius should be positive value")
    private double radius;

    @Override
    public Double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

}
