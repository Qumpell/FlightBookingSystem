package pl.matkan.flightbookingsystem.util.mail;

import org.springframework.stereotype.Component;
import pl.matkan.flightbookingsystem.reservation.ReservationResponse;

import java.time.format.DateTimeFormatter;

@Component
public class EmailBuilder {

    public String buildReservationEmail(ReservationResponse reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm");
        String formattedDate = reservation.departureDate().format(formatter);

        return "<h2>Reservation Confirmed</h2>"
                + "<p>Dear " + reservation.passengerName() + ",</p>"
                + "<p>Thank you for booking your flight with us!</p>"
                + "<p>Here are your reservation details:</p>"
                + "<ul>"
                + "<li><strong>Reservation Number:</strong> " + reservation.reservationNumber() + "</li>"
                + "<li><strong>Flight Number:</strong> " + reservation.flightNumber() + "</li>"
                + "<li><strong>Departure Date:</strong> " + formattedDate + "</li>"
                + "</ul>"
                + "<p>We wish you a pleasant journey!</p>"
                + "<p>Best regards,<br/>Flight Booking System Team</p>";
    }
}
