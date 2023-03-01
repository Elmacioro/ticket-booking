package com.elmc.booking.adapters.outgoing.database.repository;

import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketTypeJpaRepositoryIT {

    @Autowired
    private TicketTypeJpaRepository ticketTypeJpaRepository;


    @Test
    @Sql("/data/simpleTicketTypes.sql")
    void getByIdsShouldReturnRightTicketTypeEntitiesWhenGivenValidIds() {
        // adult and child ticket type ids
        List<Long> ticketTypesIds = List.of(1L, 3L);

        List<TicketTypeEntity> ticketTypeEntities = ticketTypeJpaRepository.getByIds(ticketTypesIds);

        assertEquals(ticketTypesIds.size(), ticketTypeEntities.size());
        Map<Long, TicketTypeEntity> ticketTypesById = ticketTypeEntities.stream()
                .collect(Collectors.toMap(TicketTypeEntity::getId, ticketType -> ticketType));
        assertAll(() -> {
            assertEquals("adult", ticketTypesById.get(1L).getName());
            assertEquals("child", ticketTypesById.get(3L).getName());

            assertEquals("PLN", ticketTypesById.get(1L).getCurrency());
            assertEquals("PLN", ticketTypesById.get(3L).getCurrency());
        });
    }

}
