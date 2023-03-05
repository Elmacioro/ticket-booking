package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.reservation.TicketType;
import lombok.NonNull;

import java.util.List;

public interface TicketTypeRepository {

    List<TicketType> getTicketTypesByNames(@NonNull List<String> ticketTypeNames);

}
