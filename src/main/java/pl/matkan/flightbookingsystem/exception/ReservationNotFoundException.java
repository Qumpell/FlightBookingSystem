package pl.matkan.flightbookingsystem.exception;

import java.util.UUID;

public class ReservationNotFoundException extends ObjectNotFoundException{
    public ReservationNotFoundException(UUID id) {
        super("Reservation with id " + id + " not found");
    }
}
