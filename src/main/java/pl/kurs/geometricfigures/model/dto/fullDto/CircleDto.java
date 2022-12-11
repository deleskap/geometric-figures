package pl.kurs.geometricfigures.model.dto.fullDto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CircleDto implements ShapeDto{
    private Long id;
    private String type;
    private double radius;
    private int version;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    private double area;
    private double perimeter;


}
