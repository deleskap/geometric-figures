package pl.kurs.geometricfigures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import pl.kurs.geometricfigures.exceptions.BadEntityException;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.command.FindShapesQuery;
import pl.kurs.geometricfigures.model.command.UpdateShapeCommand;
import pl.kurs.geometricfigures.model.shapechange.ShapeChange;
import pl.kurs.geometricfigures.repository.ShapeChangeRepository;
import pl.kurs.geometricfigures.repository.ShapeRepository;
import pl.kurs.geometricfigures.security.AppUser;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Service

public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ShapeChangeRepository shapeChangeRepository;
    @Autowired
    ViewService viewService;

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

    @Transactional
    public Shape edit(Long id, UpdateShapeCommand command) throws NoSuchFieldException, IllegalAccessException {
        Shape loadedEntity = repository.findById(id).orElseThrow(() -> new BadEntityException(id));
        ShapeChange change = new ShapeChange();
        Field field = loadedEntity.getClass().getDeclaredField(command.getFieldName());
        field.setAccessible(true);
        change.setShape(loadedEntity);
        change.setFieldName(command.getFieldName());
        change.setNewValue(command.getValue());
        change.setOldValue((Double) field.get(loadedEntity));
        field.set(loadedEntity, command.getValue());
        loadedEntity.setVersion(loadedEntity.getVersion() + 1);
        shapeChangeRepository.saveAndFlush(change);
        return loadedEntity;
    }

    public List<ShapeView> findShapesByParameters(FindShapesQuery params) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ShapeView> query = builder.createQuery(ShapeView.class);
        Root<ShapeView> root = query.from(ShapeView.class);
        List<Predicate> predicates = new ArrayList<>();
        if (params.getType() != null) {
            String type = params.getType().toUpperCase(Locale.ROOT);
            predicates.add(builder.equal(root.get("type"), type));
        }
        if (params.getAreaFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("area"), params.getAreaFrom()));
        }
        if (params.getAreaTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("area"), params.getAreaTo()));
        }
        if (params.getPerimeterFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("perimeter"), params.getPerimeterFrom()));
        }
        if (params.getPerimeterTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("perimeter"), params.getPerimeterTo()));
        }
        if (params.getRadiusFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("radius"), params.getRadiusFrom()));
        }
        if (params.getRadiusTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("radius"), params.getRadiusTo()));
        }
        if (params.getWidthFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("width"), params.getWidthFrom()));
        }
        if (params.getWidthTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("width"), params.getWidthTo()));
        }
        if (params.getHeightFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("height"), params.getHeightFrom()));
        }
        if (params.getHeightTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("height"), params.getHeightTo()));
        }
        if (params.getCreatedBy() != null) {
            Long createdBy = params.getCreatedBy();
            predicates.add(builder.equal(root.get("createdById"), createdBy));
        }
        if (params.getCreatedAtFrom() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(params.getCreatedAtFrom(), formatter);
            LocalDateTime createdDateFrom = date.atStartOfDay();
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), createdDateFrom));
        }
        if (params.getCreatedAtTo() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(params.getCreatedAtTo(), formatter);
            LocalDateTime createdDateTo = date.atStartOfDay();
            predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), createdDateTo));
        }

        if (predicates.size() != 0) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        TypedQuery<ShapeView> typedQuery = entityManager.createQuery(query);
        List<ShapeView> shapeViewList = typedQuery.getResultList();

        return shapeViewList;

    }

    public Context createReportContext() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        List<Shape> shapes = repository.findAllByCreatedAtAfterAndCreatedAtBefore(from, to);
        int count = shapes.size();
        Map<String, Long> subclassCounts = shapes.stream()
                .collect(Collectors.groupingBy(shape -> shape.getClass().getSimpleName().toUpperCase(Locale.ROOT), Collectors.counting()));
        Map<String, Long> users = shapes.stream()
                .collect(Collectors.groupingBy(shape -> shape.getCreatedBy().getUsername(), Collectors.counting()));

        Context context = new Context();
        context.setVariable("date", LocalDate.now().minusDays(1));
        context.setVariable("count", count);
        context.setVariable("subclassCounts", subclassCounts);
        context.setVariable("users", users);
        return context;
//
    }

}