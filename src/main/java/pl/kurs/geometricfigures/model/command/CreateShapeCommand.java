package pl.kurs.geometricfigures.model.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CreateShapeCommand {
    private String type;
    private Double[] parameters;
}
