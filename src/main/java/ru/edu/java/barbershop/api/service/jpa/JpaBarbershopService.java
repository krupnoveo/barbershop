package ru.edu.java.barbershop.api.service.jpa;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.java.barbershop.api.service.BarbershopService;
import ru.edu.java.barbershop.domain.entity.BarbershopEntity;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.domain.repository.BarbershopRepository;
import ru.edu.java.barbershop.domain.repository.UserRepository;
import ru.edu.java.barbershop.dto.response.BarbershopResponse;
import ru.edu.java.barbershop.dto.response.UserResponse;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class JpaBarbershopService implements BarbershopService {
    private final BarbershopRepository barbershopRepository;
    private final UserRepository userRepository;
    @Override
    public BarbershopResponse addBarbershop(String address) {
        BarbershopEntity barbershopEntity = new BarbershopEntity();
        barbershopEntity.setAddress(address);
        return barbershopRepository.save(barbershopEntity).toResponse();
    }

    @Override
    public BarbershopResponse deleteBarbershop(UUID barbershopId) {
        BarbershopEntity barbershop = barbershopRepository.findById(barbershopId).orElseThrow();
        barbershopRepository.deleteById(barbershopId);
        return barbershop.toResponse();
    }

    @Override
    public BarbershopResponse changeAddress(UUID barbershopId, String address) {
        BarbershopEntity barbershop = barbershopRepository.findById(barbershopId).orElseThrow();
        barbershop.setAddress(address);
        return barbershopRepository.save(barbershop).toResponse();
    }

    @Override
    public List<BarbershopResponse> getAllBarbershops() {
        return barbershopRepository.findAll().stream().map(BarbershopEntity::toResponse).toList();
    }

    @Override
    public List<UserResponse> getAllBarbersConnectedToBarbershopId(UUID barbershopId) {
        return barbershopRepository.findById(barbershopId).orElseThrow().toResponse().barbers().stream().toList();
    }

    @Override
    public List<UserResponse> getAllBarbers() {
        return userRepository.findAll().stream()
                .filter(a -> a.getRole().equals("barber"))
                .map(UserEntity::toResponse)
                .map(UserResponse.UserResponseBuilder::build).toList();
    }

    @Override
    public UserResponse getBarber(UUID id) {
        return userRepository.findById(id).orElseThrow().toResponse().build();
    }

    @Override
    public BarbershopResponse connectBarberToBarbershop(UUID barberId, UUID barbershopId) {
        UserEntity barber = userRepository.findById(barberId).orElseThrow();
        BarbershopEntity barbershop = barbershopRepository.findById(barbershopId).orElseThrow();
        barbershop.addBarber(barber);
        return barbershopRepository.save(barbershop).toResponse();
    }

    @Override
    public BarbershopResponse disconnectBarberFromBarbershop(UUID barberId, UUID barbershopId) {
        UserEntity barber = userRepository.findById(barberId).orElseThrow();
        BarbershopEntity barbershop = barbershopRepository.findById(barbershopId).orElseThrow();
        barbershop.removeBarber(barber);
        return barbershopRepository.save(barbershop).toResponse();
    }
}
