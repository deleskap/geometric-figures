package pl.kurs.geometricfigures.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.model.dto.fullDto.SquareDto;

import java.util.Locale;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Square extends Shape {
    @Column(nullable = false)
    private double width;

    @Override
    public double getArea() {
        return width*width;
    }

    @Override
    public double getPerimeter() {
        return 4*width;
    }
}
