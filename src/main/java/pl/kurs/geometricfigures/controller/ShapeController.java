package pl.kurs.geometricfigures.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.factory.ShapeFactory;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.ShapeChange;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;
import pl.kurs.geometricfigures.model.command.UpdateShapeCommand;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.repository.ShapeChangeRepository;
import pl.kurs.geometricfigures.repository.ShapeRepository;
import pl.kurs.geometricfigures.security.AppRole;
import pl.kurs.geometricfigures.security.AppUser;
import pl.kurs.geometricfigures.service.ShapeManagementService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/shapes")
public class ShapeController {
    private final ShapeManagementService shapeManagementService;
    private final ShapeFactory shapeFactory;
    private final ShapeChangeRepository shapeChangeRepository;
    private final ShapeRepository shapeRepository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CREATOR')")
    public ResponseEntity<ShapeDto> createShape(@RequestBody @Valid CreateShapeCommand command) {
        Shape shape = shapeFactory.createShape(command);
        shape = shapeManagementService.add(shape);
        ShapeDto dto = shapeFactory.createDto(shape);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    //Chciałem skorzystać z tej adnotacji, ale wyrzuca mi błąd, że shapeManagementService jest nullem
//    @PreAuthorize("hasRole('ROLE_ADMIN') or #shapeManagementService.isShapeCreator(#id, principal)")
    @Transactional
    public ResponseEntity<ShapeDto> updateShape(@PathVariable Long id, @RequestBody UpdateShapeCommand command) throws NoSuchFieldException, IllegalAccessException {
        Shape returnedShape = shapeManagementService.get(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        boolean adminRole = user.getRoles().stream().map(AppRole::getAuthority).anyMatch(x -> x.equals("ROLE_ADMIN"));
        boolean creatorOfShape = shapeManagementService.isShapeCreator(id, user);
        if (!adminRole && !creatorOfShape) {
            throw new AccessDeniedException("You do not have sufficient privileges to access this resource.");
        }
        ShapeChange change = new ShapeChange();
        Field field = returnedShape.getClass().getDeclaredField(command.getFieldName());
        field.setAccessible(true);
        change.setShape(returnedShape);
        change.setFieldName(command.getFieldName());
        change.setNewValue(command.getValue());
        change.setOldValue((Double) field.get(returnedShape));
        field.set(returnedShape, command.getValue());
        returnedShape.setVersion(returnedShape.getVersion() + 1);
        shapeChangeRepository.saveAndFlush(change);
        ShapeDto dto = shapeFactory.createDto(returnedShape);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<ShapeDto>> getShapesByParameters(@RequestParam Map<String, Object> parameters) throws ClassNotFoundException {

        List<Shape> shapeList = shapeManagementService.findShapes(parameters);
        System.out.println(shapeList);
        List<ShapeDto> shapeDtoList = shapeList.stream().map(x -> shapeFactory.createDto(x))
                .collect(Collectors.toList());
        return new ResponseEntity<>(shapeDtoList, HttpStatus.OK);
    }
}