package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.reservation.TicketType;

import java.util.List;

public interface TicketTypeRepository {

    List<TicketType> getTicketTypesByTypeNames(List<String> typeNames);

}
