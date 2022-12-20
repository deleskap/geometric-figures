package pl.kurs.geometricfigures.exceptions;

public class BadIdException extends RuntimeException {

    private Long badId;


    public BadIdException(Long badId) {
        this.badId = badId;
    }

    public BadIdException(String message, Long badId) {
        super(message);
        this.badId = badId;
    }

    public BadIdException(String message, Throwable cause, Long badId) {
        super(message, cause);
        this.badId = badId;
    }

    public Long getBadId() {
        return badId;
    }
}
