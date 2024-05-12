package ru.edu.java.barbershop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.java.barbershop.domain.entity.RecordEntity;

import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {
}
