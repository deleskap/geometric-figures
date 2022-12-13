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
@AllArgsConstructor
@NoArgsConstructor
//@DiscriminatorValue(value = "square")
public class Square extends Shape {
    @Column(nullable = false)
    @Positive(message = "Width should be positive value")
    private double width;

    @Override
    public Double getArea() {
        return width * width;
    }

    @Override
    public double getPerimeter() {
        return 4 * width;
    }
}
