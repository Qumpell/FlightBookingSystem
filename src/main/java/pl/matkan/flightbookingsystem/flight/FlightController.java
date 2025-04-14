package pl.matkan.flightbookingsystem.flight;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flight")
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return new ResponseEntity<>(flightService.getAllFlights(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.getFlightById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequest flightRequest) {
        return new ResponseEntity<>(flightService.updateFlight(id, flightRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody FlightRequest flightRequest) {
        return new ResponseEntity<>(flightService.addFlight(flightRequest), HttpStatus.CREATED);
    }


}
