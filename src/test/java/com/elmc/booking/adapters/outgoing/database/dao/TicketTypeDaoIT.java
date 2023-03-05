package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.domain.reservation.TicketType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TicketTypeDaoIT {

    @Autowired
    private TicketTypeDao ticketTypeDao;

    @Test
    @Sql("/data/simpleTicketTypes.sql")
    void getTicketTypesByNamesShouldReturnCorrectTicketTypes() {
        List<String> ticketTypeNames = List.of("adult", "child");

        List<TicketType> ticketTypes = ticketTypeDao.getTicketTypesByNames(ticketTypeNames);

        assertEquals(2, ticketTypes.size());
        assertTrue(
                ticketTypes.stream()
                .map(TicketType::name)
                .toList()
                .containsAll(ticketTypeNames));
    }
}