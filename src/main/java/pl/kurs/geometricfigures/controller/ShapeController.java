package pl.kurs.geometricfigures.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.factory.ShapeFactory;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeView;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.command.FindShapesQuery;
import pl.kurs.geometricfigures.model.command.UpdateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.model.shapechange.ShapeChange;
import pl.kurs.geometricfigures.model.shapechange.ShapeChangeDto;
import pl.kurs.geometricfigures.repository.ShapeChangeRepository;
import pl.kurs.geometricfigures.service.ShapeManagementService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/shapes")
public class ShapeController {
    private final ShapeManagementService shapeManagementService;
    private final ShapeFactory shapeFactory;
    private final ShapeChangeRepository shapeChangeRepository;
    private final ModelMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CREATOR')")
    public ResponseEntity<ShapeDto> createShape(@RequestBody @Valid CreateShapeCommand command) {
        Shape shape = shapeFactory.createShape(command);
        shape = shapeManagementService.add(shape);
        ShapeDto dto = shapeFactory.createDto(shape);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @shapeManagementService.isShapeCreator(#id, principal)")
    public ResponseEntity<ShapeDto> updateShape(@PathVariable Long id, @RequestBody UpdateShapeCommand command) throws NoSuchFieldException, IllegalAccessException {
        Shape returnedShape = shapeManagementService.edit(id, command);
        ShapeDto dto = shapeFactory.createDto(returnedShape);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ShapeDto>> getShapes(FindShapesQuery query) {
        List<ShapeView> shapeList = shapeManagementService.findShapesByParameters(query);
        List<ShapeDto> shapeDtoList = shapeList.stream().map(shapeFactory::createDtoFromView)
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }

    @GetMapping(("/{id}/changes"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or @shapeManagementService.isShapeCreator(#id, principal)")
    public ResponseEntity<List<ShapeChangeDto>> getChangesById(@PathVariable Long id) {
        List<ShapeChange> shapeChangeList = shapeChangeRepository.findByShapeId(id);
        List<ShapeChangeDto> changeDtoList = shapeChangeList.stream().map(x -> mapper.map(x, ShapeChangeDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(changeDtoList, HttpStatus.OK);
    }
}