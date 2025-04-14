package pl.matkan.flightbookingsystem.flight;

import pl.matkan.flightbookingsystem.passenger.Passenger;
import pl.matkan.flightbookingsystem.passenger.PassengerRequest;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlightById(Long id);

    Flight updateFlight(Long id, FlightRequest flightRequest);

    void deleteFlight(Long id);

    Flight addFlight(FlightRequest flightRequest);
}
