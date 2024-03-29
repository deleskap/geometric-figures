package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.dto.fullDto.RectangleDto;

import java.util.Locale;

@Service
public class RectangleToRectangleDtoConverter implements Converter<Rectangle, RectangleDto>, ShapeToShapeDtoConverter {
    @Override
    public RectangleDto convert(MappingContext<Rectangle, RectangleDto> mappingContext) {
        Rectangle source = mappingContext.getSource();

        return RectangleDto.builder()
                .id(source.getId())
                .type(source.getClass().getSimpleName().toUpperCase(Locale.ROOT))
                .width(source.getWidth())
                .height(source.getHeight())
                .version(source.getVersion())
                .createdAt(source.getCreatedAt())
                .createdBy(source.getCreatedBy().getId())
                .lastModifiedAt(source.getLastModifiedAt())
                .lastModifiedBy(source.getLastModifiedBy().getId())
                .area(source.getArea())
                .perimeter(source.getPerimeter())
                .build();
    }

}
