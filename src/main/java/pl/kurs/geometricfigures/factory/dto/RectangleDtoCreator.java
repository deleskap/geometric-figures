package pl.kurs.geometricfigures.factory.dto;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.dto.fullDto.RectangleDto;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;

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
        return mapper.map(source, RectangleDto.class);
    }
}
