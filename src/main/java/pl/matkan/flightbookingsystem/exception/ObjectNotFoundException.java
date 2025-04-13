package pl.matkan.flightbookingsystem.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ObjectNotFoundException extends RuntimeException {
    private final Instant timestamp;

    public ObjectNotFoundException(String message) {
        super(message);
        this.timestamp = Instant.now();
    }
}
