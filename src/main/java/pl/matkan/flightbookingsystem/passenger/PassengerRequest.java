package pl.matkan.flightbookingsystem.passenger;

public record PassengerRequest(
        String firstname,
        String lastname,
        String email,
        String phone
) {
}
