package com.elmc.booking.domain.reservation;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;
import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import com.elmc.booking.domain.ports.incoming.ReservationService;
import com.elmc.booking.domain.ports.outgoing.ReservationRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.ports.outgoing.TicketTypeRepository;
import com.elmc.booking.domain.reservation.exceptions.InvalidTicketTypesException;
import com.elmc.booking.domain.screening.Screening;
import com.elmc.booking.domain.screening.SeatId;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ReservationManagement implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    public CreatedReservationDto bookSeats(RequestedReservationDto requestedReservationDto) {

        Screening screening = getScreening(requestedReservationDto.screeningId());
        List<SeatId> seatsToBook = getSeatsToBook(requestedReservationDto);
        log.debug("Booking seats for screening [seatIds: {}], [screening {}]", seatsToBook, screening);
        screening.bookSeats(seatsToBook);

        List<Ticket> tickets = getTickets(requestedReservationDto);
        log.debug("Creating reservation for tickets: {}", tickets);
        Reservation reservation = new Reservation(screening.getId(),
                tickets,
                requestedReservationDto.firstName(),
                requestedReservationDto.surname());
        long reservationId = reservationRepository.save(reservation);
        reservation.setReservationId(reservationId);

        return new CreatedReservationDto(reservation);
    }

    private Screening getScreening(long screeningId) {
        return screeningRepository.findScreeningById(screeningId)
                .orElseThrow(() -> new NoSuchScreeningException(screeningId));
    }

    private List<Ticket> getTickets(RequestedReservationDto requestedReservationDto) {
        List<Long> ticketTypeIds = getRequestedTicketTypesIds(requestedReservationDto);
        List<TicketType> ticketTypes = getRequestedTicketTypes(ticketTypeIds);
        Map<Long, TicketType> ticketTypesByIds = groupTicketTypesByIds(ticketTypes);

        return requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> ticketDto.toTicket(ticketTypesByIds.get(ticketDto.ticketTypeId())))
                .toList();
    }

    private Map<Long, TicketType> groupTicketTypesByIds(List<TicketType> ticketTypes) {
        return ticketTypes.stream()
                .collect(Collectors.toMap(TicketType::id, ticketType -> ticketType));
    }

    private List<TicketType> getRequestedTicketTypes(List<Long> ticketTypeIds) {
        List<TicketType> ticketTypes = ticketTypeRepository.getTicketTypesByIds(ticketTypeIds);
        validateWhetherProvidedTicketTypesExist(ticketTypeIds, ticketTypes);
        return ticketTypes;
    }

    private void validateWhetherProvidedTicketTypesExist(List<Long> ticketTypesIds, List<TicketType> ticketTypes) {
        if (ticketTypes.size() != new HashSet<>(ticketTypesIds).size()) {
            throw new InvalidTicketTypesException(ticketTypesIds);
        }
    }

    private List<Long> getRequestedTicketTypesIds(RequestedReservationDto requestedReservationDto) {
        return requestedReservationDto.tickets()
                .stream()
                .map(TicketDto::ticketTypeId)
                .toList();
    }

    private List<SeatId> getSeatsToBook(RequestedReservationDto requestedReservationDto) {
        return requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> ticketDto.seatDto().toSeatId())
                .toList();
    }
}
