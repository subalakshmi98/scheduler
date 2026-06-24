package com.scheduler.repository;

import com.scheduler.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlotRepository extends JpaRepository<Slot, UUID> {

    @Query("""
        select count(s) > 0 from Slot s
        where s.owner.email = :email
        and s.startTime < :endTime
        and s.endTime > :startTime
    """)
    boolean existsOverlappingSlot(String email, LocalDateTime startTime, LocalDateTime endTime);
    List<Slot> findByOwnerEmailOrderByStartTimeAsc(String email);

    long countByOwnerEmail(String email);
    Optional<Slot> findByOwnerEmailAndSlotNumber(String email, String slotNumber);
    @Query("""
        select count(s) > 0 from Slot s
        where s.owner.email = :email
        and s.slotNumber <> :slotNumber
        and s.startTime < :endTime
        and s.endTime > :startTime
    """)
    boolean existsOverlappingSlotExcludingCurrent(String email, String slotNumber,
                                                  LocalDateTime startTime, LocalDateTime endTime);

    @Query("""
        select s from Slot s
        where s.owner.email = :email
        and s.startTime >= :from
        and s.endTime <= :to
        order by s.startTime
    """)
    List<Slot> findAvailability(String email, LocalDateTime from, LocalDateTime to
    );
}
