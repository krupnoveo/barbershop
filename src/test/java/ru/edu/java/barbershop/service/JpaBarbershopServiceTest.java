package ru.edu.java.barbershop.service;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import ru.edu.java.barbershop.IntegrationEnvironment;
import ru.edu.java.barbershop.api.service.jpa.JpaBarbershopService;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.dto.response.BarbershopResponse;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class JpaBarbershopServiceTest extends IntegrationEnvironment {
    @Autowired
    private JpaBarbershopService barbershopService;

    @Autowired
    private EntityManager manager;

    @Autowired
    private JdbcClient client;


    @Test
    public void addBarbershop_shouldWorkCorrectly() {
        barbershopService.addBarbershop("address");
        manager.flush();
        assertThat(client.sql("select address from barbershop where address=?").param("address").query(String.class).list()).isNotEmpty();
    }

    @Test
    public void deleteBarbershop_shouldWorkCorrectly() {
        BarbershopResponse barbershopResponse = barbershopService.addBarbershop("address");
        manager.flush();
        assertThat(client.sql("select address from barbershop where address=?").param("address").query(String.class).list()).isNotEmpty();
        barbershopService.deleteBarbershop(barbershopResponse.id());
        manager.flush();
        assertThat(client.sql("select address from barbershop where address=?").param("address").query(String.class).list()).isEmpty();
    }

    @Test
    public void changeAddress_shouldWorkCorrectly() {
        BarbershopResponse barbershopResponse = barbershopService.addBarbershop("address");
        manager.flush();
        assertThat(client.sql("select address from barbershop where address=?").param("address").query(String.class).list()).isNotEmpty();
        BarbershopResponse response = barbershopService.changeAddress(barbershopResponse.id(), "newAddress");
        manager.flush();
        assertThat(response.address()).isEqualTo("newAddress");
    }

    @Test
    public void getAllBarbershops_shouldWorkCorrectly() {
        BarbershopResponse barbershopResponse1 = barbershopService.addBarbershop("address1");
        BarbershopResponse barbershopResponse2 = barbershopService.addBarbershop("address2");
        manager.flush();
        assertThat(client.sql("select address from barbershop where address=?").param("address1").query(String.class).list()).isNotEmpty();
        assertThat(client.sql("select address from barbershop where address=?").param("address1").query(String.class).list()).isNotEmpty();
        List<BarbershopResponse> responseList = barbershopService.getAllBarbershops();
        assertThat(responseList).containsExactlyInAnyOrderElementsOf(List.of(barbershopResponse1, barbershopResponse2));
    }

    @Test
    public void getAllBarbers_shouldWorkCorrectly() {
        UUID id1 = client.sql("insert into user_entity(user_name, email, password, user_role) values('Evgenij', 'barber1@mail.ru', '1234', 'barber') returning id").query(UUID.class).single();
        UUID id2 = client.sql("insert into user_entity(user_name, email, password, user_role) values('Ivan', 'barber2@mail.ru', '1234', 'barber') returning id").query(UUID.class).single();
        UserEntity barber1 = new UserEntity();
        UserEntity barber2 = new UserEntity();
        barber1.setEmail("barber1@mail.ru");
        barber1.setRole("barber");
        barber1.setPassword("1234");
        barber1.setName("Evgenij");
        barber1.setId(id1);
        barber2.setName("Ivan");
        barber2.setRole("barber");
        barber2.setPassword("1234");
        barber2.setEmail("barber2@mail.ru");
        barber2.setId(id2);
        manager.flush();
        List<UserResponse> barbers = barbershopService.getAllBarbers();
        assertThat(barbers).containsExactlyInAnyOrderElementsOf(List.of(barber1.toResponse().build(), barber2.toResponse().build()));
    }

    @Test
    public void getBarber_shouldWorkCorrectly() {
        UUID id = client.sql("insert into user_entity(user_name, email, password, user_role) values('Evgenij', 'barber1@mail.ru', '1234', 'barber') returning id").query(UUID.class).single();
        UserEntity barber1 = new UserEntity();
        barber1.setEmail("barber1@mail.ru");
        barber1.setRole("barber");
        barber1.setPassword("1234");
        barber1.setName("Evgenij");
        barber1.setId(id);
        manager.flush();
        UserResponse actual = barbershopService.getBarber(id);
        assertThat(actual).isEqualTo(barber1.toResponse().build());
    }
}
