package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.repository.RectangleRepository;
import pl.kurs.geometricfigures.repository.SquareRepository;

import java.util.List;
import java.util.Map;

@Component
public class RectangleManagementService extends GenericManagementService<Rectangle, RectangleRepository> {
    public RectangleManagementService(RectangleRepository repository) {
        super(repository, "RECTANGLE");
    }

    @Override
    public List<Rectangle> getAllByParameters(Map<String, Object> parameters) {
        return repository.findByWidthBetweenAndHeightBetween(Double.valueOf(parameters.get("widthFrom").toString()),
                                                            Double.valueOf(parameters.get("widthTo").toString()),
                                                            Double.valueOf(parameters.get("heightFrom").toString()),
                                                            Double.valueOf(parameters.get("heightTo").toString()));
    }
}




