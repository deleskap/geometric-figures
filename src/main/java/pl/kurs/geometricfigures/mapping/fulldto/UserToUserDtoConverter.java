package pl.kurs.geometricfigures.mapping.fulldto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.model.shapechange.ShapeChange;
import pl.kurs.geometricfigures.model.shapechange.ShapeChangeDto;
import pl.kurs.geometricfigures.security.AppRole;
import pl.kurs.geometricfigures.security.AppUser;
import pl.kurs.geometricfigures.security.UserDto;

import java.util.stream.Collectors;

@Service
public class UserToUserDtoConverter implements Converter<AppUser, UserDto>, ShapeToShapeDtoConverter {
    @Override
    public UserDto convert(MappingContext<AppUser, UserDto> mappingContext) {
        AppUser source = mappingContext.getSource();

        return UserDto.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password("*SECRET*")
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .roles(source.getRoles().stream().map(AppRole::getAuthority).collect(Collectors.toList()))
                .build();
    }

}
