package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Circle;

import java.util.List;

public interface CircleRepository extends JpaRepository<Circle, Long> {
    List<Circle> findByRadiusBetween(double minRadius, double maxRadius);
}
