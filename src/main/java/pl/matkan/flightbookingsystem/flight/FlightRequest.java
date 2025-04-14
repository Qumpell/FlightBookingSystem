package pl.matkan.flightbookingsystem.flight;

import java.time.Duration;
import java.time.LocalDateTime;

public record FlightRequest(
        String flightNumber,
        String departureLocation,
        String arrivalLocation,
        Duration duration,
        boolean roundTrip,
        LocalDateTime departureDateTime
) {
}
