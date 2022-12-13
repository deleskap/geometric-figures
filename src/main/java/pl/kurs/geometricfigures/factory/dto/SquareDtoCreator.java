package pl.kurs.geometricfigures.factory.dto;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;

@Service
@AllArgsConstructor
public class SquareDtoCreator implements ShapeDtoCreator {
    private final ModelMapper mapper;

    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public ShapeDto create(Shape source) {
        SquareDto dto = mapper.map(source, SquareDto.class);
        return dto;
    }
}
