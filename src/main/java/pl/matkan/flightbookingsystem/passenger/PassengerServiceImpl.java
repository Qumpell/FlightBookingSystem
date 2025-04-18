package pl.matkan.flightbookingsystem.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matkan.flightbookingsystem.exception.BadRequestException;
import pl.matkan.flightbookingsystem.exception.PassengerNotFoundException;
import pl.matkan.flightbookingsystem.reservation.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
    }

    @Override
    public Passenger updatePassenger(Long id, PassengerRequest passengerRequest) {
        Passenger passengerToUpdate = getPassengerById(id);

        passengerToUpdate.setFirstname(passengerRequest.firstname());
        passengerToUpdate.setLastname(passengerRequest.lastname());
        passengerToUpdate.setEmail(passengerRequest.email());
        passengerToUpdate.setPhone(passengerRequest.phone());

        return passengerRepository.save(passengerToUpdate);
    }

    @Override
    public void deletePassenger(Long id) {
        Passenger passengerToDelete = getPassengerById(id);

        boolean reservationExists = reservationRepository.existsByPassenger(passengerToDelete);
        if (reservationExists) {
            throw new BadRequestException("Cannot delete passenger with existing reservations.");
        }

        passengerRepository.delete(passengerToDelete);
    }

    @Override
    public Passenger addPassenger(PassengerRequest passengerRequest) {
        Passenger passengerToCreate = new Passenger();
        passengerToCreate.setFirstname(passengerRequest.firstname());
        passengerToCreate.setLastname(passengerRequest.lastname());
        passengerToCreate.setEmail(passengerRequest.email());
        passengerToCreate.setPhone(passengerRequest.phone());

        return passengerRepository.save(passengerToCreate);
    }
}
