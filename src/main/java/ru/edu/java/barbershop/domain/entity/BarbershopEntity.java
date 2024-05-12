package ru.edu.java.barbershop.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.edu.java.barbershop.dto.response.BarbershopResponse;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "barbershop")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BarbershopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "barbershop")
    private Set<UserEntity> barbers = new HashSet<>();

    public BarbershopResponse toResponse() {
        return new BarbershopResponse(
                id,
                address,
                barbers.stream()
                        .map(UserEntity::toResponse)
                        .map(UserResponse.UserResponseBuilder::build)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BarbershopEntity that = (BarbershopEntity) object;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    public void addBarber(UserEntity barber) {
        barbers.add(barber);
        barber.setBarbershop(this);
    }

    public void removeBarber(UserEntity barber) {
        barbers.remove(barber);
        barber.setBarbershop(null);
    }
}


