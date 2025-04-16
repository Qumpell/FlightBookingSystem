package pl.matkan.flightbookingsystem.util;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.matkan.flightbookingsystem.flight.Flight;
import pl.matkan.flightbookingsystem.flight.FlightRepository;
import pl.matkan.flightbookingsystem.passenger.Passenger;
import pl.matkan.flightbookingsystem.passenger.PassengerRepository;
import pl.matkan.flightbookingsystem.reservation.Reservation;
import pl.matkan.flightbookingsystem.reservation.ReservationRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;

    @PostConstruct
    @Transactional
    public void initData() {

        List<Flight> flights = List.of(
                new Flight(null, "FL1001", "Warsaw", "Paris", Duration.ofHours(2), false, LocalDateTime.now().plusDays(1)),
                new Flight(null, "FL1002", "Berlin", "Madrid", Duration.ofHours(3), true, LocalDateTime.now().plusDays(2)),
                new Flight(null, "FL1003", "London", "Rome", Duration.ofHours(2).plusMinutes(30), false, LocalDateTime.now().plusDays(3)),
                new Flight(null, "FL1004", "Amsterdam", "Vienna", Duration.ofHours(1).plusMinutes(45), true, LocalDateTime.now().plusDays(4)),
                new Flight(null, "FL1005", "Prague", "Lisbon", Duration.ofHours(4), false, LocalDateTime.now().plusDays(5))
        );
        if (flightRepository.count() == 0) {
            flightRepository.saveAll(flights);
        }


        List<Passenger> passengers = List.of(
                new Passenger(null, "Anna", "Kowalska", "anna.kowalska@example.com", "123456789"),
                new Passenger(null, "John", "Doe", "john.doe@example.com", "987654321"),
                new Passenger(null, "Maria", "Nowak", "maria.nowak@example.com", "456789123"),
                new Passenger(null, "Lukas", "Smith", "lukas.smith@example.com", "789123456"),
                new Passenger(null, "Eva", "Brown", "eva.brown@example.com", "321654987")
        );
        if (passengerRepository.count() == 0) {
            passengerRepository.saveAll(passengers);
        }


        List<Reservation> reservations = List.of(
                new Reservation(null, flights.get(0), passengers.get(0), "1A", false),
                new Reservation(null, flights.get(1), passengers.get(1), "2B", true),
                new Reservation(null, flights.get(2), passengers.get(2), "3C", false),
                new Reservation(null, flights.get(3), passengers.get(3), "4D", false),
                new Reservation(null, flights.get(4), passengers.get(4), "5E", true)
        );
        if (reservationRepository.count() == 0) {
            reservationRepository.saveAll(reservations);
        }
    }
}
