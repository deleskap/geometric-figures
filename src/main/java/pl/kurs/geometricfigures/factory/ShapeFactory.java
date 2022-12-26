package pl.kurs.geometricfigures.factory;

import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.factory.creators.ShapeCreator;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeView;
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


    public ShapeFactory(Set<ShapeCreator> creators) {
        this.creators = creators.stream().collect(Collectors.toMap(ShapeCreator::getType, Function.identity()));
    }

    public Shape createShape(CreateShapeCommand command) {
        if (!creators.containsKey(command.getType()))
            throw new BadRequestException("Typed shape not found!");
        return creators.get(command.getType().toUpperCase(Locale.ROOT)).create(command.getParameters());
    }

    public ShapeDto createDto(Shape shape) {
        return creators.get(shape.getClass().getSimpleName().toUpperCase(Locale.ROOT)).createDto(shape);
    }

    public ShapeDto createDtoFromView(ShapeView shapeView) {
        return creators.get(shapeView.getType().toUpperCase(Locale.ROOT)).createDtoFromView(shapeView);
    }

}
