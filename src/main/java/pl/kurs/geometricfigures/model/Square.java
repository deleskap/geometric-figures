package pl.kurs.geometricfigures.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Positive;
import java.util.Map;

@Getter
@Setter
@Entity
@DiscriminatorValue("SQUARE")
@NoArgsConstructor
public class Square extends Shape {
    @Positive(message = "Width should be positive value")
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

    @Override
    public Expression<Double> getAreaExpression(CriteriaBuilder builder, Root<?> root) {
        return builder.prod(1.0, builder.prod(root.get("width"), root.get("width")));
    }

    @Override
    public Expression<Double> getPerimeterExpression(CriteriaBuilder builder, Root<?> root) {
        return builder.prod(4.0, root.get("width"));
    }
}
