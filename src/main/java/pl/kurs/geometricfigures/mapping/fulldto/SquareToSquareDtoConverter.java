package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;

import java.util.Locale;

@Service
public class SquareToSquareDtoConverter implements Converter<Square, SquareDto>, ShapeToShapeDtoConverter {
    @Override
    public SquareDto convert(MappingContext<Square, SquareDto> mappingContext) {
        Square source = mappingContext.getSource();

        return SquareDto.builder()
                .id(source.getId())
                .type(source.getClass().getSimpleName().toUpperCase(Locale.ROOT))
                .width(source.getWidth())
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
