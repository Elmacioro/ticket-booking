package com.elmc.booking.adapters.configuration;

import com.elmc.booking.adapters.outgoing.database.dao.EntityToDomainMapper;
import com.elmc.booking.adapters.outgoing.database.dao.ScreeningDao;
import com.elmc.booking.adapters.outgoing.database.dao.ScreeningJpaRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public ScreeningRepository getScreeningRepository(ScreeningJpaRepository screeningJpaRepository,
                                                      EntityToDomainMapper entityToDomainMapper) {
        return new ScreeningDao(screeningJpaRepository, entityToDomainMapper);
    }
}
