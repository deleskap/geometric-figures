package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.repository.ShapeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Component
public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    @PersistenceContext
    EntityManager entityManager;

    public ShapeManagementService(ShapeRepository repository) {
        super(repository, "SHAPE");
    }

//    public List<Shape> getByAreaBetween(double min, double max) {
//        return repository.findByAreaBetween(min, max);
//    }


//    public List<Shape> getByParameters(Map<String, Object> parameters) {
//
//        double areaTo = Double.valueOf(parameters.get("areaTo").toString());
//        double areaFrom = Double.valueOf(parameters.get("areaFrom").toString());
//
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Shape> criteria = builder.createQuery(Shape.class);
//
//        Root<Shape> root = criteria.from(Shape.class);
//
//        Expression<Double> area = root.get("area");
//
//        Predicate areaPredicate = builder.between(area, areaFrom, areaTo);
//
//        criteria.where(areaPredicate);
//
//
//        return entityManager.createQuery(criteria).getResultList();
//    }












//        Root<Shape> treatedRoot = cb.treat(root, Shape.class);
//        Expression<Double> area = treatedRoot.get("getArea");
////        predicate = cb.and(predicate, cb.between(areaFunction, parameters.get("areaFrom").toString(), parameters.get("areaTo")));
//        predicate = cb.and(predicate, cb.between(
//                areaFunction,
//                Double.valueOf(parameters.get("areaFrom").toString()),
//                Double.valueOf(parameters.get("areaTo").toString())));
//
//        query.where(predicate);
//        predicates.add(cb.equal(root.get("createdBy"), parameters.get("createdBy")));
//        predicates.add(cb.equal(root.get("radius"), parameters.get("radius")));
//        Predicate predicate = cb.conjunction();
    }
//    public List<Shape> getAllByCreatedBy(String createdBy){
//        List<Shape> shapes = repository.getAllByCreatedBy(createdBy);
//        if (shapes.isEmpty()){
//            throw new RuntimeException();
//        }
//        return shapes;
//    }

//    public List<Shape> getByArea(Double areaFrom, Double areaTo){
//        List<Shape> shapes = repository.getAllByAreaBeforeAndAreaAfter(areaFrom, areaTo);
//        if (shapes.isEmpty()){
//            throw new RuntimeException();
//        }
//        return shapes;
//    }


