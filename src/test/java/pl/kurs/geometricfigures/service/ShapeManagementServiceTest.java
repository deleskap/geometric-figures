package pl.kurs.geometricfigures.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.kurs.geometricfigures.GeometricFiguresApplication;
import pl.kurs.geometricfigures.model.Circle;
import pl.kurs.geometricfigures.model.Rectangle;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.Square;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {GeometricFiguresApplication.class, ShapeManagementService.class}, properties = "src/test/resources/application.properties")
@AutoConfigureMockMvc
@Sql(scripts = "classpath:db_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ShapeManagementServiceTest {

    @Autowired
    ShapeManagementService service;

    @Test
    void shouldReturnSquares() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Square");
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() != 0;
        for (Object obj : returnedShapes) {
            assert obj instanceof Square;
        }
    }

    @Test
    void shouldReturnCircles() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Circle");
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() != 0;
        for (Object obj : returnedShapes) {
            assert obj instanceof Circle;
        }
    }

    @Test
    void shouldReturnRectangles() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Rectangle");
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() != 0;
        for (Object obj : returnedShapes) {
            assert obj instanceof Rectangle;
        }
    }

    @Test
    void shouldReturn2RectanglesWhichWidthIsFrom3To4() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Rectangle");
        params.put("widthFrom", 3.0);
        params.put("widthTo", 4.0);
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() == 2;
        for (Object obj : returnedShapes) {
            assert obj instanceof Rectangle;
            assert ((Rectangle) obj).getWidth() >= 3.0 && ((Rectangle) obj).getWidth() <= 4.0;
        }
    }

    @Test
    void shouldReturn2CirclesWhichRadiusIsGreaterThan3_5() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Circle");
        params.put("radiusFrom", 3.5);
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() == 2;
        for (Object obj : returnedShapes) {
            assert obj instanceof Circle;
            assert ((Circle) obj).getRadius() >= 3.5;
        }
    }

    @Test
    void shouldReturnRectangleCreatedByCreatorUserForTests() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("createdBy", "creatorUserForTests");
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() != 0;
        for (Object obj : returnedShapes) {
            assert ((Shape) obj).getCreatedBy().equals("creatorUserForTests");
        }
    }

    @Test
    void shouldReturn2RectanglesWithAreaBetween5And10() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Rectangle");
        params.put("areaFrom", 5.0);
        params.put("areaTo", 10.0);
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() == 2;
        for (Object obj : returnedShapes) {
            assert ((Rectangle) obj).getArea() <= 10.0 && ((Rectangle) obj).getArea() >= 5.0;
        }
    }

    @Test
    void shouldReturn2SquaresWithPerimeterBetween10And20() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Square");
        params.put("perimeterFrom", 10.0);
        params.put("perimeterTo", 20.0);
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() == 2;
        for (Object obj : returnedShapes) {
            assert ((Square) obj).getArea() <= 20.0 && ((Square) obj).getArea() >= 10.0;
        }
    }

    @Test
    void shouldReturnShapesCreatedBefore2022() throws NoSuchFieldException, ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("createdAtTo", "2022-12-14");
        List<Shape> returnedShapes = (List<Shape>) service.findShapes(params);
        assert returnedShapes.size() != 0;
        for (Object obj : returnedShapes) {
            assert ((Shape) obj).getCreatedAt().isBefore(ChronoLocalDateTime.from(LocalDateTime.of(2022, 1, 1, 12, 20)));
        }
    }
}