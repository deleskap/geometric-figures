package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Square;

import java.util.List;

public interface SquareRepository extends JpaRepository<Square, Long> {
    List<Square> findByWidthBetween(double minWidth, double maxWidth);
}
