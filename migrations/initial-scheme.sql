create table if not exists barbershop
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    address TEXT NOT NULL
);

create table if not exists user_entity
(
    id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_name     TEXT        NOT NULL,
    phone_number  TEXT        NULL UNIQUE,
    email         TEXT        NOT NULL UNIQUE,
    password      TEXT        NOT NULL,
    user_role     TEXT        NOT NULL,
    barbershop_id UUID        NULL REFERENCES barbershop (id)
);

create table if not exists service
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    service_type TEXT NOT NULL,
    description  TEXT NOT NULL,
    cost         INT  NOT NULL
);

create table if not exists record_available_time
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    time      TIMESTAMP WITH TIME ZONE,
    barber_id UUID REFERENCES user_entity (id) NOT NULL
);

create table if not exists record
(
    id             UUID      PRIMARY KEY DEFAULT gen_random_uuid(),
    time_of_record UUID      REFERENCES record_available_time (id) NOT NULL,
    client_id      UUID      REFERENCES user_entity (id) NOT NULL,
    service_id     UUID      REFERENCES service (id)     NOT NULL,
    barber_id      UUID      REFERENCES user_entity (id) NOT NULL
);

INSERT INTO user_entity(user_name, phone_number, email, password, user_role, barbershop_id) VALUES ('admin', null, 'admin', '$2y$12$WHMoHyVjoCnBQNvVmVMKROJ1wMshfB26z6K6xVE/V9Xiit.6txF5K', 'admin', null)