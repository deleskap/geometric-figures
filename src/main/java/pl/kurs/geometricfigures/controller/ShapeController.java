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
    private final Set<GenericManagementService> managementServicesSet;
    private Map<String, GenericManagementService> managementServices = new HashMap<>();
    private final ShapeManagementService shapeManagementService;
    private final ModelMapper modelMapper;
    private final ShapeFactory shapeFactory;

    public ShapeController(Set<GenericManagementService> managementServicesSet, ShapeManagementService shapeManagementService, ModelMapper modelMapper, ShapeFactory shapeFactory) {
        this.managementServicesSet = managementServicesSet;
        this.shapeManagementService = shapeManagementService;
        this.modelMapper = modelMapper;
        this.shapeFactory = shapeFactory;
    }

    @PostConstruct
    public void postConstruct() {
        this.managementServices = managementServicesSet.stream()
                .collect(Collectors.toMap(GenericManagementService::getType, Function.identity()));
    }

    @PostMapping("/init")
    public void init() {
        for (int i = 1; i < 100; i++) {
            shapeManagementService.add(new Circle(0.0 + i));
        }
        for (int i = 1; i < 100; i++) {
            shapeManagementService.add(new Rectangle(0.0 + i, 0.0 + i * 1.5));
        }
        for (int i = 1; i < 100; i++) {
            shapeManagementService.add(new Square(0.0 + i));
        }
    }

    @GetMapping
    public ResponseEntity<List<ShapeDto>> getAllShapes() {
        List<ShapeDto> shapeDtoList = shapeManagementService.getAll()
                .stream()
                .map(shapeFactory::createDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/parameters")
    public ResponseEntity<List<ShapeDto>> getByParameters(@RequestParam Map<String, Object> parameters) {
        List<Shape> shapeList;
        Set<String> keywords = Set.of("areaFrom", "areaTo", "perimeterFrom", "perimeterTo", "createdBy", "type");
        Set<String> differentKeywords = new HashSet<>(parameters.keySet());
        differentKeywords.removeAll(keywords);
        int numberOfDifferentKeywords = differentKeywords.size();

        if (!parameters.containsKey("type")) {
            shapeList = shapeManagementService.getAll();
        } else {
            if (!managementServices.containsKey(parameters.get("type")))
                throw new BadRequestException("Specified shape not found!");

            if (parameters.containsKey("type") & numberOfDifferentKeywords >= 1
            ) {
                shapeList = managementServices.get(parameters.get("type")).getAllByParameters(parameters);
            } else {
                shapeList = managementServices.get(parameters.get("type")).getAll();
            }
        }


        if (parameters.containsKey("areaFrom") & parameters.containsKey("areaTo")) {
            shapeList = shapeList.stream()
                    .filter(x -> x.getArea() < Double.parseDouble(parameters.get("areaTo").toString()))
                    .filter(x -> x.getArea() > Double.parseDouble(parameters.get("areaFrom").toString()))
                    .collect(Collectors.toList());
        }
        if (parameters.containsKey("perimeterFrom") & parameters.containsKey("perimeterTo")) {
            shapeList = shapeList.stream()
                    .filter(x -> x.getPerimeter() > Double.parseDouble(parameters.get("perimeterFrom").toString()))
                    .filter(x -> x.getPerimeter() > Double.parseDouble(parameters.get("perimeterTo").toString()))
                    .collect(Collectors.toList());
        }
        if (parameters.containsKey("createdBy")) {
            shapeList = shapeList.stream()
                    .filter(x -> x.getCreatedBy().equals(parameters.get("createdBy")))
                    .collect(Collectors.toList());
        }
        List<ShapeDto> shapeDtoList = shapeList.stream()
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


//    @GetMapping("/test")
//    public ResponseEntity<List<ShapeDto>> getAllShapesWithAreaBetween(@RequestParam double min, @RequestParam double max) {
//
//        List<Shape> shapeList = shapeManagementService.getByAreaBetween(min, max);
//        List<ShapeDto> shapeDtoList = shapeList.stream().map(x -> shapeFactory.createDto(x))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
//    }
}

//@GetMapping(value = "/parameters")
//    public ResponseEntity<List<ShapeDto>> getByParameters (@RequestParam Map<String, Object> parameters) {
//
//        List<Shape> shapeList = shapeManagementService.getByParameters(parameters);
//
//    List<ShapeDto> shapeDtoList = shapeList.stream()
//                .map(x -> shapeFactory.createDto(x))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
//}
//List<Shape> shapeList = shapeManagementService.getAllByCreatedBy(parameters.get("createdBy"));
//        List<Shape> shapeList = managementServices.get(parameters.get("type")).getAll();
//        List<Shape> shapeList = shapeManagementService.getByArea(0.0,100.0);

