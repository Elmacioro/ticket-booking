package com.elmc.booking.adapters.outgoing.database.dao.mapper;

import com.elmc.booking.adapters.outgoing.database.entity.*;
import com.elmc.booking.domain.reservation.Price;
import com.elmc.booking.domain.reservation.TicketType;
import com.elmc.booking.domain.screening.*;
import lombok.NonNull;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class EntityToDomainMapper {

    public abstract Movie map(@NonNull MovieEntity movieEntity);

    public abstract Room map(@NonNull RoomEntity roomEntity);

    @Mapping(target = "amount", source = "price")
    @Mapping(target = "currency", source = "currency" )
    @Named("ticketTypeToPrice")
    public abstract Price mapTicketTypeEntityToPrice(@NonNull TicketTypeEntity ticketTypeEntity);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "ticketTypeEntity", qualifiedByName = "ticketTypeToPrice")
    public abstract TicketType map(@NonNull TicketTypeEntity ticketTypeEntity);

    @Mapping(target = "seats", expression = "java(getSeatsForScreening(screeningEntity))")
    public abstract Screening map(@NonNull ScreeningEntity screeningEntity);

    public List<Seat> getSeatsForScreening(@NonNull ScreeningEntity screeningEntity) {
        Set<Seat> bookedSeats = getBookedSeats(screeningEntity);
        return getSeatsForRoom(screeningEntity.getRoom(), bookedSeats);
    }

    private Set<Seat> getBookedSeats(ScreeningEntity screeningEntity) {
        return screeningEntity.getReservations()
                .stream()
                .map(ReservationEntity::getTickets)
                .flatMap(Collection::stream)
                .map(ticketEntity -> new Seat(
                        new SeatId(ticketEntity.getRowNumber(),
                                ticketEntity.getSeatInRowNumber()),
                        SeatStatus.BOOKED))
                .collect(Collectors.toSet());
    }

    private List<Seat> getSeatsForRoom(RoomEntity room, Set<Seat> bookedSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= room.getNumberOfRows(); row++) {
            for (int seatInRow = 1; seatInRow <= room.getNumberOfSeatsInRow(); seatInRow++) {
                Seat seat = new Seat(new SeatId(row, seatInRow), SeatStatus.FREE);
                if (bookedSeats.contains(seat)) {
                    seat.setSeatStatus(SeatStatus.BOOKED);
                }
                seats.add(seat);
            }
        }
        return seats;
    }

}
