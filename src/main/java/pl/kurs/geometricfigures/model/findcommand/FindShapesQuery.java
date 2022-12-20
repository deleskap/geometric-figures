package pl.kurs.geometricfigures.model.findcommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindShapesQuery<T> {


    private Double areaFrom;

    private Double areaTo;

    private String createdBy;

    private Double perimeterFrom;

    private Double perimeterTo;

    private String shapeType;

    private T shapeSpecificParamsFrom;

    private T shapeSpecificParamsTo;
}
