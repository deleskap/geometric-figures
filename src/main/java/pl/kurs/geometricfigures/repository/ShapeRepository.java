package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Shape;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
}
