package pl.kurs.geometricfigures.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.factory.ShapeFactory;
import pl.kurs.geometricfigures.factory.creators.ShapeCreator;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.service.GenericManagementService;
import pl.kurs.geometricfigures.service.IManagementService;
import pl.kurs.geometricfigures.service.ShapeManagementService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/shapes")
//@AllArgsConstructor
public class ShapeController {
    private final Set<GenericManagementService> managementServicesSet;
    private Map<String, GenericManagementService> managementServices = new HashMap<>();
    private final ShapeManagementService shapeManagementService;
    private final ModelMapper modelMapper;
    private final ShapeFactory shapeFactory;

    public ShapeController(Set<GenericManagementService> managementServicesSet, ShapeManagementService shapeManagementService, ModelMapper modelMapper, ShapeFactory shapeFactory) {
        this.managementServicesSet=managementServicesSet;
        this.shapeManagementService = shapeManagementService;
        this.modelMapper = modelMapper;
        this.shapeFactory = shapeFactory;
    }

    @PostConstruct
    public void postConstruct(){
        this.managementServices = managementServicesSet.stream()
                .collect(Collectors.toMap(x -> x.getType(), Function.identity()));
    }



    @PostMapping("/init")
    public void init(){
        for (int i=0; i<100; i++){
        shapeManagementService.add(new Circle(0.0+i));
        shapeManagementService.add(new Rectangle(0.0+i, 0.0+i*1.5));
        shapeManagementService.add(new Square(0.0+i));
        }
    }

    @GetMapping
    public ResponseEntity<List<ShapeDto>> getAllShapes (){
        List<ShapeDto> shapeDtoList = shapeManagementService.getAll()
                .stream()
                .map(x -> shapeFactory.createDto(x))
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/parameters")
    public ResponseEntity<List<ShapeDto>> getByParameters (@RequestParam Map<String, Object> parameters){
        List<Shape> shapeList;

        //List<Shape> shapeList = shapeManagementService.getAllByCreatedBy(parameters.get("createdBy"));
//        List<Shape> shapeList = managementServices.get(parameters.get("type")).getAll();
//        List<Shape> shapeList = shapeManagementService.getByArea(0.0,100.0);

        if (parameters.containsKey("type")&parameters.size()==1){
            shapeList = managementServices.get(parameters.get("type")).getAll();
        } else if (parameters.containsKey("type")&parameters.size()>1){
            shapeList = managementServices.get(parameters.get("type")).getAllByParameters(parameters);
        }


        else{
            shapeList = shapeManagementService.getAll();
        }

        if(parameters.containsKey("areaFrom")&parameters.containsKey("areaTo")){
            shapeList.stream()
                    .filter(x -> x.getArea()<Double.valueOf(parameters.get("areaTo").toString()))
                    .filter(x -> x.getArea()>Double.valueOf(parameters.get("areaFrom").toString()))
                    .collect(Collectors.toList());
        }
        if(parameters.containsKey("perimeterFrom")&parameters.containsKey("perimeterTo")){
            shapeList.stream()
                    .filter(x -> x.getPerimeter()>Double.valueOf(parameters.get("perimeterFrom").toString()))
                    .filter(x -> x.getPerimeter()>Double.valueOf(parameters.get("perimeterTo").toString()))
                    .collect(Collectors.toList());
        }
        if(parameters.containsKey("createdBy")){
            shapeList.stream()
                    .filter(x -> x.getCreatedBy()==parameters.get("createdBy").toString())
                    .collect(Collectors.toList());
        }

        List<ShapeDto> shapeDtoList = shapeList.stream()
                .map(x -> shapeFactory.createDto(x))
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }

//    @GetMapping(value = "/parameters")
//    public ResponseEntity<List<ShapeDto>> getByParameters (@RequestParam(name = "createdBy", required = false) String createdBy,
//                                                           @RequestParam(name = "type", required = false) String type,
//                                                           @RequestParam(name = "areaFrom", required = false) Double areaFrom,
//                                                           @RequestParam(name = "areaTo", required = false) Double areaTo,
//                                                           @RequestParam(name = "perimeterFrom", required = false) Double perimeterFrom,
//                                                           @RequestParam(name = "perimeterTo", required = false) Double perimeterTo,
//                                                           @RequestParam(name = "widthFrom", required = false) Double widthFrom,
//                                                           @RequestParam(name = "widthTo", required = false) Double widthTo,
//                                                           @RequestParam(name = "radiusFrom", required = false) Double radiusFrom,
//                                                           @RequestParam(name = "radiusTo", required = false) Double radiusTo){
//        List<ShapeDto> shapeDtoList = new ArrayList<>();
//
//        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<ShapeDto> createShape(@RequestBody CreateShapeCommand command){
        Shape shape = shapeFactory.createShape(command);
        shape = shapeManagementService.add(shape);
        ShapeDto dto = shapeFactory.createDto(shape);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



}
