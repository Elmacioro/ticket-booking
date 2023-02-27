package com.elmc.booking.adapters.configuration;

import com.elmc.booking.domain.ports.incoming.ReservationService;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import com.elmc.booking.domain.ports.outgoing.ReservationRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.ports.outgoing.TicketTypeRepository;
import com.elmc.booking.domain.reservation.ReservationManagement;
import com.elmc.booking.domain.screening.ScreeningManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    @Bean
    public ScreeningService getScreeningService(ScreeningRepository screeningRepository) {
        return new ScreeningManagement(screeningRepository);
    }

    @Bean
    public ReservationService getReservationService(ReservationRepository reservationRepository,
                                                    TicketTypeRepository ticketTypeRepository,
                                                    ScreeningRepository screeningRepository) {
        return new ReservationManagement(reservationRepository, ticketTypeRepository, screeningRepository);
    }

}
