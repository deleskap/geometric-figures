package pl.kurs.geometricfigures.factory.creators;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.dto.fullDto.RectangleDto;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;

@Service
@AllArgsConstructor
public class RectangleCreator implements ShapeCreator {
    private final ModelMapper mapper;
    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public Shape create(Double[] parameters) {
        if (parameters.length != 2)
            throw new BadRequestException("WRONG_PARAMETERS");
        return new Rectangle(parameters[0], parameters[1]);
    }

    @Override
    public ShapeDto createDto(Shape source) {
        return mapper.map(source, RectangleDto.class);
    }

    @Override
    public ShapeDto createDtoFromView(ShapeView shapeView) {
        return mapper.map(shapeView, RectangleDto.class);
    }
}
