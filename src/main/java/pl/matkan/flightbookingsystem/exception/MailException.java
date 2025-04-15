package pl.matkan.flightbookingsystem.exception;

import lombok.Getter;
import java.time.Instant;

@Getter
public class MailException extends RuntimeException{

    private final Instant timestamp;
    public MailException(String message) {
        super(message);
        this.timestamp = Instant.now();
    }
}
