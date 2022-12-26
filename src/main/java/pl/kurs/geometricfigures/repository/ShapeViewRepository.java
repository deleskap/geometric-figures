package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.ShapeView;

public interface ShapeViewRepository extends JpaRepository<ShapeView, Long> {
}



