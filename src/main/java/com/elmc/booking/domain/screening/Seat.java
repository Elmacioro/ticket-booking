package com.elmc.booking.domain.screening;

import lombok.*;

@Getter
@EqualsAndHashCode(of = {"seatId"})
@ToString
public final class Seat {

    private final SeatId seatId;

    @Setter
    private SeatStatus seatStatus;

    public Seat(@NonNull SeatId seatId, @NonNull SeatStatus seatStatus) {
        this.seatId = seatId;
        this.seatStatus = seatStatus;
    }

    public boolean isBooked() {
        return seatStatus == SeatStatus.BOOKED;
    }

    public boolean isFree() {
        return seatStatus == SeatStatus.FREE;
    }

}
