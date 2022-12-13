package pl.kurs.geometricfigures.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@DiscriminatorValue(value = "square")
public class Square extends Shape {
    @Column(nullable = false)
    private double width;

    @Override
    public Double getArea() {
        return width*width;
    }

    @Override
    public double getPerimeter() {
        return 4*width;
    }
}
