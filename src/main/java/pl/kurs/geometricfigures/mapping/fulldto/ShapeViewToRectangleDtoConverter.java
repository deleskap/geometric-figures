package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.dto.fullDto.RectangleDto;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;


@Service
public class ShapeViewToRectangleDtoConverter implements Converter<ShapeView, RectangleDto>,  ShapeViewToShapeDtoConverter {

    @Override
    public RectangleDto convert(MappingContext<ShapeView, RectangleDto> mappingContext) {
        ShapeView source = mappingContext.getSource();
        return RectangleDto.builder()
                .id(source.getId())
                .type(source.getType())
                .width(source.getWidth())
                .height(source.getHeight())
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
