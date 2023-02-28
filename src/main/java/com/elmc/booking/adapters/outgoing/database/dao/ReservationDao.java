package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.dao.mapper.DomainToEntityMapper;
import com.elmc.booking.adapters.outgoing.database.entity.ReservationEntity;
import com.elmc.booking.domain.ports.outgoing.ReservationRepository;
import com.elmc.booking.domain.reservation.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
@AllArgsConstructor
public class ReservationDao implements ReservationRepository {

    private final EntityManager entityManager;
    private final DomainToEntityMapper domainToEntityMapper;

    @Override
    public long save(Reservation reservation) {
        ReservationEntity reservationEntity = domainToEntityMapper.map(reservation);
        entityManager.persist(reservationEntity);
        return reservationEntity.getId();
    }
}
