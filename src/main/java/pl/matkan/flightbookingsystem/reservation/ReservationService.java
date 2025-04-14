package pl.matkan.flightbookingsystem.reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    List<Reservation> getAllReservations();

    Reservation getReservationById(UUID id);

    Reservation updateReservation(UUID id, ReservationRequest reservationRequest);

    void deleteReservation(UUID id);

    Reservation addReservation(ReservationRequest reservationRequest);
}
