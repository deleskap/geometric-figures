package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Rectangle;

public interface RectangleRepository extends JpaRepository<Rectangle, Long> {

}
