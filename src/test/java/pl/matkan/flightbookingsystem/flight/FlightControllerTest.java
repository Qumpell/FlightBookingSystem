package pl.matkan.flightbookingsystem.flight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.matkan.flightbookingsystem.exception.BadRequestException;
import pl.matkan.flightbookingsystem.exception.FlightNotFoundException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private FlightRequest flightRequest;
    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight(1L,
                "FL1001",
                "Warsaw",
                "Paris",
                Duration.ofHours(2),
                false,
                LocalDateTime.now().plusDays(1));

        flightRequest = new FlightRequest(flight.getFlightNumber(),
                flight.getDepartureLocation(),
                flight.getArrivalLocation(),
                flight.getDuration(),
                flight.isRoundTrip(),
                flight.getDepartureDateTime());
    }

    @Test
    void shouldReturnAllFlights() {
        // Given
        List<Flight> flightList = List.of(flight);
        when(flightService.getAllFlights()).thenReturn(flightList);

        // When
        ResponseEntity<List<Flight>> response = flightController.getAllFlights();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flightList, response.getBody());
    }

    @Test
    void shouldReturnFlightById() {
        // Given
        when(flightService.getFlightById(1L)).thenReturn(flight);

        // When
        ResponseEntity<Flight> response = flightController.getFlightById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flight, response.getBody());
    }

    @Test
    void shouldUpdateFlight() {
        // Given
        when(flightService.updateFlight(1L, flightRequest)).thenReturn(flight);

        // When
        ResponseEntity<Flight> response = flightController.updateFlight(1L, flightRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flight, response.getBody());
    }

    @Test
    void shouldDeleteFlight() {
        // Given
        doNothing().when(flightService).deleteFlight(1L);

        // When
        ResponseEntity<Flight> response = flightController.deleteFlight(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(flightService, times(1)).deleteFlight(1L);
    }

    @Test
    void shouldCreateFlight() {
        // Given
        when(flightService.addFlight(flightRequest)).thenReturn(flight);

        // When
        ResponseEntity<Flight> response = flightController.createFlight(flightRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(flight, response.getBody());
    }

    @Test
    void shouldThrowFlightNotFoundExceptionWhenFlightDoesNotExist_getById() {
        // Given
        Long flightId = 99L;
        when(flightService.getFlightById(flightId))
                .thenThrow(new FlightNotFoundException(flightId));

        // When / Then
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> flightController.getFlightById(flightId)
        );

        assertEquals("Flight with id 99 not found", exception.getMessage());
    }

    @Test
    void shouldThrowFlightNotFoundExceptionWhenFlightDoesNotExist_update() {
        // Given
        Long flightId = 99L;
        when(flightService.updateFlight(flightId, flightRequest))
                .thenThrow(new FlightNotFoundException(flightId));

        // When / Then
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> flightController.updateFlight(flightId, flightRequest)
        );

        assertEquals("Flight with id 99 not found", exception.getMessage());
    }

    @Test
    void shouldThrowFlightNotFoundExceptionWhenFlightDoesNotExist_delete() {
        // Given
        Long flightId = 99L;
        doThrow(new FlightNotFoundException(flightId)).when(flightService).deleteFlight(flightId);

        // When / Then
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> flightController.deleteFlight(flightId)
        );

        assertEquals("Flight with id 99 not found", exception.getMessage());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenFlightHasReservationsOnDelete() {
        // Given
        Long flightId = 1L;
        doThrow(new BadRequestException("Cannot delete flight with existing reservations."))
                .when(flightService).deleteFlight(flightId);

        // When / Then
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> flightController.deleteFlight(flightId)
        );

        assertEquals("Cannot delete flight with existing reservations.", exception.getMessage());
    }
}