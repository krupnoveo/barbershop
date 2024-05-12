package ru.edu.java.barbershop.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import ru.edu.java.barbershop.IntegrationEnvironment;
import ru.edu.java.barbershop.api.service.jpa.JpaServiceService;
import ru.edu.java.barbershop.dto.response.ServiceResponse;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class JpaServiceServiceTest extends IntegrationEnvironment {

    @Autowired
    private JdbcClient client;
    @Autowired
    private EntityManager manager;

    @Autowired
    private JpaServiceService service;

    @Test
    public void getService() {
        UUID id = client.sql("insert into service(service_type, description, cost) values('стрижка', 'usual', 1000) returning id").query(UUID.class).single();
        ServiceResponse actual = service.getService(id);
        ServiceResponse expected = new ServiceResponse(id, "стрижка", "usual", 1000);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getAllServices() {
        UUID id1 = client.sql("insert into service(service_type, description, cost) values('стрижка1', 'usual', 1000) returning id").query(UUID.class).single();
        UUID id2 = client.sql("insert into service(service_type, description, cost) values('стрижка2', 'usual', 1000) returning id").query(UUID.class).single();
        List<ServiceResponse> actual = service.getAllServices();
        List<ServiceResponse> expected = List.of(
                new ServiceResponse(id1, "стрижка1", "usual", 1000),
                new ServiceResponse(id2, "стрижка2", "usual", 1000)
        );
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void addService() {
        ServiceResponse resp = service.addService("haircut", "usual", 1000);
        manager.flush();
        assertThat(client.sql("select id from service where service_type='haircut'").query(UUID.class).single()).isEqualTo(resp.id());
    }

    @Test
    public void changeName() {
        UUID id1 = client.sql("insert into service(service_type, description, cost) values('стрижка1', 'usual', 1000) returning id").query(UUID.class).single();
        service.changeName(id1, "newName");
        manager.flush();
        assertThat(client.sql("select service_type from service where id=?").param(id1).query(String.class).single()).isEqualTo("newName");
    }

    @Test
    public void changeDescription() {
        UUID id1 = client.sql("insert into service(service_type, description, cost) values('стрижка1', 'usual', 1000) returning id").query(UUID.class).single();
        service.changeDescription(id1, "newDescription");
        manager.flush();
        assertThat(client.sql("select description from service where id=?").param(id1).query(String.class).single()).isEqualTo("newDescription");
    }

    @Test
    public void changeCost() {
        UUID id1 = client.sql("insert into service(service_type, description, cost) values('стрижка1', 'usual', 1000) returning id").query(UUID.class).single();
        service.changeCost(id1, 1000);
        manager.flush();
        assertThat(client.sql("select cost from service where id=?").param(id1).query(Integer.class).single()).isEqualTo(1000);
    }
}
