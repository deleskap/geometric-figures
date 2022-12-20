package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.ShapeChange;

public interface ShapeChangeRepository extends JpaRepository<ShapeChange, Integer> {
}
