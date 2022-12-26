package pl.kurs.geometricfigures.factory.creators;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;

@Service
@AllArgsConstructor
public class SquareCreator implements ShapeCreator {
    private final ModelMapper mapper;
    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public Shape create(Double[] parameters) {
        if (parameters.length != 1)
            throw new BadRequestException("WRONG_PARAMETERS");
        return new Square(parameters[0]);
    }
    @Override
    public ShapeDto createDto(Shape source) {
        return mapper.map(source, SquareDto.class);
    }

    @Override
    public ShapeDto createDtoFromView(ShapeView shapeView) {
        return mapper.map(shapeView, SquareDto.class);
    }
}
