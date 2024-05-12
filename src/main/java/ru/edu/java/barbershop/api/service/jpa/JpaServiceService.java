package ru.edu.java.barbershop.api.service.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.java.barbershop.api.service.ServiceService;
import ru.edu.java.barbershop.domain.entity.ServiceEntity;
import ru.edu.java.barbershop.domain.repository.ServiceRepository;
import ru.edu.java.barbershop.dto.response.ServiceResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class JpaServiceService implements ServiceService {
    private final ServiceRepository serviceRepository;
    @Override
    public ServiceResponse getService(UUID id) {
        return serviceRepository.findById(id).orElseThrow().toResponse();
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll().stream().map(ServiceEntity::toResponse).toList();
    }

    @Override
    public ServiceResponse addService(String name, String description, Integer cost) {
        ServiceEntity service = new ServiceEntity();
        service.setType(name);
        service.setDescription(description);
        service.setCost(cost);
        return serviceRepository.save(service).toResponse();
    }

    @Override
    public ServiceResponse deleteService(UUID id) {
        ServiceEntity service = serviceRepository.findById(id).orElseThrow();
        serviceRepository.deleteById(id);
        return service.toResponse();
    }

    @Override
    public ServiceResponse changeName(UUID id, String name) {
        ServiceEntity service = serviceRepository.findById(id).orElseThrow();
        service.setType(name);
        return serviceRepository.save(service).toResponse();
    }

    @Override
    public ServiceResponse changeDescription(UUID id, String description) {
        ServiceEntity service = serviceRepository.findById(id).orElseThrow();
        service.setDescription(description);
        return serviceRepository.save(service).toResponse();
    }

    @Override
    public ServiceResponse changeCost(UUID id, Integer cost) {
        ServiceEntity service = serviceRepository.findById(id).orElseThrow();
        service.setCost(cost);
        return serviceRepository.save(service).toResponse();
    }
}
