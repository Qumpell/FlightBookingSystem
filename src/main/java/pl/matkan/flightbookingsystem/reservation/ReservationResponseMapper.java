package pl.matkan.flightbookingsystem.reservation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReservationResponseMapper {

    ReservationResponseMapper INSTANCE = Mappers.getMapper(ReservationResponseMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "reservationNumber"),
            @Mapping(source = "flight.flightNumber", target = "flightNumber"),
            @Mapping(source = "seatNumber", target = "seatNumber"),
            @Mapping(expression = "java(reservation.getPassenger().getFirstname() + \" \" + reservation.getPassenger().getLastname())", target = "passengerName"),
            @Mapping(source = "passenger.email", target = "email"),
            @Mapping(source = "passenger.phone", target = "phone"),
            @Mapping(source = "flight.departureDateTime", target = "departureDate"),
            @Mapping(source = "departureDone", target = "departureDone"),
    })
    ReservationResponse toDto(Reservation reservation);

    List<ReservationResponse> toDtoList(List<Reservation> reservations);

}
