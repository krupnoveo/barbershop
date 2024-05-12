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
import ru.edu.java.barbershop.api.service.jpa.JpaUserService;
import ru.edu.java.barbershop.api.service.security.JwtService;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
@ExtendWith(MockitoExtension.class)
public class JpaUserServiceTest extends IntegrationEnvironment {
    @Autowired
    private EntityManager manager;

    @Autowired
    private JpaUserService service;

    @Autowired
    private JdbcClient client;

    @MockBean
    private JwtService jwtService;

    @Test
    public void getUser() {
        UUID uuid = client.sql("insert into user_entity(user_name, user_role, email, password) values('evgenij', 'client', 'example@mail.ru', '1234') returning id").query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(uuid);
        UserResponse response = service.getUser("token");
        assertThat(response.id()).isEqualTo(uuid);
    }

    @Test
    public void changeName() {
        UUID uuid = client.sql("insert into user_entity(user_name, user_role, email, password) values('evgenij', 'client', 'example@mail.ru', '1234') returning id").query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(uuid);
        service.changeName("token", "name");
        manager.flush();
        assertThat(client.sql("select user_name from user_entity where id=?").param(uuid).query(String.class).single()).isEqualTo("name");
    }

    @Test
    public void changePhoneNumber() {
        UUID uuid = client.sql("insert into user_entity(user_name, user_role, email, password) values('evgenij', 'client', 'example@mail.ru', '1234') returning id").query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(uuid);
        service.changePhoneNumber("token","89164033906");
        manager.flush();
        assertThat(client.sql("select phone_number from user_entity where id=?").param(uuid).query(String.class).single()).isEqualTo("89164033906");
    }

    @Test
    public void changeEmail() {
        UUID uuid = client.sql("insert into user_entity(user_name, user_role, email, password) values('evgenij', 'client', 'example@mail.ru', '1234') returning id").query(UUID.class).single();
        Mockito.when(jwtService.extractUserId("token")).thenReturn(uuid);
        service.changeEmail("token","newEmail");
        manager.flush();
        assertThat(client.sql("select email from user_entity where id=?").param(uuid).query(String.class).single()).isEqualTo("newEmail");
    }
}
