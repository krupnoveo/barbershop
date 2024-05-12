package ru.edu.java.barbershop.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.edu.java.barbershop.dto.response.RecordAvailableTimeResponse;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "record_available_time")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecordAvailableTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "time", nullable = false)
    private OffsetDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
    private UserEntity barber;


    public RecordAvailableTimeResponse toResponse() {
        return new RecordAvailableTimeResponse(
                id,
                time,
                barber.getId()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RecordAvailableTimeEntity that = (RecordAvailableTimeEntity) object;
        return Objects.equals(time, that.time) && Objects.equals(barber, that.barber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, barber);
    }
}
