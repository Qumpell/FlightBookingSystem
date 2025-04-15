package pl.matkan.flightbookingsystem.reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    List<ReservationResponse> getAllReservations();

    Reservation getReservationById(UUID id);

    ReservationResponse updateReservation(UUID id, ReservationRequest reservationRequest);

    void deleteReservation(UUID id);

    ReservationResponse addReservation(ReservationRequest reservationRequest);

    ReservationResponse getReservationResponseById(UUID id);
}
