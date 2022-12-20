package pl.kurs.geometricfigures.model;

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
@DiscriminatorValue("CIRCLE")
@NoArgsConstructor
public class Circle extends Shape {
    @Positive(message = "Radius should be positive value")
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

    @Override
    public Expression<Double> getAreaExpression(CriteriaBuilder builder, Root<?> root) {
        return builder.prod(builder.function("PI", Double.class), builder.prod(root.get("radius"), root.get("radius")));
    }

    @Override
    public Expression<Double> getPerimeterExpression(CriteriaBuilder builder, Root<?> root) {
        return builder.prod(builder.function("PI", Double.class), builder.prod(root.get("radius"), 2.0));
    }

}
