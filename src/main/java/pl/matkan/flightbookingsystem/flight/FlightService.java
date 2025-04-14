package pl.matkan.flightbookingsystem.flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlightById(Long id);

    Flight updateFlight(Long id, FlightRequest flightRequest);

    void deleteFlight(Long id);

    Flight addFlight(FlightRequest flightRequest);
}
