package pl.kurs.geometricfigures.model.command;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindShapesQuery {

    private Double areaFrom;

    private Double areaTo;

    private String createdBy;

    private Double perimeterFrom;

    private Double perimeterTo;

    private String shapeType;

    private Map<String, Object> otherCriteria;
}
