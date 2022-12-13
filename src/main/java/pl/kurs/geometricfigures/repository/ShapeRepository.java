package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.model.Shape;

public interface ShapeRepository extends JpaRepository<Shape, Long> {

}


//    List<Shape> getAllByCreatedBy(String createdBy);

//    @Query("SELECT s FROM Shape s WHERE :subclass  BETWEEN :min AND :max")
//@Query("SELECT s FROM Shape s WHERE :subClass instanceof Shape AND Circle.radius BETWEEN :min AND :max")
//    List<Shape> getAllByAreaBeforeAndAreaAfter(@Param("min") Double min, @Param("max") Double max, @Param("subclass") Object subclass);
////
//    List<Shape> findByClass(Class<? extends Shape> shapeClass );






