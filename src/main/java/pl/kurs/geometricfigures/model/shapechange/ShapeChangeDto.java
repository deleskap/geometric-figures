package pl.kurs.geometricfigures.model.shapechange;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import pl.kurs.geometricfigures.model.Shape;
import pl.kurs.geometricfigures.model.dto.fullDto.ShapeDto;
import pl.kurs.geometricfigures.security.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;
@Value
@Builder
public class ShapeChangeDto implements ShapeDto {
    private Long id;
    private String fieldName;
    private Double oldValue;
    private Double newValue;
    private String modifiedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modificationTime;

}