package pl.kurs.geometricfigures.model.command;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateShapeCommand {
    private String fieldName;
    private Double value;
}
