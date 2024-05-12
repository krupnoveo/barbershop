package ru.edu.java.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.edu.java.barbershop.dto.response.ServiceResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "service")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "service_type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<RecordEntity> records;

    public ServiceResponse toResponse() {
        return new ServiceResponse(
                id,
                type,
                description,
                cost
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ServiceEntity that = (ServiceEntity) object;
        return Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(cost, that.cost) && Objects.equals(records, that.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, description, cost, records);
    }

    public void addRecord(RecordEntity record) {
        records.add(record);
        record.setService(this);
    }

    public void removeRecord(RecordEntity record) {
        records.remove(record);
        record.setService(null);
    }
}
