package pl.kurs.geometricfigures.factory.creators;

import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;

public interface ShapeCreator {

    String getType();

    Shape create(Double[] parameters);

    ShapeDto createDto(Shape shape);

    ShapeDto createDtoFromView(ShapeView shapeView);
}
