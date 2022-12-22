package pl.kurs.geometricfigures.service;

import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeAreaAndPerimeterUtility;
import pl.kurs.geometricfigures.repository.ShapeRepository;
import pl.kurs.geometricfigures.security.AppUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    @PersistenceContext
    EntityManager entityManager;

    public ShapeManagementService(ShapeRepository repository) {
        super(repository);
    }

    public boolean isShapeCreator(Long shapeId, AppUser user) {
        Shape shape = repository.findById(shapeId).orElse(null);
        if (shape == null) {
            return false;
        }
        return shape.getCreatedBy().getId().equals(user.getId());
    }

    public List<Shape> findShapes(Map<String, Object> parameters) throws ClassNotFoundException {
        List<Shape> shapeList = new ArrayList<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Set<Class<? extends Shape>> subclasses = new HashSet<>();

        if (parameters.containsKey("type")) {
            String type = parameters.get("type").toString();
            type = type.substring(0, 1).toUpperCase(Locale.ROOT) + type.substring(1).toLowerCase(Locale.ROOT);
            Class<? extends Shape> typeClass = (Class<? extends Shape>) Class.forName("pl.kurs.geometricfigures.model." + type);
            subclasses.add(typeClass);
        } else {
            Reflections reflections = new Reflections("pl.kurs.geometricfigures.model");
            subclasses = reflections.getSubTypesOf(Shape.class);
        }

        for (Class<? extends Shape> type : subclasses) {
            try {
                CriteriaQuery<? extends Shape> queryForType = builder.createQuery(type);
                Root<? extends Shape> root = queryForType.from(type);
                List<Predicate> predicates = new ArrayList<>();

                if (parameters.containsKey("areaFrom")) {
                    Object areaFrom = parameters.get("areaFrom");
                    Double minArea = Double.valueOf(areaFrom.toString());
                    Expression<Double> areaExpression = ShapeAreaAndPerimeterUtility.getAreaExpressionForShape(type, builder, root);
                    Predicate areaFromPredicate = builder.greaterThanOrEqualTo(areaExpression, minArea);
                    predicates.add(areaFromPredicate);
                }

                if (parameters.containsKey("areaTo")) {
                    Object areaTo = parameters.get("areaTo");
                    Double maxArea = Double.valueOf(areaTo.toString());
                    Expression<Double> areaExpression = ShapeAreaAndPerimeterUtility.getAreaExpressionForShape(type, builder, root);
                    Predicate areaToPredicate = builder.lessThanOrEqualTo(areaExpression, maxArea);
                    predicates.add(areaToPredicate);
                }

                if (parameters.containsKey("perimeterFrom")) {
                    Object perimeterFrom = parameters.get("perimeterFrom");
                    Double minPerimeter = Double.valueOf(perimeterFrom.toString());
                    Expression<Double> areaExpression = ShapeAreaAndPerimeterUtility.getPerimeterExpressionForShape(type, builder, root);
                    Predicate perimeterFromPredicate = builder.greaterThanOrEqualTo(areaExpression, minPerimeter);
                    predicates.add(perimeterFromPredicate);
                }

                if (parameters.containsKey("perimeterTo")) {
                    Object perimeterTo = parameters.get("perimeterTo");
                    Double maxPerimeter = Double.valueOf(perimeterTo.toString());
                    Expression<Double> areaExpression = ShapeAreaAndPerimeterUtility.getPerimeterExpressionForShape(type, builder, root);
                    Predicate perimeterToPredicate = builder.lessThanOrEqualTo(areaExpression, maxPerimeter);
                    predicates.add(perimeterToPredicate);
                }

                Set<String> keys = new HashSet<>(parameters.keySet());
                Collection<String> toRemove = Arrays.asList("type", "areaFrom", "areaTo", "perimeterFrom", "perimeterTo",
                        "createdBy", "createdAtFrom", "createdAtTo");
                keys.removeAll(toRemove);

                for (String key : keys) {
                    String fieldName = key.replaceAll("(From|To)$", "");

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
                if (predicates.size() != 0) {
                    queryForType.where(predicates.toArray(new Predicate[predicates.size()]));
                }
                TypedQuery<? extends Shape> typedQuery = entityManager.createQuery(queryForType);
                List<? extends Shape> shapeListOfType = typedQuery.getResultList();
                shapeList.addAll(shapeListOfType);
            } catch (IllegalArgumentException e) {
            }
        }
        return shapeList.stream().sorted(Comparator.comparingLong(Shape::getId)).collect(Collectors.toList());
    }

}