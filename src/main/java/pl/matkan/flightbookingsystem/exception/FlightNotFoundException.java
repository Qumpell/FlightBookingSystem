package pl.matkan.flightbookingsystem.exception;

public class FlightNotFoundException extends ObjectNotFoundException{
    public FlightNotFoundException(Long id) {
        super("Flight with id " + id + " not found");
    }
}
