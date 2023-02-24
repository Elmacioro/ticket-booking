package com.elmc.booking.adapters.configuration;

import com.elmc.booking.domain.ports.incoming.ScreeningService;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.ScreeningManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ScreeningService getScreeningService(ScreeningRepository screeningRepository) {
        return new ScreeningManagement(screeningRepository);
    }

}
