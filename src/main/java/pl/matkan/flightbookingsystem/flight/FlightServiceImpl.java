package pl.matkan.flightbookingsystem.flight;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matkan.flightbookingsystem.exception.FlightNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl  implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    @Override
    public Flight updateFlight(Long id, FlightRequest flightRequest) {

        Flight flightToUpdate = getFlightById(id);
        flightToUpdate.setFlightNumber(flightRequest.flightNumber());
        flightToUpdate.setDepartureLocation(flightRequest.departureLocation());
        flightToUpdate.setArrivalLocation(flightRequest.arrivalLocation());
        flightToUpdate.setDuration(flightRequest.duration());
        flightToUpdate.setRoundTrip(flightRequest.roundTrip());
        flightToUpdate.setDepartureDateTime(flightRequest.departureDateTime());

        return flightRepository.save(flightToUpdate);
    }

    @Override
    public void deleteFlight(Long id) {
        Flight flightToDelete = getFlightById(id);
        flightRepository.delete(flightToDelete);
    }

    @Override
    public Flight addFlight(FlightRequest flightRequest) {

        Flight flightToAdd = new Flight();
        flightToAdd.setFlightNumber(flightRequest.flightNumber());
        flightToAdd.setDepartureLocation(flightRequest.departureLocation());
        flightToAdd.setArrivalLocation(flightRequest.arrivalLocation());
        flightToAdd.setDuration(flightRequest.duration());
        flightToAdd.setRoundTrip(flightRequest.roundTrip());
        flightToAdd.setDepartureDateTime(flightRequest.departureDateTime());

        return flightRepository.save(flightToAdd);
    }
}
