package pl.kurs.geometricfigures.factory;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.factory.creators.ShapeCreator;
import pl.kurs.geometricfigures.factory.dto.ShapeDtoCreator;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.security.JwtUserDetailsService;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShapeFactory {
    private final Map<String, ShapeCreator> creators;
    private final Map<String, ShapeDtoCreator> dtoCreators;
    private final JwtUserDetailsService service;
    private final AuditorAware<String> auditorAware;


    public ShapeFactory(Set<ShapeCreator> creators, Set<ShapeDtoCreator> dtoCreators,
                        JwtUserDetailsService service, AuditorAware auditorAware) {
        this.creators = creators.stream().collect(Collectors.toMap(ShapeCreator::getType, Function.identity()));
        this.dtoCreators = dtoCreators.stream().collect(Collectors.toMap(ShapeDtoCreator::getType, Function.identity()));
        this.service = service;
        this.auditorAware = auditorAware;
    }

    public Shape createShape(CreateShapeCommand command) {
        if (!creators.containsKey(command.getType()))
            throw new BadRequestException("Typed shape not found!");
        Shape shape = creators.get(command.getType().toUpperCase(Locale.ROOT)).create(command.getParameters());
        return shape;
    }

    public ShapeDto createDto(Shape shape) {
        ShapeDto dto = dtoCreators.get(shape.getClass().getSimpleName().toUpperCase(Locale.ROOT)).create(shape);
        return dto;
    }
}
