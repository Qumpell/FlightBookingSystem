package pl.matkan.flightbookingsystem.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matkan.flightbookingsystem.flight.Flight;
import pl.matkan.flightbookingsystem.passenger.Passenger;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByFlightAndSeatNumber(Flight flight, String seatNumber);

    boolean existsByFlight(Flight flight);

    boolean existsByPassenger(Passenger passenger);
}
