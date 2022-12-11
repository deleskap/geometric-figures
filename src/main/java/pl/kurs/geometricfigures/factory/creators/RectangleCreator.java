package pl.kurs.geometricfigures.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;

@Service
public class RectangleCreator implements ShapeCreator{
    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public Shape create(Double[] parameters) {
        //TODO walidacja parametrow
        return new Rectangle(parameters[0], parameters[1]);
    }
}
