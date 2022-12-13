package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.repository.CircleRepository;

import java.util.List;
import java.util.Map;

@Component
public class CircleManagementService extends GenericManagementService<Circle, CircleRepository> {
    public CircleManagementService(CircleRepository repository) {
        super(repository, "CIRCLE");
    }

    @Override
    public List<Circle> getAllByParameters(Map<String, Object> parameters) {

        double radiusFrom = parameters.containsKey("radiusFrom") ? Double.valueOf(parameters.get("radiusFrom").toString()) : Double.MIN_VALUE;
        double radiusTo = parameters.containsKey("radiusTo") ? Double.valueOf(parameters.get("radiusTo").toString()) : Double.MAX_VALUE;

        return repository.findByRadiusBetween(radiusFrom, radiusTo);
    }
}




