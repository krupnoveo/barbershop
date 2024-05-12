package ru.edu.java.barbershop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import ru.edu.java.barbershop.IntegrationEnvironment;
import ru.edu.java.barbershop.api.service.jpa.JpaRecordAvailableTimeService;
import ru.edu.java.barbershop.dto.response.RecordAvailableTimeResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback
public class JpaRecordAvailableTimeServiceTest extends IntegrationEnvironment {

    @Autowired
    private JpaRecordAvailableTimeService service;

    @Autowired
    private JdbcClient client;

    @Test
    public void getAllTimes_shouldReturnCorrectResult() {
        OffsetDateTime time = OffsetDateTime.of(2024, 5, 10, 12, 0, 0, 0, ZoneOffset.UTC);
        UUID barberId = client.sql("insert into user_entity(user_name, email, password, user_role) values('', '', '', 'barber') returning id").query(UUID.class).single();
        UUID timeId1 = client.sql("insert into record_available_time(time, barber_id) values(?, ?) returning id").params(time, barberId).query(UUID.class).single();
        time = time.plusDays(1);
        UUID timeId2 = client.sql("insert into record_available_time(time, barber_id) values(?, ?) returning id").params(time, barberId).query(UUID.class).single();
        List<RecordAvailableTimeResponse> actual = service.getAllTimes(barberId);
        List<RecordAvailableTimeResponse> expected = List.of(
                new RecordAvailableTimeResponse(timeId1, time.minusDays(1), barberId),
                new RecordAvailableTimeResponse(timeId2, time, barberId)
        );
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void getTime_shouldReturnCorrectResult() {
        OffsetDateTime time = OffsetDateTime.of(2024, 5, 10, 12, 0, 0, 0, ZoneOffset.UTC);
        UUID barberId = client.sql("insert into user_entity(user_name, email, password, user_role) values('', '', '', 'barber') returning id").query(UUID.class).single();
        UUID timeId1 = client.sql("insert into record_available_time(time, barber_id) values(?, ?) returning id").params(time, barberId).query(UUID.class).single();
        RecordAvailableTimeResponse response = service.getTime(timeId1);
        RecordAvailableTimeResponse expected = new RecordAvailableTimeResponse(timeId1, time, barberId);
        assertThat(response).isEqualTo(expected);
    }
}
