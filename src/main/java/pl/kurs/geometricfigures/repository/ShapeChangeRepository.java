package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.shapechange.ShapeChange;

import java.util.List;

public interface ShapeChangeRepository extends JpaRepository<ShapeChange, Integer> {
    List<ShapeChange> findByShapeId(Long id);
}
