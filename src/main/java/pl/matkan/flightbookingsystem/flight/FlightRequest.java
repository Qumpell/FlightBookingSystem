package pl.matkan.flightbookingsystem.flight;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;
import java.time.LocalDateTime;

public record FlightRequest(
        @NotBlank(message = "Flight number is required")
        String flightNumber,

        @NotBlank(message = "Departure location is required")
        String departureLocation,

        @NotBlank(message = "Arrival location is required")
        String arrivalLocation,

        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be positive")
        Duration duration,

        boolean roundTrip,

        @NotNull(message = "Departure date and time is required")
        @Future(message = "Departure time must be in the future")
        LocalDateTime departureDateTime
) {
}
