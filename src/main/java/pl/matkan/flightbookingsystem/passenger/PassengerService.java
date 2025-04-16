package pl.matkan.flightbookingsystem.passenger;


import java.util.List;


public interface PassengerService {

    List<Passenger> getAllPassengers();

    Passenger getPassengerById(Long id);

    Passenger updatePassenger(Long id, PassengerRequest passengerRequest);

    void deletePassenger(Long id);

    Passenger addPassenger(PassengerRequest passengerRequest);
}
