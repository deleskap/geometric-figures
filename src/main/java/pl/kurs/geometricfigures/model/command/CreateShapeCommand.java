package pl.kurs.geometricfigures.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateShapeCommand {
    @NotNull(message = "Type can't be null")
    @NotBlank(message = "Type can't be blank")
    private String type;

    @NotEmpty(message = "Parameters can't be empty")
    private Double[] parameters;
}
