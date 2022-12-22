package pl.kurs.geometricfigures.exceptions;

public class BadEntityException extends RuntimeException {

    private Object entity;

    public BadEntityException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
