package pl.matkan.flightbookingsystem.exception;

import java.time.Instant;

public record ApiError(
        int code,
        String error,
        String message,
        String path,
        Instant timestamp
) {
}
