package pl.matkan.flightbookingsystem.passenger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.matkan.flightbookingsystem.exception.BadRequestException;
import pl.matkan.flightbookingsystem.exception.PassengerNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {


   @Mock
   private PassengerService service;

   @InjectMocks
   private PassengerController controller;

   private PassengerRequest request;

   private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger(
                1L,
                "Anna",
                "Kowalska",
                "anna.kowalska@example.com",
                "123456789");

        request = new PassengerRequest(
                passenger.getFirstname(),
                passenger.getLastname(),
                passenger.getEmail(),
                passenger.getPhone()
        );

    }

    @Test
    void shouldCreatePassenger() {
        // Given
        when(service.addPassenger(request)).thenReturn(passenger);

        // When
        ResponseEntity<Passenger> response = controller.createPassenger(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(passenger, response.getBody());
    }

    @Test
    void shouldReturnAllPassengers() {
        // Given
        List<Passenger> passengers = List.of(passenger);
        when(service.getAllPassengers()).thenReturn(passengers);

        // When
        ResponseEntity<List<Passenger>> response = controller.getAllPassengers();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(passengers, response.getBody());
    }

    @Test
    void shouldReturnPassengerById() {
        // Given
        when(service.getPassengerById(1L)).thenReturn(passenger);

        // When
        ResponseEntity<Passenger> response = controller.getPassengerById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(passenger, response.getBody());
    }

    @Test
    void shouldUpdatePassenger() {
        // Given
        when(service.updatePassenger(1L, request)).thenReturn(passenger);

        // When
        ResponseEntity<Passenger> response = controller.updatePassenger(1L, request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(passenger, response.getBody());
    }

    @Test
    void shouldDeletePassenger() {
        // When
        ResponseEntity<Void> response = controller.deletePassenger(1L);

        // Then
        verify(service).deletePassenger(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingNonExistentPassenger() {
        // Given
        Long passengerId = 99L;
        when(service.getPassengerById(passengerId))
                .thenThrow(new PassengerNotFoundException(passengerId));

        // When
        PassengerNotFoundException ex = assertThrows(
                PassengerNotFoundException.class,
                () -> controller.getPassengerById(passengerId)
        );

        // Then
        assertEquals("Passenger with id 99 not found", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingNonExistentPassenger() {
        // Given
        Long passengerId = 99L;
        when(service.updatePassenger(passengerId, request))
                .thenThrow(new PassengerNotFoundException(passengerId));

        // When
        PassengerNotFoundException ex = assertThrows(
                PassengerNotFoundException.class,
                () -> controller.updatePassenger(passengerId, request)
        );

        // Then
        assertEquals("Passenger with id 99 not found", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingNonExistentPassenger() {
        // Given
        Long passengerId = 99L;
        doThrow(new PassengerNotFoundException(passengerId))
                .when(service).deletePassenger(passengerId);

        // When
        PassengerNotFoundException ex = assertThrows(
                PassengerNotFoundException.class,
                () -> controller.deletePassenger(passengerId)
        );

        // Then
        assertEquals("Passenger with id 99 not found", ex.getMessage());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenDeletingPassengerWithReservations() {
        // Given
        doThrow(new BadRequestException("Cannot delete passenger with existing reservations."))
                .when(service).deletePassenger(1L);

        // When
        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> controller.deletePassenger(1L)
        );

        // Then
        assertEquals("Cannot delete passenger with existing reservations.", ex.getMessage());
    }

}