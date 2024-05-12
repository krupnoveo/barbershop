package ru.edu.java.barbershop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.java.barbershop.domain.entity.BarbershopEntity;

import java.util.UUID;

@Repository
public interface BarbershopRepository extends JpaRepository<BarbershopEntity, UUID> {
}
