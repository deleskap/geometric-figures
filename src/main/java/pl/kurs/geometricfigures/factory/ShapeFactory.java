package pl.kurs.geometricfigures.factory;

import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.factory.creators.ShapeCreator;
import pl.kurs.geometricfigures.factory.dto.ShapeDtoCreator;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShapeFactory {
    private final Map<String, ShapeCreator> creators;
    private final Map<String, ShapeDtoCreator> dtoCreators;


    public ShapeFactory(Set<ShapeCreator> creators, Set<ShapeDtoCreator> dtoCreators) {
        this.creators = creators.stream().collect(Collectors.toMap(ShapeCreator::getType, Function.identity()));
        this.dtoCreators = dtoCreators.stream().collect(Collectors.toMap(ShapeDtoCreator::getType, Function.identity()));

    }

    public Shape createShape(CreateShapeCommand command) {
        if (!creators.containsKey(command.getType()))
            throw new BadRequestException("Typed shape not found!");
        return creators.get(command.getType().toUpperCase(Locale.ROOT)).create(command.getParameters());
    }

    public ShapeDto createDto(Shape shape) {
        ShapeDto dto = dtoCreators.get(shape.getClass().getSimpleName().toUpperCase(Locale.ROOT)).create(shape);
        return dto;
    }
}
