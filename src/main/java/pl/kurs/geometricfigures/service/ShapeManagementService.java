package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.repository.ShapeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    @PersistenceContext
    EntityManager entityManager;

    public ShapeManagementService(ShapeRepository repository) {
        super(repository);
    }

    public List<? extends Shape> findShapes(Map<String, Object> parameters) throws ClassNotFoundException, NoSuchFieldException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Class<?> subclass;
        if (parameters.containsKey("type")) {
            Object type = parameters.get("type");
            subclass = Class.forName("pl.kurs.geometricfigures.model." + type.toString());
        } else {
            subclass = Shape.class;
        }
        CriteriaQuery<? extends Shape> query = (CriteriaQuery<? extends Shape>) builder.createQuery(subclass);
        Root<?> root = query.from(subclass);
        List<Predicate> predicates = new ArrayList<>();

        if (parameters.containsKey("type")) {
            Set<String> keys = parameters.keySet().stream().collect(Collectors.toSet());
            Collection<String> toRemove = Arrays.asList("type", "areaFrom", "areaTo", "perimeterFrom", "perimeterTo",
                    "createdBy", "createdAtFrom", "createdAtTo");
            keys.removeAll(toRemove);

            for (String key : keys) {
                String fieldName = key.replaceAll("(From|To)$", "");
                Field field = subclass.getDeclaredField(fieldName);
                field.setAccessible(true);
                if (key.endsWith("From")) {
                    Object fieldValue = parameters.get(key);
                    double min = Double.parseDouble(fieldValue.toString());
                    predicates.add(builder.greaterThanOrEqualTo(root.get(fieldName), min));
                }
                if (key.endsWith("To")) {
                    Object fieldValue = parameters.get(key);
                    double max = Double.parseDouble(fieldValue.toString());
                    predicates.add(builder.lessThanOrEqualTo(root.get(fieldName), max));
                }
            }
        }


        if (parameters.containsKey("createdBy")) {
            Object createdBy = parameters.get("createdBy");
            predicates.add(builder.equal(root.get("createdBy"), createdBy));
        }
        if (parameters.containsKey("createdAtFrom")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(parameters.get("createdAtFrom").toString(), formatter);
            LocalDateTime createdDateFrom = date.atStartOfDay();
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), createdDateFrom));
        }
        if (parameters.containsKey("createdAtTo")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(parameters.get("createdAtTo").toString(), formatter);
            LocalDateTime createdDateTo = date.atStartOfDay();
            predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), createdDateTo));
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<? extends Shape> typedQuery = entityManager.createQuery(query);
        List<? extends Shape> shapeList = typedQuery.getResultList();


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



