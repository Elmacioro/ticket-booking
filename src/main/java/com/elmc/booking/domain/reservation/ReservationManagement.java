package com.elmc.booking.domain.reservation;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;
import com.elmc.booking.domain.ports.dto.shared.PriceDto;
import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import com.elmc.booking.domain.ports.incoming.ReservationService;
import com.elmc.booking.domain.ports.outgoing.ReservationRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.ports.outgoing.TicketTypeRepository;
import com.elmc.booking.domain.screening.Screening;
import com.elmc.booking.domain.screening.SeatId;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ReservationManagement implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    public CreatedReservationDto bookSeats(RequestedReservationDto requestedReservationDto) {
        Screening screening = screeningRepository.findScreeningById(requestedReservationDto.screeningId())
                .orElseThrow(() -> new NoSuchScreeningException(requestedReservationDto.screeningId()));

        List<String> ticketTypeNames = requestedReservationDto.tickets()
                .stream()
                .map(TicketDto::ticketType)
                .toList();

        Map<String, TicketType> ticketTypes = ticketTypeRepository.getTicketTypesByTypeNames(ticketTypeNames)
                .stream()
                .collect(Collectors.toMap(TicketType::type, ticketType -> ticketType));

        List<Ticket> tickets = requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> new Ticket(ticketDto.seatDto().rowNumber(),
                        ticketDto.seatDto().seatInRowNumber(),
                        ticketTypes.get(ticketDto.ticketType())))
                .toList();

        List<SeatId> seatsToBook = requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> ticketDto.seatDto().toSeatId())
                .toList();

        screening.bookSeats(seatsToBook);

        Reservation reservation = new Reservation(screening.getId(),
                tickets,
                requestedReservationDto.firstName(),
                requestedReservationDto.surname());

        long reservationId = reservationRepository.save(reservation);

        return new CreatedReservationDto(reservationId,
                new PriceDto(reservation.calculateTotalPrice()),
                reservation.getExpirationDate());
    }
}
