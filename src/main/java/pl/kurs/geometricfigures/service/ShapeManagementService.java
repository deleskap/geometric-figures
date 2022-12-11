package pl.kurs.geometricfigures.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.repository.ShapeRepository;

@Component
public class ShapeManagementService extends GenericManagementService<Shape, ShapeRepository> {
    public ShapeManagementService(ShapeRepository repository) {
        super(repository);
    }


}
