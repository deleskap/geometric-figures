package pl.kurs.geometricfigures.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Entity
@DiscriminatorValue("RECTANGLE")
@NoArgsConstructor
public class Rectangle extends Shape {
    @Positive(message = "Width should be positive value")
    private double width;
    @Positive(message = "Height should be positive value")
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

    @Override
    public Expression<Double> getAreaExpression(CriteriaBuilder builder, Root<?> root) {
        return builder.prod(1.0, builder.prod(root.get("width"), root.get("height")));
    }

    @Override
    public Expression<Double> getPerimeterExpression(CriteriaBuilder builder, Root<?> root) {
        return builder.prod(2.0, builder.prod(root.get("width"), root.get("height")));
    }

}
