package pl.matkan.flightbookingsystem.reservation;


public record ReservationRequest(
        Long flightId,
        Long passengerId,
        String seatNumber,
        boolean departureDone
) {
}
