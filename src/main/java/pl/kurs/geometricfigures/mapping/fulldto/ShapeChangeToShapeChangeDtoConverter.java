package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.mapping.fulldto.ShapeToShapeDtoConverter;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.dto.fullDto.RectangleDto;
import pl.kurs.geometricfigures.model.shapechange.ShapeChange;
import pl.kurs.geometricfigures.model.shapechange.ShapeChangeDto;

import java.util.Locale;

@Service
public class ShapeChangeToShapeChangeDtoConverter implements Converter<ShapeChange, ShapeChangeDto>, ShapeToShapeDtoConverter {
    @Override
    public ShapeChangeDto convert(MappingContext<ShapeChange, ShapeChangeDto> mappingContext) {
        ShapeChange source = mappingContext.getSource();

        return ShapeChangeDto.builder()
                .id(source.getId())
                .fieldName(source.getFieldName())
                .oldValue(source.getOldValue())
                .newValue(source.getNewValue())
                .modifiedBy(source.getModifiedBy().getUsername())
                .modificationTime(source.getModificationTime())
                .build();
    }

}
