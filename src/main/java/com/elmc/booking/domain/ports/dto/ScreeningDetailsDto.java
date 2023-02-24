package com.elmc.booking.domain.ports.dto;

import com.elmc.booking.domain.screening.Room;
import com.elmc.booking.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ScreeningDetailsDto {

    @NonNull String roomName;
    int roomRowsNumber;
    int roomSeatsInRowNumber;
    @NonNull List<SeatDto> freeSeats;
    @NonNull List<SeatDto> bookedSeats;

    public ScreeningDetailsDto(Screening screening) {
        Room room = screening.getRoom();
        this.roomName = room.name();
        this.roomRowsNumber = room.numberOfRows();
        this.roomSeatsInRowNumber = room.numberOfSeatsInRow();
        this.freeSeats = screening.getFreeSeats().stream().map(SeatDto::new).toList();
        this.bookedSeats = screening.getBookedSeats().stream().map(SeatDto::new).toList();
    }

}
