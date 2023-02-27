package com.elmc.booking.adapters.outgoing.database.repository;

import com.elmc.booking.adapters.outgoing.database.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
}
