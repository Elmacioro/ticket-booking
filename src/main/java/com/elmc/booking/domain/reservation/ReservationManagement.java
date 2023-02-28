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

import java.util.HashSet;
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

        Screening screening = getScreening(requestedReservationDto.screeningId());
        List<SeatId> seatsToBook = getSeatsToBook(requestedReservationDto);
        screening.bookSeats(seatsToBook);

       List<Ticket> reservationTickets = getReservationTickets(requestedReservationDto);
        Reservation reservation = new Reservation(screening.getId(),
                reservationTickets,
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

    private List<Ticket> getReservationTickets(RequestedReservationDto requestedReservationDto) {
        List<Long> ticketTypeIds = getRequestedTicketTypesIds(requestedReservationDto);
        Map<Long, TicketType> ticketTypesByIds = groupTicketTypesByIds(ticketTypeIds);

        return requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> new Ticket(ticketDto.seatDto().rowNumber(),
                        ticketDto.seatDto().seatInRowNumber(),
                        ticketTypesByIds.get(ticketDto.ticketTypeId())))
                .toList();
    }

    private Map<Long, TicketType> groupTicketTypesByIds(List<Long> ticketTypesIds) {
        Map<Long, TicketType> TicketTypesByIds = ticketTypeRepository.getTicketTypesByIds(ticketTypesIds)
                .stream()
                .collect(Collectors.toMap(TicketType::id, ticketType -> ticketType));
        validateWhetherProvidedTicketTypesExist(ticketTypesIds, TicketTypesByIds);
        return TicketTypesByIds;
    }

    private void validateWhetherProvidedTicketTypesExist(List<Long> ticketTypesIds, Map<Long, TicketType> TicketTypesByIds) {
        if (TicketTypesByIds.size() != new HashSet<>(ticketTypesIds).size()) {
            throw new InvalidTicketTypesException();
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
