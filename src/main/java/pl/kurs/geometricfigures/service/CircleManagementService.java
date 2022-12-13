package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.repository.CircleRepository;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class CircleManagementService extends GenericManagementService<Circle, CircleRepository> {
    public CircleManagementService(CircleRepository repository) {
        super(repository, "CIRCLE");
    }

    @Override
    public List<Circle> getAllByParameters(Map<String, Object> parameters) {
        return repository.findByRadiusBetween(Double.valueOf(parameters.get("radiusFrom").toString()),Double.valueOf(parameters.get("radiusTo").toString()));
    }
}




