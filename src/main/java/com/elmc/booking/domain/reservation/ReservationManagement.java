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

        Screening screening = screeningRepository.getScreeningById(requestedReservationDto.screeningId());
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

    private List<Ticket> getTickets(RequestedReservationDto requestedReservationDto) {
        List<String> ticketTypeNames = getRequestedTicketTypeNames(requestedReservationDto);
        List<TicketType> ticketTypes = getRequestedTicketTypes(ticketTypeNames);
        Map<String, TicketType> ticketTypeByName = mapTicketTypesToNames(ticketTypes);

        return requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> ticketDto.toTicket(ticketTypeByName.get(ticketDto.ticketTypeName())))
                .toList();
    }

    private Map<String, TicketType> mapTicketTypesToNames(List<TicketType> ticketTypes) {
        return ticketTypes.stream()
                .collect(Collectors.toMap(TicketType::name, ticketType -> ticketType));
    }

    private List<TicketType> getRequestedTicketTypes(List<String> ticketTypeNames) {
        List<TicketType> ticketTypes = ticketTypeRepository.getTicketTypesByNames(ticketTypeNames);
        validateWhetherProvidedTicketTypesExist(ticketTypeNames, ticketTypes);
        return ticketTypes;
    }

    private void validateWhetherProvidedTicketTypesExist(List<String> ticketTypeNames, List<TicketType> ticketTypes) {
        if (ticketTypes.size() != new HashSet<>(ticketTypeNames).size()) {
            throw new InvalidTicketTypesException(ticketTypeNames);
        }
    }

    private List<String> getRequestedTicketTypeNames(RequestedReservationDto requestedReservationDto) {
        return requestedReservationDto.tickets()
                .stream()
                .map(TicketDto::ticketTypeName)
                .toList();
    }

    private List<SeatId> getSeatsToBook(RequestedReservationDto requestedReservationDto) {
        return requestedReservationDto.tickets()
                .stream()
                .map(ticketDto -> ticketDto.seatDto().toSeatId())
                .toList();
    }
}
