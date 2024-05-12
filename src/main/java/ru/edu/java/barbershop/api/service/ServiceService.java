package ru.edu.java.barbershop.api.service;

import ru.edu.java.barbershop.dto.response.ServiceResponse;

import java.util.List;
import java.util.UUID;

public interface ServiceService {
    ServiceResponse getService(UUID id);

    List<ServiceResponse> getAllServices();

    ServiceResponse addService(String name, String description, Integer cost);

    ServiceResponse deleteService(UUID id);

    ServiceResponse changeName(UUID id, String name);

    ServiceResponse changeDescription(UUID id, String description);

    ServiceResponse changeCost(UUID id, Integer cost);
}
