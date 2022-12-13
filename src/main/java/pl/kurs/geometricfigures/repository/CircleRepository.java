package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Circle;

public interface CircleRepository extends JpaRepository<Circle, Long> {
}
