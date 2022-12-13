package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.repository.CircleRepository;

import java.util.Locale;

@Component
public class CircleManagementService extends GenericManagementService<Circle, CircleRepository> {
    public CircleManagementService(CircleRepository repository) {
        super(repository, "CIRCLE");
    }

}




