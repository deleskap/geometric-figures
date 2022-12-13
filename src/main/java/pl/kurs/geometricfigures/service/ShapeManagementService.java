package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.repository.ShapeRepository;

import java.util.List;


@Component
public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    public ShapeManagementService(ShapeRepository repository) {
        super(repository, "SHAPE");
    }

//    public List<Shape> getAllByCreatedBy(String createdBy){
//        List<Shape> shapes = repository.getAllByCreatedBy(createdBy);
//        if (shapes.isEmpty()){
//            throw new RuntimeException();
//        }
//        return shapes;
//    }

//    public List<Shape> getByArea(Double areaFrom, Double areaTo){
//        List<Shape> shapes = repository.getAllByAreaBeforeAndAreaAfter(areaFrom, areaTo);
//        if (shapes.isEmpty()){
//            throw new RuntimeException();
//        }
//        return shapes;
//    }

}
