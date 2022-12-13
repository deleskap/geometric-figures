package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.repository.SquareRepository;

import java.util.List;
import java.util.Map;

@Component
public class SquareManagementService extends GenericManagementService<Square, SquareRepository> {
    public SquareManagementService(SquareRepository repository) {
        super(repository, "SQUARE");
    }

    @Override
    public List<Square> getAllByParameters(Map<String, Object> parameters) {

        double widthFrom = parameters.containsKey("widthFrom") ? Double.valueOf(parameters.get("widthFrom").toString()) : Double.MIN_VALUE;
        double widthTo = parameters.containsKey("widthTo") ? Double.valueOf(parameters.get("widthTo").toString()) : Double.MAX_VALUE;

        return repository.findByWidthBetween(widthFrom,
                widthTo);
    }


}




