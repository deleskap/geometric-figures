package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.repository.ShapeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    @PersistenceContext
    EntityManager entityManager;

    public ShapeManagementService(ShapeRepository repository) {
        super(repository);
    }

    public List<Shape> findShapes(Map<String, Object> parameters) throws ClassNotFoundException, NoSuchFieldException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shape> query = builder.createQuery(Shape.class);
        Root<Shape> root = query.from(Shape.class);

        if (parameters.containsKey("type")) {
            Object type = parameters.get("type");
            Class<?> subclass = Class.forName(type.toString());
            Root<?> root2 = query.from(subclass);
            Set<String> keys = parameters.keySet();

            for (String key : keys) {
                if (key.endsWith("From") || key.endsWith("To")) {
                    String fieldName = key.substring(0, key.length() - 4);
                    // Use reflection to get the value of the field from the Map
                    Object fieldValue = parameters.get(key);
                    Field field = subclass.getDeclaredField(fieldName);
                    field.setAccessible(true);
//
//                    // Add a condition to the query to match the field value
//                    if (key.endsWith("From")) {
//                        query.where(builder.greaterThanOrEqualTo(root2.get(fieldName), fieldValue));
//                    } else {
//                        query.where(builder.lessThanOrEqualTo(root2.get(fieldName), fieldValue));
//                    }
                }

            query.where(builder.equal(root.get("type"), type.toString()));
        }}
        if (parameters.containsKey("createdBy")) {
            Object createdBy = parameters.get("createdBy");
            query.where(builder.equal(root.get("createdBy"), createdBy));
        }
        if (parameters.containsKey("createdAtFrom")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(parameters.get("createdAtFrom").toString(), formatter);
            LocalDateTime createdDateFrom = date.atStartOfDay();
            query.where(builder.greaterThanOrEqualTo(root.get("createdAt"), createdDateFrom));
        }
        if (parameters.containsKey("createdAtTo")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(parameters.get("createdAtTo").toString(), formatter);
            LocalDateTime createdDateTo = date.atStartOfDay();
            query.where(builder.lessThanOrEqualTo(root.get("createdAt"), createdDateTo));
        }


        TypedQuery<Shape> typedQuery = entityManager.createQuery(query);
        List<Shape> shapeList = typedQuery.getResultList();

        shapeList = shapeList.stream()
                .filter(s -> s.getArea() <= Double.parseDouble(parameters.get("areaTo").toString()))
                .collect(Collectors.toList());

        if (parameters.containsKey("areaTo")) {
            shapeList = shapeList.stream()
                    .filter(s -> s.getArea() <= Double.parseDouble(parameters.get("areaTo").toString()))
                    .collect(Collectors.toList());
        }

        if (parameters.containsKey("areaFrom")) {
            shapeList = shapeList.stream()
                    .filter(s -> s.getArea() >= Double.parseDouble(parameters.get("areaFrom").toString()))
                    .collect(Collectors.toList());
        }

        if (parameters.containsKey("perimeterTo")) {
            shapeList = shapeList.stream()
                    .filter(s -> s.getArea() <= Double.parseDouble(parameters.get("perimeterTo").toString()))
                    .collect(Collectors.toList());
        }

        if (parameters.containsKey("perimeterFrom")) {
            shapeList = shapeList.stream()
                    .filter(s -> s.getArea() >= Double.parseDouble(parameters.get("perimeterFrom").toString()))
                    .collect(Collectors.toList());
        }



        return shapeList;
    }


}



