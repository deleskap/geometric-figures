package pl.kurs.geometricfigures.factory.dto;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.factory.creators.ShapeCreator;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.dto.fullDto.RectangleDto;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;

import java.util.Map;

@Service
@AllArgsConstructor
public class RectangleDtoCreator implements ShapeDtoCreator {
    private final ModelMapper mapper;

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public ShapeDto create(Shape source) {
        //TODO walidacja parametrow
        RectangleDto dto = mapper.map(source, RectangleDto.class);
        return dto;
    }
}
