package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.iShape;

import java.util.List;
import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
    List<Shape> getAllByCreatedBy(String createdBy);

    @Query("SELECT s FROM Shape s WHERE s.area BETWEEN :min AND :max")
    List<Shape> getAllByAreaBeforeAndAreaAfter(@Param("min") Double min, @Param("max") Double max);


}




