package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.dto.fullDto.CircleDto;

import java.util.Locale;


@Service
public class CircleToCircleDtoConverter implements Converter<Circle, CircleDto>, ShapeToShapeDtoConverter {
    @Override
    public CircleDto convert(MappingContext<Circle, CircleDto> mappingContext) {
        Circle source = mappingContext.getSource();

        return CircleDto.builder()
                .id(source.getId())
                .type(source.getClass().getSimpleName().toUpperCase(Locale.ROOT))
                .radius(source.getRadius())
                .version(source.getVersion())
                .createdAt(source.getCreatedAt())
                .createdBy(source.getCreatedBy().getUsername())
                .lastModifiedAt(source.getLastModifiedAt())
                .lastModifiedBy(source.getLastModifiedBy().getUsername())
                .area(source.getArea())
                .perimeter(source.getPerimeter())
                .build();
    }
}
