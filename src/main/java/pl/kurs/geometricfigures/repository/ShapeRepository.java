package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.geometricfigures.model.Shape;

import javax.persistence.NamedNativeQuery;
import java.util.List;

public interface ShapeRepository extends JpaRepository<Shape, Long> {

}



