package pl.matkan.flightbookingsystem.util.mail;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.matkan.flightbookingsystem.reservation.ReservationResponse;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailBuilder emailBuilder;

    public void sendReservationEmail(ReservationResponse reservation) throws MessagingException {
        String emailContent = emailBuilder.buildReservationEmail(reservation);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(reservation.email());
        helper.setSubject("Your Flight Reservation Confirmation");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }


}
