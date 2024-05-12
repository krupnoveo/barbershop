package ru.edu.java.barbershop.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import ru.edu.java.barbershop.IntegrationEnvironment;
import ru.edu.java.barbershop.api.service.jpa.JpaRecordService;
import ru.edu.java.barbershop.api.service.security.JwtService;
import ru.edu.java.barbershop.dto.request.RecordCreateRequest;
import ru.edu.java.barbershop.dto.response.RecordResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
@ExtendWith(MockitoExtension.class)
public class JpaRecordServiceTest extends IntegrationEnvironment {
    @Autowired
    private EntityManager manager;
    @Autowired
    private JpaRecordService service;

    @Autowired
    private JdbcClient client;

    @MockBean
    private JwtService jwtService;
    @Test
    public void addRecord_shouldWorkCorrectly() {
        UUID barbershopId = client.sql("insert into barbershop(address) values('улица') returning id").query(UUID.class).single();
        UUID clientId = client.sql("insert into user_entity(user_name, user_role, email, password) values('vasya', 'client', '11', '1234') returning id").query(UUID.class).single();
        UUID barberId = client.sql("insert into user_entity(user_name, user_role, email, password, barbershop_id) values('petya', 'barber', '22', '1234', ?) returning id").param(barbershopId).query(UUID.class).single();
        UUID serviceId = client.sql("insert into service(service_type, description, cost) values('стрижка', 'usual', 1000) returning id").query(UUID.class).single();
        OffsetDateTime time = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);;
        UUID timeId = client.sql("insert into record_available_time(time, barber_id) values(?, ?) returning id").params(time, barberId).query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(clientId);
        RecordResponse actual = service.addRecord(new RecordCreateRequest(serviceId, barberId, timeId), "token");
        RecordResponse expected = new RecordResponse(actual.recordId(), "vasya", "petya", "стрижка", "улица", time, 1000);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getRecord_shouldReturnCorrectResult() {
        UUID barbershopId = client.sql("insert into barbershop(address) values('улица') returning id").query(UUID.class).single();
        UUID clientId = client.sql("insert into user_entity(user_name, user_role, email, password) values('vasya', 'client', '11', '1234') returning id").query(UUID.class).single();
        UUID barberId = client.sql("insert into user_entity(user_name, user_role, email, password, barbershop_id) values('petya', 'barber', '22', '1234', ?) returning id").param(barbershopId).query(UUID.class).single();
        UUID serviceId = client.sql("insert into service(service_type, description, cost) values('стрижка', 'usual', 1000) returning id").query(UUID.class).single();
        OffsetDateTime time = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
        UUID timeId = client.sql("insert into record_available_time(time, barber_id) values(?, ?) returning id").params(time, barberId).query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(clientId);
        RecordResponse actual = service.addRecord(new RecordCreateRequest(serviceId, barberId, timeId), "token");
        RecordResponse expected = new RecordResponse(actual.recordId(), "vasya", "petya", "стрижка", "улица", time, 1000);
        assertThat(service.getRecord(actual.recordId())).isEqualTo(expected);
    }

    @Test
    public void cancelRecord_shouldWorkCorrectly() {
        UUID barbershopId = client.sql("insert into barbershop(address) values('улица') returning id").query(UUID.class).single();
        UUID clientId = client.sql("insert into user_entity(user_name, user_role, email, password) values('vasya', 'client', '11', '1234') returning id").query(UUID.class).single();
        UUID barberId = client.sql("insert into user_entity(user_name, user_role, email, password, barbershop_id) values('petya', 'barber', '22', '1234', ?) returning id").param(barbershopId).query(UUID.class).single();
        UUID serviceId = client.sql("insert into service(service_type, description, cost) values('стрижка', 'usual', 1000) returning id").query(UUID.class).single();
        OffsetDateTime time = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
        UUID timeId = client.sql("insert into record_available_time(time, barber_id) values(?, ?) returning id").params(time, barberId).query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(clientId);
        RecordResponse actual = service.addRecord(new RecordCreateRequest(serviceId, barberId, timeId), "token");
        RecordResponse expected = new RecordResponse(actual.recordId(), "vasya", "petya", "стрижка", "улица", time, 1000);
        assertThat(service.getRecord(actual.recordId())).isEqualTo(expected);
        manager.flush();
        service.cancelRecord(actual.recordId());
        manager.flush();
        assertThat(client.sql("select id from record where client_id=?").param(clientId).query(UUID.class).list()).isEmpty();
    }
}
