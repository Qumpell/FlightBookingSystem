package pl.matkan.flightbookingsystem.reservation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

        @NotNull(message = "Flight ID cannot be null")
        Long flightId,

        @NotNull(message = "Passenger ID cannot be null")
        Long passengerId,

        @NotBlank(message = "Seat number cannot be blank")
        String seatNumber,

        @NotNull(message = "Departure status must be provided")
        boolean departureDone
) {
}
