package ru.edu.java.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.edu.java.barbershop.api.exception.NoSuchRoleException;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbershop_id")
    private BarbershopEntity barbershop;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<RecordEntity> clientRecords = new HashSet<>();

    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
    private Set<RecordEntity> recordsOfClients = new HashSet<>();

    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
    private Set<RecordAvailableTimeEntity> availableTime = new HashSet<>();

   public UserResponse.UserResponseBuilder toResponse() {
        return UserResponse.builder()
                .id(id)
                .barbershopId(barbershop == null ? null : barbershop.getId())
                .recordsOfClients(recordsOfClients == null ? new HashSet<>() :
                        recordsOfClients.stream().map(RecordEntity::toResponse).collect(Collectors.toSet())
                )
                .clientRecords(clientRecords == null ? new HashSet<>() :
                        clientRecords.stream().map(RecordEntity::toResponse).collect(Collectors.toSet())
                )
                .fullName(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .role(role)
                .availableTime(availableTime == null ? new HashSet<>() :
                        availableTime.stream().map(RecordAvailableTimeEntity::toResponse).collect(Collectors.toSet())
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (role) {
            case "admin" -> List.of(
                    new SimpleGrantedAuthority("ROLE_CLIENT"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_BARBER")
            );
            case "client" -> List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
            case "barber" -> List.of(new SimpleGrantedAuthority("ROLE_BARBER"));
            default -> throw new NoSuchRoleException(role);
        };
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserEntity that = (UserEntity) object;
        return Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role) &&
                Objects.equals(barbershop, that.barbershop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, password, role, barbershop);
    }

    public void addRecord(RecordEntity record) {
        clientRecords.add(record);
        record.getBarber().getRecordsOfClients().add(record);
    }

    public void cancelRecord(RecordEntity record) {
        clientRecords.remove(record);
        record.getBarber().getRecordsOfClients().remove(record);
    }

    public void addAvailableTime(RecordAvailableTimeEntity recordAvailableTime) {
        availableTime.add(recordAvailableTime);
        recordAvailableTime.setBarber(this);
    }

    public void removeAvailableTime(RecordAvailableTimeEntity recordAvailableTime) {
        availableTime.remove(recordAvailableTime);
        recordAvailableTime.setBarber(null);
    }
}
