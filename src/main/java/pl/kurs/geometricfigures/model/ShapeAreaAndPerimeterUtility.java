package pl.kurs.geometricfigures.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface ShapeAreaAndPerimeterUtility {

    Expression<Double> getAreaExpression(CriteriaBuilder builder, Root<?> root);

    Expression<Double> getPerimeterExpression(CriteriaBuilder builder, Root<?> root);


    static Expression<Double> getAreaExpressionForShape(Class<? extends Shape> typeClass, CriteriaBuilder builder, Root<?> root) {
        Shape shape = null;
        try {
            shape = typeClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ShapeAreaAndPerimeterUtility util = shape;
        return util.getAreaExpression(builder, root);
    }

    static Expression<Double> getPerimeterExpressionForShape(Class<? extends Shape> typeClass, CriteriaBuilder builder, Root<?> root) {
        Shape shape = null;
        try {
            shape = typeClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ShapeAreaAndPerimeterUtility util = shape;
        return util.getPerimeterExpression(builder, root);
    }
}
