package pl.kurs.geometricfigures.exceptions;

public class BadEntityException extends RuntimeException {

    private Object entity;

    public BadEntityException(String message) {
        super(message);
    }

    public BadEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadEntityException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
