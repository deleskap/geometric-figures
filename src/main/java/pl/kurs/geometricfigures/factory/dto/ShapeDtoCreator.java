package pl.kurs.geometricfigures.factory.dto;

import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;

public interface ShapeDtoCreator {
    String getType();

    ShapeDto create(Shape shape);

}
