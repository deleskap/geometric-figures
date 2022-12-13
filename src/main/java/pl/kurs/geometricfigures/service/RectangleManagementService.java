package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.repository.RectangleRepository;
import pl.kurs.geometricfigures.repository.SquareRepository;

@Component
public class RectangleManagementService extends GenericManagementService<Rectangle, RectangleRepository> {
    public RectangleManagementService(RectangleRepository repository) {
        super(repository, "RECTANGLE");
    }

}




