package pl.kurs.geometricfigures.exceptions;

import javax.persistence.EntityNotFoundException;

public class SpecificEntityNotFoundException extends EntityNotFoundException {
    private Long idNotFound;

    public SpecificEntityNotFoundException(Long idNotFound) {
        this.idNotFound = idNotFound;
    }

    public Long getIdNotFound() {
        return idNotFound;
    }
}
