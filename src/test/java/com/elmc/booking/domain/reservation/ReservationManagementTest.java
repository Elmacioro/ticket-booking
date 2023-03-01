package com.elmc.booking.domain.reservation;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;
import com.elmc.booking.domain.ports.dto.shared.SeatDto;
import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import com.elmc.booking.domain.ports.outgoing.ReservationRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.ports.outgoing.TicketTypeRepository;
import com.elmc.booking.domain.reservation.exceptions.InvalidTicketTypesException;
import com.elmc.booking.domain.screening.*;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationManagementTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TicketTypeRepository ticketTypeRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ReservationManagement reservationManagement;

    private long screeningId;
    private String firstname;
    private String surname;
    private List<TicketDto> tickets;
    private Screening screening;
    private TicketType adultTicketType;
    private RequestedReservationDto requestedReservationDto;

    @BeforeEach
    void setUp() {
        screeningId = 1;
        firstname = "Jan";
        surname = "Nowak";
        tickets = getValidTicketDtos();
        screening = prepareScreening();
        adultTicketType = new TicketType(1, "adult", new Price(BigDecimal.valueOf(25), "PLN"));
    }

    @Test
    void bookSeatsShouldThrowExceptionWhenInvalidScreeningId() {
        long invalidScreeningId = 567;
        requestedReservationDto = new RequestedReservationDto(invalidScreeningId, firstname, surname, tickets);

        when(screeningRepository.findScreeningById(invalidScreeningId)).thenThrow(NoSuchScreeningException.class);

        assertThrows(NoSuchScreeningException.class,
                () -> reservationManagement.bookSeats(requestedReservationDto));
    }

    @Test
    void bookSeatsShouldThrowExceptionWhenProvidedInvalidTicketTypes() {
        List<TicketDto> invalidTicketDtos = getInvalidTicketDtos();
        List<Long> ticketTypesIds = invalidTicketDtos.stream().map(TicketDto::ticketTypeId).toList();
        requestedReservationDto = new RequestedReservationDto(screeningId, firstname, surname, invalidTicketDtos);

        when(screeningRepository.findScreeningById(screeningId)).thenReturn(Optional.of(screening));
        when(ticketTypeRepository.getTicketTypesByIds(ticketTypesIds)).thenReturn(List.of(adultTicketType));

        assertThrows(InvalidTicketTypesException.class,
                () -> reservationManagement.bookSeats(requestedReservationDto));
    }

    @Test
    void bookSeatsShouldSaveReservation() {
        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        List<Long> ticketTypesIds = tickets.stream().map(TicketDto::ticketTypeId).toList();
        requestedReservationDto = new RequestedReservationDto(screeningId, firstname, surname, tickets);

        when(screeningRepository.findScreeningById(screeningId)).thenReturn(Optional.of(screening));
        when(ticketTypeRepository.getTicketTypesByIds(ticketTypesIds)).thenReturn(List.of(adultTicketType));
        when(reservationRepository.save(reservationCaptor.capture())).thenReturn(1L);

        CreatedReservationDto actualReservationDto = reservationManagement.bookSeats(requestedReservationDto);
        assertEquals(reservationCaptor.getValue().getExpirationDate(), actualReservationDto.expirationTime());
        assertEquals(1L, actualReservationDto.reservationId());
        assertEquals("PLN", actualReservationDto.priceDto().currency());
        assertTrue(BigDecimal.valueOf(50).compareTo(actualReservationDto.priceDto().amount()) == 0);
    }


    private List<TicketDto> getInvalidTicketDtos() {
        return List.of(
                new TicketDto(new SeatDto(2, 1), 52),
                new TicketDto(new SeatDto(2, 2), 1)
        );
    }

    private List<TicketDto> getValidTicketDtos() {
        return List.of(
                new TicketDto(new SeatDto(2, 1), 1),
                new TicketDto(new SeatDto(2, 2), 1)
        );
    }

    private Screening prepareScreening() {
        Movie movie = new Movie(1, "Django", "Lorem ipsum");
        Room room = new Room("Room A", 2, 2);
        List<Seat> seats = List.of(new Seat(new SeatId(1, 1), SeatStatus.BOOKED),
                new Seat(new SeatId(1, 2), SeatStatus.BOOKED),
                new Seat(new SeatId(2, 1), SeatStatus.FREE),
                new Seat(new SeatId(2, 2), SeatStatus.FREE));
        LocalDateTime startTime = LocalDateTime.now().plusDays(5);
        LocalDateTime endTime = startTime.plusHours(2);
        return new Screening(1, movie, room, startTime, endTime, seats);
    }


}