package pl.matkan.flightbookingsystem.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matkan.flightbookingsystem.exception.BadRequestException;
import pl.matkan.flightbookingsystem.exception.FlightNotFoundException;
import pl.matkan.flightbookingsystem.exception.PassengerNotFoundException;
import pl.matkan.flightbookingsystem.exception.ReservationNotFoundException;
import pl.matkan.flightbookingsystem.flight.Flight;
import pl.matkan.flightbookingsystem.flight.FlightRepository;
import pl.matkan.flightbookingsystem.passenger.Passenger;
import pl.matkan.flightbookingsystem.passenger.PassengerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    @Override
    public List<ReservationResponse> getAllReservations() {
        return ReservationResponseMapper.INSTANCE.toDtoList(reservationRepository.findAll());
    }

    @Override
    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

    }

    @Override
    public ReservationResponse getReservationResponseById(UUID id) {
        Reservation reservation = getReservationById(id);
        return ReservationResponseMapper.INSTANCE.toDto(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(UUID id, ReservationRequest reservationRequest) {
        Flight flight = flightRepository.findById(reservationRequest.flightId())
                .orElseThrow(() -> new FlightNotFoundException(reservationRequest.flightId()));

        Passenger passenger = passengerRepository.findById(reservationRequest.passengerId())
                .orElseThrow(() -> new PassengerNotFoundException(reservationRequest.passengerId()));

        boolean isSeatTaken = reservationRepository.existsByFlightAndSeatNumber(flight, reservationRequest.seatNumber());
        Reservation reservationToUpdate = getReservationById(id);

        if (isSeatTaken && !reservationToUpdate.getSeatNumber().equals(reservationRequest.seatNumber())) {
            throw new BadRequestException("Provided seat is already taken for this flight");
        }

        reservationToUpdate.setSeatNumber(reservationRequest.seatNumber());
        reservationToUpdate.setFlight(flight);
        reservationToUpdate.setPassenger(passenger);
        reservationToUpdate.setDepartureDone(reservationRequest.departureDone());

        Reservation updated = reservationRepository.save(reservationToUpdate);
        return ReservationResponseMapper.INSTANCE.toDto(updated);
    }

    @Override
    public void deleteReservation(UUID id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        Flight flight = flightRepository.findById(reservationRequest.flightId())
                .orElseThrow(() -> new FlightNotFoundException(reservationRequest.flightId()));

        Passenger passenger = passengerRepository.findById(reservationRequest.passengerId())
                .orElseThrow(() -> new PassengerNotFoundException(reservationRequest.passengerId()));

        boolean isSeatTaken = reservationRepository.existsByFlightAndSeatNumber(flight, reservationRequest.seatNumber());
        if (isSeatTaken) {
            throw new BadRequestException("Provided seat is already taken for this flight");
        }

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setSeatNumber(reservationRequest.seatNumber());
        reservation.setDepartureDone(reservationRequest.departureDone());

        Reservation created =  reservationRepository.save(reservation);
        return ReservationResponseMapper.INSTANCE.toDto(created);
    }

}
