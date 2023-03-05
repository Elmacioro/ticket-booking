package com.elmc.booking.domain.ports.dto.outgoing;

import com.elmc.booking.domain.ports.dto.shared.SeatDto;
import com.elmc.booking.domain.screening.Room;
import com.elmc.booking.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ScreeningDetailsDto {

    @NonNull
    String roomName;

    int rowsNumber;

    int seatsInRowNumber;

    @NonNull
    List<SeatDto> availableSeats;

    public ScreeningDetailsDto(@NonNull Screening screening) {
        Room room = screening.getRoom();
        this.roomName = room.name();
        this.rowsNumber = room.rowsNumber();
        this.seatsInRowNumber = room.seatsInRowNumber();
        this.availableSeats = screening.getAvailableSeats().stream().map(SeatDto::new).toList();
    }

}
