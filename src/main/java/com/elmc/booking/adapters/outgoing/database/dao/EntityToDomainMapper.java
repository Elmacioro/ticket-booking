package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.entity.*;
import com.elmc.booking.domain.screening.*;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntityToDomainMapper {

    public Movie mapToDomain(@NonNull MovieEntity movieEntity) {
        return new Movie(movieEntity.getId(),
                movieEntity.getTitle(),
                movieEntity.getDescription());
    }

    public Room mapToDomain(@NonNull RoomEntity roomEntity) {
        return new Room(roomEntity.getName(),
                roomEntity.getRowsNumber(),
                roomEntity.getSeatsInRowNumber());
    }

    public Screening mapToDomain(@NonNull ScreeningEntity screeningEntity) {

        Room room = mapToDomain(screeningEntity.getRoom());
        Set<Seat> bookedSeats = getBookedSeats(screeningEntity);
        List<Seat> seats = getSeatsForRoom(room, bookedSeats);

        return new Screening(screeningEntity.getId(),
                mapToDomain(screeningEntity.getMovie()),
                room,
                screeningEntity.getStartTime(),
                screeningEntity.getEndTime(),
                seats);
    }

    private Set<Seat> getBookedSeats(ScreeningEntity screeningEntity) {
        return screeningEntity.getReservations()
                .stream()
                .map(ReservationEntity::getTickets)
                .flatMap(Collection::stream)
                .map(ticketEntity -> new Seat(ticketEntity.getRowNumber(),
                        ticketEntity.getSeatInRowNumber(),
                        SeatStatus.BOOKED))
                .collect(Collectors.toSet());
    }

    private List<Seat> getSeatsForRoom(Room room, Set<Seat> bookedSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= room.numberOfRows(); row++) {
            for (int seatInRow = 1; seatInRow <= room.numberOfSeatsInRow(); seatInRow++) {
                Seat seat = new Seat(row, seatInRow, SeatStatus.FREE);
                if (bookedSeats.contains(seat)) {
                    seat.setSeatStatus(SeatStatus.BOOKED);
                }
                seats.add(seat);
            }
        }
        return seats;
    }

}
