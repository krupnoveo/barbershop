package ru.edu.java.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.edu.java.barbershop.dto.response.RecordResponse;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "record")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_of_record")
    private RecordAvailableTimeEntity timeOfRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private UserEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
    private UserEntity barber;

    public RecordResponse toResponse() {
        return new RecordResponse(
                id,
                client.getName(),
                barber.getName(),
                service.getType(),
                barber.getBarbershop().getAddress(),
                timeOfRecord.getTime(),
                service.getCost()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RecordEntity that = (RecordEntity) object;
        return Objects.equals(timeOfRecord, that.timeOfRecord) && Objects.equals(client, that.client) && Objects.equals(service, that.service) && Objects.equals(barber, that.barber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeOfRecord, client, service, barber);
    }
}
