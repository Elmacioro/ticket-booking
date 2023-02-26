package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningManagementTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ScreeningManagement screeningManagement;

    private final int screeningId = 1;

    private Screening screening;

    @BeforeEach
    void setUp() {
        Movie movie = new Movie(screeningId, "Django", "Lorem ipsum");
        Room room = new Room("Room A", 2, 2);
        List<Seat> seats = List.of(new Seat(1, 1, SeatStatus.BOOKED),
                new Seat(1, 2, SeatStatus.BOOKED),
                new Seat(2, 1, SeatStatus.FREE),
                new Seat(2, 2, SeatStatus.FREE));
        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T10:30:00");
        LocalDateTime endTime = LocalDateTime.parse("2023-01-01T12:45:00");
        screening = new Screening(screeningId, movie, room, startTime, endTime, seats);
    }

    @Test
    void getScreeningDetailsShouldThrowExceptionWhenScreeningIdNotExist() {
        long wrongScreeningId = 5967;

        when(screeningRepository.findScreeningById(wrongScreeningId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchScreeningException.class,
                () -> screeningManagement.getScreeningDetails(wrongScreeningId));
    }

    @Test
    void getScreeningDetailsShouldBeEqualWhenCorrectScreeningId() {
        ScreeningDetailsDto expected = new ScreeningDetailsDto(screening);

        when(screeningRepository.findScreeningById(screeningId))
                .thenReturn(Optional.of(screening));

        ScreeningDetailsDto actual = screeningManagement.getScreeningDetails(screeningId);
        assertEquals(expected, actual);
    }

}