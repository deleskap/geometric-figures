package pl.kurs.geometricfigures.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Shape;

@Service
public class CircleCreator implements ShapeCreator{
    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public Shape create(Double[] parameters) {
        //TODO walidacja parametrow
        return new Circle(parameters[0]);
    }
}
