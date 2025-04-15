package pl.matkan.flightbookingsystem.reservation;

import java.util.UUID;

public record ReservationResponse(
        UUID reservationNumber,
        String flightNumber,
        String seatNumber,
        String passengerName,
        String email,
        String phone,
        boolean departureDone
) {
}
