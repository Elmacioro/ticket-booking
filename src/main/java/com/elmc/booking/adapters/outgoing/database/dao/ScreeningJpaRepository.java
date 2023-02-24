package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningJpaRepository extends JpaRepository<ScreeningEntity, Long> {

    @Query("SELECT s FROM ScreeningEntity s JOIN FETCH s.reservations r JOIN FETCH r.tickets WHERE s.id = ?1")
    Optional<ScreeningEntity> findScreeningWithReservationsAndTicketsById(long screeningId);

    @Query("SELECT s FROM ScreeningEntity s WHERE s.startTime BETWEEN ?1 AND ?2")
    List<ScreeningEntity> findScreeningsInDateRange(LocalDateTime start, LocalDateTime end);
}
