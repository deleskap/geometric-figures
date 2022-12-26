package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.dto.fullDto.CircleDto;

import java.util.Locale;


@Service
public class ShapeViewToCircleDtoConverter implements Converter<ShapeView, CircleDto>,  ShapeViewToShapeDtoConverter {

    @Override
    public CircleDto convert(MappingContext<ShapeView, CircleDto> mappingContext) {
        ShapeView source = mappingContext.getSource();

        return CircleDto.builder()
                .id(source.getId())
                .type(source.getType())
                .radius(source.getRadius())
                .version(source.getVersion())
                .createdAt(source.getCreatedAt())
                .createdBy(source.getCreatedById())
                .lastModifiedAt(source.getLastModifiedAt())
                .lastModifiedBy(source.getLastModifiedById())
                .area(source.getArea())
                .perimeter(source.getPerimeter())
                .build();

    }
}
