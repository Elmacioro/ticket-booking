package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;
import com.elmc.booking.adapters.incoming.rest.request.ReservationRequest;
import com.elmc.booking.domain.ports.incoming.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reservation")
@RestController
@AllArgsConstructor
public class ReservationController {

    private ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedReservationDto bookSeats(@RequestBody @Valid ReservationRequest reservationRequest) {
        return reservationService.bookSeats(reservationRequest.toDto());
    }

}
