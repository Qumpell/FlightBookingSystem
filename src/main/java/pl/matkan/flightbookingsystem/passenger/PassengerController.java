package pl.matkan.flightbookingsystem.passenger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.getPassengerById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody PassengerRequest passengerRequest
    ) {
        return new ResponseEntity<>(passengerService.updatePassenger(id, passengerRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody PassengerRequest passengerRequest) {
        return new ResponseEntity<>(passengerService.addPassenger(passengerRequest), HttpStatus.CREATED);
    }
}
