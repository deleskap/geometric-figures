package pl.kurs.geometricfigures.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.factory.ShapeFactory;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.service.GenericManagementService;
import pl.kurs.geometricfigures.service.ShapeManagementService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/shapes")
public class ShapeController {
    private final ShapeManagementService shapeManagementService;
    private final ModelMapper modelMapper;
    private final ShapeFactory shapeFactory;

    public ShapeController(ShapeManagementService shapeManagementService, ModelMapper modelMapper, ShapeFactory shapeFactory) {
        this.shapeManagementService = shapeManagementService;
        this.modelMapper = modelMapper;
        this.shapeFactory = shapeFactory;
    }

    @GetMapping
    public ResponseEntity<List<ShapeDto>> getAllShapes() {
        List<ShapeDto> shapeDtoList = shapeManagementService.getAll()
                .stream()
                .map(shapeFactory::createDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ShapeDto> createShape(@RequestBody @Valid CreateShapeCommand command) {
        Shape shape = shapeFactory.createShape(command);
        shape = shapeManagementService.add(shape);
        ShapeDto dto = shapeFactory.createDto(shape);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/parameters")
    public ResponseEntity<List<ShapeDto>> getShapesByParameters(@RequestParam Map<String, Object> parameters) throws NoSuchFieldException, ClassNotFoundException {

        List<? extends Shape> shapeList = shapeManagementService.findShapes(parameters);
        List<ShapeDto> shapeDtoList = shapeList.stream().map(x -> shapeFactory.createDto(x))
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }
}