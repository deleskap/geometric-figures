package pl.kurs.geometricfigures.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateShapeCommand {
    @NotNull
    @NotBlank
    private String type;

    @NotEmpty
    private Double[] parameters;
}
