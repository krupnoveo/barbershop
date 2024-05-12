package ru.edu.java.barbershop.api.service;

import ru.edu.java.barbershop.dto.response.BarbershopResponse;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface BarbershopService {
    BarbershopResponse addBarbershop(String address);

    BarbershopResponse deleteBarbershop(UUID barbershopId);

    BarbershopResponse changeAddress(UUID barbershopId, String address);

    List<BarbershopResponse> getAllBarbershops();

    List<UserResponse> getAllBarbersConnectedToBarbershopId(UUID barbershopId);

    List<UserResponse> getAllBarbers();

    UserResponse getBarber(UUID id);

    BarbershopResponse connectBarberToBarbershop(UUID barberId, UUID barbershopId);

    BarbershopResponse disconnectBarberFromBarbershop(UUID barberId, UUID barbershopId);
}
