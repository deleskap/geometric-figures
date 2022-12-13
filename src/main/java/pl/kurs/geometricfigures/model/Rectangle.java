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
//@DiscriminatorValue(value = "rectangle")
public class Rectangle extends Shape{
    @Column(nullable = false)
    private double width;
    @Column(nullable = false)
    private double height;

    @Override
    public Double getArea() {
        return width*height;
    }

    @Override
    public double getPerimeter() {
        return 2*(width+height);
    }


}
