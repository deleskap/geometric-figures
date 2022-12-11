package pl.kurs.geometricfigures.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.Square;

@Service
public class SquareCreator implements ShapeCreator{
    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public Shape create(Double[] parameters) {
        //TODO walidacja parametrow
        return new Square(parameters[0]);
    }
}
