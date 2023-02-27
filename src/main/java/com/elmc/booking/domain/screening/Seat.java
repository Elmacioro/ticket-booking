package com.elmc.booking.domain.screening;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = {"seatId"})
public final class Seat {

    private final SeatId seatId;

    @Setter
    private SeatStatus seatStatus;

    public Seat(@NonNull SeatId seatId, @NonNull SeatStatus seatStatus) {
        this.seatId = seatId;
        this.seatStatus = seatStatus;
    }

    public boolean isSeatBooked() {
        return seatStatus == SeatStatus.BOOKED;
    }

    public boolean isSeatFree() {
        return seatStatus == SeatStatus.FREE;
    }

}
