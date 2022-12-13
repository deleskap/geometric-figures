package pl.kurs.geometricfigures.service;

import org.springframework.stereotype.Component;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.repository.SquareRepository;

@Component
public class SquareManagementService extends GenericManagementService<Square, SquareRepository> {
    public SquareManagementService(SquareRepository repository) {
        super(repository, "SQUARE");
    }




}




