package pl.kurs.geometricfigures.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.factory.ShapeFactory;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.Square;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.service.ShapeManagementService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/shapes")
@AllArgsConstructor
public class ShapeController {
    private final ShapeManagementService shapeManagementService;
    private final ModelMapper modelMapper;
    private final ShapeFactory shapeFactory;


    @PostMapping("/init")
    public void init(){
        for (int i=0; i<1000; i++){
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

    @PostMapping
    public ResponseEntity<ShapeDto> createShape(@RequestBody CreateShapeCommand command){
        Shape shape = shapeFactory.create(command);
        shape = shapeManagementService.add(shape);
        ShapeDto dto = shapeFactory.createDto(shape);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



}
