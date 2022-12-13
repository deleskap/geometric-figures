package pl.kurs.geometricfigures.factory.dto;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.dto.fullDto.CircleDto;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;

@Service
@AllArgsConstructor
public class CircleDtoCreator implements ShapeDtoCreator {

    private final ModelMapper mapper;

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public ShapeDto create(Shape source) {
        //TODO walidacja parametrow
        CircleDto dto = mapper.map(source, CircleDto.class);

        return dto;
    }
}
