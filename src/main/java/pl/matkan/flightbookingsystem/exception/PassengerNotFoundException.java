package pl.matkan.flightbookingsystem.exception;

public class PassengerNotFoundException extends ObjectNotFoundException {
    public PassengerNotFoundException(Long id) {
        super("Passenger with id " + id + " not found");
    }
}
