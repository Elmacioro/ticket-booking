package com.elmc.booking.adapters.outgoing.database.repository;

import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketTypeJpaRepository extends JpaRepository<TicketTypeEntity, Long> {

    @Query("SELECT tt FROM TicketTypeEntity tt WHERE tt.name in :ticketTypeNames")
    List<TicketTypeEntity> getByTicketTypeNames(List<String> ticketTypeNames);

}
