package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.repository.RectangleRepository;

import java.util.List;
import java.util.Map;

@Component
public class RectangleManagementService extends GenericManagementService<Rectangle, RectangleRepository> {
    public RectangleManagementService(RectangleRepository repository) {
        super(repository, "RECTANGLE");
    }

    @Override
    public List<Rectangle> getAllByParameters(Map<String, Object> parameters) {

        double widthFrom = parameters.containsKey("widthFrom") ? Double.valueOf(parameters.get("widthFrom").toString()) : Double.MIN_VALUE;
        double widthTo = parameters.containsKey("widthTo") ? Double.valueOf(parameters.get("widthTo").toString()) : Double.MAX_VALUE;
        double heightFrom = parameters.containsKey("heightFrom") ? Double.valueOf(parameters.get("heightFrom").toString()) : Double.MIN_VALUE;
        double heightTo = parameters.containsKey("heightTo") ? Double.valueOf(parameters.get("heightTo").toString()) : Double.MAX_VALUE;

        return repository.findByWidthBetweenAndHeightBetween(widthFrom, widthTo, heightFrom, heightTo);
    }
}




