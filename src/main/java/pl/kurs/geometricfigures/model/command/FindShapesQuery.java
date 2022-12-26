package pl.kurs.geometricfigures.model.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindShapesQuery {
    private Long createdBy;
    private String createdAtFrom;
    private String createdAtTo;
    private String type;
    private Double areaFrom;
    private Double areaTo;
    private Double perimeterFrom;
    private Double perimeterTo;
    private Double radiusFrom;
    private Double radiusTo;
    private Double widthFrom;
    private Double widthTo;
    private Double heightFrom;
    private Double heightTo;


}
