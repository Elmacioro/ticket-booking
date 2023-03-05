package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;
import com.elmc.booking.adapters.incoming.rest.request.ReservationRequest;
import com.elmc.booking.domain.ports.incoming.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reservation")
@RestController
@Slf4j
@AllArgsConstructor
public class ReservationController {

    private ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedReservationDto bookSeats(@RequestBody @Valid ReservationRequest reservationRequest) {
        log.debug("New reservation request received {}", reservationRequest);
        return reservationService.bookSeats(reservationRequest.toDto());
    }

}
