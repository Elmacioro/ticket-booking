package com.elmc.booking.adapters.outgoing.database.repository;

import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketTypeJpaRepositoryIT {

    @Autowired
    private TicketTypeJpaRepository ticketTypeJpaRepository;


    @Test
    @Sql("/data/simpleTicketTypes.sql")
    void getByTicketTypeNamesShouldReturnRightTicketTypeEntitiesWhenGivenValidIds() {
        // adult and child ticket type ids
        List<String> ticketTypeNames = List.of("adult", "child");

        List<TicketTypeEntity> ticketTypeEntities = ticketTypeJpaRepository.getByTicketTypeNames(ticketTypeNames);

        assertEquals(ticketTypeNames.size(), ticketTypeEntities.size());
    }

}
