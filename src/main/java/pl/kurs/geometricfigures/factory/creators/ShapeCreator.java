package pl.kurs.geometricfigures.factory.creators;

import pl.kurs.geometricfigures.model.Shape;

import javax.validation.constraints.NotEmpty;

public interface ShapeCreator {
    @NotEmpty
    String getType();

    @NotEmpty
    Shape create(Double[] parameters);
}
