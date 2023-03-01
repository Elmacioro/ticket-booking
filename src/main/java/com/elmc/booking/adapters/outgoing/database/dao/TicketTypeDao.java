package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.dao.mapper.EntityToDomainMapper;
import com.elmc.booking.adapters.outgoing.database.repository.TicketTypeJpaRepository;
import com.elmc.booking.domain.ports.outgoing.TicketTypeRepository;
import com.elmc.booking.domain.reservation.TicketType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class TicketTypeDao implements TicketTypeRepository {

    private final TicketTypeJpaRepository ticketTypeJpaRepository;
    private final EntityToDomainMapper entityToDomainMapper;

    @Override
    public List<TicketType> getTicketTypesByIds(List<Long> ticketTypesIds) {
        log.debug("Fetching for ticket types by ids: {}", ticketTypesIds);
        return ticketTypeJpaRepository.getByIds(ticketTypesIds)
                .stream()
                .map(entityToDomainMapper::map)
                .toList();
    }
}
