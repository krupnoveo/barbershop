package ru.edu.java.barbershop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.java.barbershop.domain.entity.RecordAvailableTimeEntity;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecordAvailableTimeRepository extends JpaRepository<RecordAvailableTimeEntity, UUID> {
    Optional<RecordAvailableTimeEntity> findByTimeAndBarberId(OffsetDateTime time, UUID barberId);

    boolean existsByTimeAndBarberId(OffsetDateTime time, UUID barberId);
}
