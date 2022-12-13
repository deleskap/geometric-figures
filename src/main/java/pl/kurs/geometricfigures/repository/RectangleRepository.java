package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Rectangle;

import java.util.List;

public interface RectangleRepository extends JpaRepository<Rectangle, Long> {
    List<Rectangle> findByWidthBetweenAndHeightBetween(double minWidth, double maxWidth, double minHeight, double maxHeight);
}
