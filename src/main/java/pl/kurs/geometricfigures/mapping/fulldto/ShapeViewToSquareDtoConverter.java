package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.dto.fullDto.CircleDto;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;


@Service
public class ShapeViewToSquareDtoConverter implements Converter<ShapeView, SquareDto>,  ShapeViewToShapeDtoConverter {

    @Override
    public SquareDto convert(MappingContext<ShapeView, SquareDto> mappingContext) {
        ShapeView source = mappingContext.getSource();
        return SquareDto.builder()
                .id(source.getId())
                .type(source.getType())
                .width(source.getWidth())
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
