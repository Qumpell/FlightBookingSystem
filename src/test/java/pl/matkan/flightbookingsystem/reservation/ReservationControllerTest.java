package pl.matkan.flightbookingsystem.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.matkan.flightbookingsystem.exception.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService service;

    @InjectMocks
    private ReservationController controller;

    private ReservationRequest request;
    private ReservationResponse response;
    private UUID reservationId;

    @BeforeEach
    void setUp() {
        reservationId = UUID.randomUUID();
        request = new ReservationRequest(
                1L,
                1L,
                "1A",
                false
        );
        response = new ReservationResponse(
                reservationId,
                "FL1001",
                "1A",
                "Anna Kowalska",
                "anna.kowalska@example.com",
                "123456789",
                LocalDateTime.now().plusDays(1),
                false
        );
    }
    @Test
    void shouldCreateReservation() {
        // Given
        when(service.addReservation(request)).thenReturn(response);

        // When
        ResponseEntity<ReservationResponse> result = controller.createReservation(request);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldReturnAllReservations() {
        // Given
        List<ReservationResponse> reservations = List.of(response);
        when(service.getAllReservations()).thenReturn(reservations);

        // When
        ResponseEntity<List<ReservationResponse>> result = controller.getAllReservations();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reservations, result.getBody());
    }

    @Test
    void shouldReturnReservationById() {
        // Given
        when(service.getReservationResponseById(reservationId)).thenReturn(response);

        // When
        ResponseEntity<ReservationResponse> result = controller.getReservationById(reservationId);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldUpdateReservation() {
        // Given
        when(service.updateReservation(reservationId, request)).thenReturn(response);

        // When
        ResponseEntity<ReservationResponse> result = controller.updateReservation(reservationId, request);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldDeleteReservation() {
        // When
        ResponseEntity<Void> result = controller.deleteReservation(reservationId);

        // Then
        verify(service).deleteReservation(reservationId);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingNonExistentReservation() {
        // Given
        when(service.getReservationResponseById(reservationId))
                .thenThrow(new ReservationNotFoundException(reservationId));

        // When
        ReservationNotFoundException ex = assertThrows(
                ReservationNotFoundException.class,
                () -> controller.getReservationById(reservationId)
        );

        // Then
        assertEquals("Reservation with id " + reservationId + " not found", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingNonExistentReservation() {
        // Given
        when(service.updateReservation(reservationId, request))
                .thenThrow(new ReservationNotFoundException(reservationId));

        // When
        ReservationNotFoundException ex = assertThrows(
                ReservationNotFoundException.class,
                () -> controller.updateReservation(reservationId, request)
        );

        // Then
        assertEquals("Reservation with id " + reservationId + " not found", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingNonExistentReservation() {
        // Given
        doThrow(new ReservationNotFoundException(reservationId))
                .when(service).deleteReservation(reservationId);

        // When
        ReservationNotFoundException ex = assertThrows(
                ReservationNotFoundException.class,
                () -> controller.deleteReservation(reservationId)
        );

        // Then
        assertEquals("Reservation with id " + reservationId + " not found", ex.getMessage());
    }

    @Test
    void shouldThrowFlightNotFoundExceptionWhenFlightDoesNotExist_updateReservation() {
        // Given
        when(service.updateReservation(reservationId, request))
                .thenThrow(new FlightNotFoundException(request.flightId()));

        // When
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> controller.updateReservation(reservationId, request)
        );

        // Then
        assertEquals("Flight with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldThrowPassengerNotFoundExceptionWhenPassengerDoesNotExist_updateReservation() {
        // Given
        when(service.updateReservation(reservationId, request))
                .thenThrow(new PassengerNotFoundException(request.passengerId()));

        // When
        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> controller.updateReservation(reservationId, request)
        );

        // Then
        assertEquals("Passenger with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenSeatIsTaken_updateReservation() {
        // Given
        when(service.updateReservation(reservationId, request))
                .thenThrow(new BadRequestException("Provided seat is already taken for this flight"));

        // When
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> controller.updateReservation(reservationId, request)
        );

        // Then
        assertEquals("Provided seat is already taken for this flight", exception.getMessage());
    }

    @Test
    void shouldThrowFlightNotFoundExceptionWhenFlightDoesNotExist_addReservation() {
        // Given
        when(service.addReservation(request))
                .thenThrow(new FlightNotFoundException(request.flightId()));

        // When
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> controller.createReservation(request)
        );

        // Then
        assertEquals("Flight with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldThrowPassengerNotFoundExceptionWhenPassengerDoesNotExist_addReservation() {
        // Given
        when(service.addReservation(request))
                .thenThrow(new PassengerNotFoundException(request.passengerId()));

        // When
        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> controller.createReservation(request)
        );

        // Then
        assertEquals("Passenger with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenSeatIsTaken_addReservation() {
        // Given
        when(service.addReservation(request))
                .thenThrow(new BadRequestException("Provided seat is already taken for this flight"));

        // When
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> controller.createReservation(request)
        );

        // Then
        assertEquals("Provided seat is already taken for this flight", exception.getMessage());
    }

    @Test
    void shouldThrowMailExceptionWhenEmailSendingFails_addReservation() {
        // Given
        when(service.addReservation(request))
                .thenThrow(new MailException("Failed to send reservation email"));

        // When
        MailException exception = assertThrows(
                MailException.class,
                () -> controller.createReservation(request)
        );

        // Then
        assertEquals("Failed to send reservation email", exception.getMessage());
    }


}