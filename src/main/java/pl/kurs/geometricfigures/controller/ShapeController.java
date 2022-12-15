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

        List<Shape> shapeList = shapeManagementService.findShapes(parameters);
        List<ShapeDto> shapeDtoList = shapeList.stream().map(x -> shapeFactory.createDto(x))
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }
//    @GetMapping("/param")
//    public ResponseEntity<List<ShapeDto>> getShapes(@RequestParam Map<String, Object> parameters) throws ClassNotFoundException {
//
//        String shapeClassName = (String) parameters.get("shapeClass");
//        // Load the class object for the shape class
//        Class<? extends Shape> shapeClass = (Class<? extends Shape>) Class.forName(shapeClassName);
//        // Get the list of shapes from the service using the provided class and parameters
//        List<? extends Shape> shapeList = shapeManagementService.getShapes(shapeClass, parameters);
//
//        List<ShapeDto> shapeDtoList = shapeList.stream().map(x -> shapeFactory.createDto(x))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
//    }


}







//    @PostMapping("/init")
//    public void init() {
//        for (int i = 1; i < 100; i++) {
//            shapeManagementService.add(new Circle(0.0 + i));
//        }
//        for (int i = 1; i < 100; i++) {
//            shapeManagementService.add(new Rectangle(0.0 + i, 0.0 + i * 1.5));
//        }
//        for (int i = 1; i < 100; i++) {
//            shapeManagementService.add(new Square(0.0 + i));
//        }
//    }
