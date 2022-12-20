package pl.kurs.geometricfigures.factory.creators;

import pl.kurs.geometricfigures.model.Shape;

public interface ShapeCreator {

    String getType();

    Shape create(Double[] parameters);
}
