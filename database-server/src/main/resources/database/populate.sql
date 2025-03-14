
-- subscription-service

create table if not exists discount (
    id bigint generated by default as identity unique not null ,
    name varchar not null ,
    description text not null ,
    percentage bigint not null check ( percentage >= 0 and percentage <= 100) ,
    starts date not null ,
    ends date not null ,
    actual boolean not null
);

create table if not exists plan (
    id bigint generated by default as identity unique not null ,
    name varchar not null ,
    price bigint not null ,
    period varchar not null ,
    scores bigint not null check ( scores >= 0 ) default 0,
    type varchar not null ,
    discount_id bigint references discount(id) on delete set null on update cascade
);

create table if not exists subscription (
    id bigint generated by default as identity unique not null ,
    plan_id bigint references plan(id) on delete set null on update cascade ,
    scores bigint check ( scores >= 0 ) default 0,
    paid_at date not null ,
    next_payment date not null ,
    cancelled boolean not null
);

-- image-service

create table if not exists image (
    id bigint generated by default as identity unique not null ,
    name varchar ,
    content_type varchar ,
    size bigint ,
    data bytea not null
);

-- identity-server

create table if not exists authority (
    id bigint generated by default as identity unique not null ,
    name varchar unique not null
);

create table if not exists identity (
    id bigint generated by default as identity unique not null ,
    username varchar unique not null ,
    password varchar not null ,
    email varchar unique not null ,
    image_id bigint unique references image(id) on delete set null on update cascade ,
    registered date not null ,
    authority_id bigint references authority(id) on delete set null on update cascade ,
    subscription_id bigint references subscription(id) on delete set null on update cascade
);

-- payment-service

create table if not exists payment (
    id bigint generated by default as identity unique not null ,
    identity_id bigint references identity(id) on delete set null on update cascade ,
    subscription_id bigint references subscription(id) on delete set null on update cascade ,
    price bigint not null ,
    paid_at timestamp not null
);

create table if not exists identity_payment_token (
    identity_id bigint references identity(id) on delete cascade on update cascade ,
    payment_id bigint references payment(id) on delete cascade on update cascade ,
    token uuid ,
    primary key (identity_id, payment_id)
);

-- producer-service

create table if not exists label (
    id bigint generated by default as identity unique not null ,
    title varchar not null ,
    description text not null ,
    boss_id bigint not null unique ,
    banner_id bigint unique references image(id) on delete set null on update cascade
);

create table if not exists producer (
    id bigint generated by default as identity unique not null ,
    nickname varchar not null ,
    firstname varchar not null ,
    lastname varchar not null ,
    patronymic varchar not null ,
    phone varchar ,
    about text ,
    identity_id bigint unique references identity(id) on delete cascade on update cascade ,
    label_id bigint unique references label(id) on delete set null on update cascade ,
    banner_id bigint unique references image(id) on delete set null on update cascade
);

create table if not exists identity_producer_token (
    identity_id bigint references identity(id) on delete cascade on update cascade ,
    producer_id bigint references producer(id) on delete cascade on update cascade ,
    token uuid ,
    primary key (identity_id, producer_id)
);

-- sound-service

create table if not exists genre (
    id bigint generated by default as identity unique not null ,
    name varchar not null ,
    description text not null
);

create table if not exists pack (
    id bigint generated by default as identity unique not null ,
    title varchar not null ,
    description text not null ,
    scores bigint check ( scores >= 0 ) default 0,
    genre_id bigint references genre(id) on delete set null on update cascade ,
    image_id bigint references image(id) on delete set null on update cascade ,
    label_id bigint references label(id) on delete cascade on update cascade ,
    demo_id bigint unique
);

create table if not exists category (
    id bigint generated by default as identity unique not null ,
    name varchar not null
);

create table if not exists key (
    id bigint generated by default as identity unique not null ,
    harmony varchar not null ,
    key varchar not null ,
    type varchar
);

create table if not exists instrument (
    id bigint generated by default as identity unique not null ,
    name varchar not null unique
);

create table if not exists sample (
    id bigint generated by default as identity unique not null ,
    name varchar not null ,
    content_type varchar not null ,
    format varchar not null ,
    bpm bigint ,
    key_id bigint references key(id) on delete set null on update cascade ,
    size bigint not null ,
    category_id bigint references category(id) on delete set null on update cascade ,
    pack_id bigint references pack(id) on delete cascade on update cascade ,
    scores bigint check ( scores >= 0 ) default 1,
    data bytea not null ,
    additions bigint not null check ( additions >= 0 ) default 0
);

create table if not exists tag (
    id bigint generated by default as identity unique not null ,
    name varchar not null unique
);

create table if not exists sample_tag (
    sample_id bigint references sample(id) on delete cascade on update cascade ,
    tag_id bigint references tag(id) on delete cascade on update cascade ,
    primary key (sample_id, tag_id)
);

create table if not exists identity_sample (
    identity_id bigint references identity(id) on delete cascade on update cascade ,
    sample_id bigint references sample(id) on delete cascade on update cascade ,
    primary key (identity_id, sample_id)
);



-- POPULATIONS



insert into plan(name, price, period, scores, type) VALUES ('Monthly Low User Plan', 20,'monthly',100,'user');
insert into plan(name, price, period, scores, type) VALUES ('Monthly Middle User Plan', 30,'monthly',250,'user');
insert into plan(name, price, period, scores, type) VALUES ('Monthly High User Plan', 45,'monthly',500,'user');
insert into plan(name, price, period, scores, type) VALUES ('Annual Low User Plan', 220,'annual',2200,'user');
insert into plan(name, price, period, scores, type) VALUES ('Annual Middle User Plan', 300,'annual',3500,'user');
insert into plan(name, price, period, scores, type) VALUES ('Annual High User Plan', 450,'annual',5000,'user');

insert into plan(name, price, period, scores, type) VALUES ('Monthly Low Producer Plan', 40,'monthly',400,'producer');
insert into plan(name, price, period, scores, type) VALUES ('Monthly Middle Producer Plan', 70,'monthly',750,'producer');
insert into plan(name, price, period, scores, type) VALUES ('Monthly High Producer Plan', 90,'monthly',1000,'producer');
insert into plan(name, price, period, scores, type) VALUES ('Annual Low Producer Plan', 400,'annual',4000,'producer');
insert into plan(name, price, period, scores, type) VALUES ('Annual Middle Producer Plan', 700,'annual',7500,'producer');
insert into plan(name, price, period, scores, type) VALUES ('Annual High Producer Plan', 900,'annual',10000,'producer');

insert into subscription(plan_id, scores, paid_at, next_payment, cancelled)
values (9,1000,'2025-03-07','2025-04-07',false);

insert into subscription(plan_id, scores, paid_at, next_payment, cancelled)
values (4,2200,'2025-03-07','2025-04-07',false);

insert into authority(name) values ('ROLE_ADMIN');
insert into authority(name) values ('ROLE_PRODUCER');
insert into authority(name) values ('ROLE_USER');

insert into identity(username, password, email, registered, authority_id)
values ('admin','$2a$10$1Ez.YrFVzzdsFq7FvUhSN.zh4aQTLzro00lWkOsB5Xkfa9KOO0g.S',
        'admin@gmail.com','2024-05-16',1);

insert into identity(username, password, email, registered, authority_id, subscription_id)
values ('producer','$2a$10$xkxrBnwEGUl5ArbNy1apseFQnStZvPiaPjrWx9A5err19QCPKNI0W',
        'producer@gmail.com','2024-08-10',2, 1);

insert into identity(username, password, email, registered, authority_id, subscription_id)
values ('user','$2a$10$M3ovZqwFz1gfipD/9.2/jufxq60q0h.o8N5XDVMSPLiyT/Vasg58e',
        'user@gmail.com','2024-11-24',3, 2);

insert into label(title, description, boss_id, banner_id)
values ('Anthology Label', 'New Anthology Label description', 1, null);

insert into producer(nickname, firstname, lastname, patronymic, phone, about, identity_id, label_id, banner_id)
VALUES ('BestProducer','Вартаев','Игорь','Клементьев','9139034678',
        'Some description and information about producer',2,1,null);

insert into identity_producer_token(identity_id, producer_id, token)
VALUES (2, 1, gen_random_uuid());

insert into payment(identity_id, subscription_id, price, paid_at)
VALUES (2, 1, 90, '2025-04-07 21:15');

insert into payment(identity_id, subscription_id, price, paid_at)
VALUES (3, 2, 220, '2025-04-07 22:36');

insert into identity_payment_token(identity_id, payment_id, token)
values (2, 1, gen_random_uuid());

insert into identity_payment_token(identity_id, payment_id, token)
values (3, 2, gen_random_uuid());

insert into genre(name, description) VALUES ('Hip-Hop','New Hip-Hop description');
insert into genre(name, description) VALUES ('R&B','New R&B description');
insert into genre(name, description) VALUES ('Trap','New Trap description');
insert into genre(name, description) VALUES ('Soul','New Soul description');
insert into genre(name, description) VALUES ('Drill','New Drill description');
insert into genre(name, description) VALUES ('Lo-Fi Hip-Hop','New Lo-Fi Hip-Hop description');
insert into genre(name, description) VALUES ('Reggaeton','New Reggaeton description');
insert into genre(name, description) VALUES ('Dancehall','New Dancehall description');
insert into genre(name, description) VALUES ('Future Bass','New Soul description');
insert into genre(name, description) VALUES ('Pop','New Pop description');
insert into genre(name, description) VALUES ('Techno','New Techno description');
insert into genre(name, description) VALUES ('House','New House description');
insert into genre(name, description) VALUES ('Deep House','New Deep House description');
insert into genre(name, description) VALUES ('Disco','New Disco description');
insert into genre(name, description) VALUES ('Electro','New Electro description');
insert into genre(name, description) VALUES ('EDM','New EDM description');
insert into genre(name, description) VALUES ('Trance','New Trance description');
insert into genre(name, description) VALUES ('Rock','New Rock description');
insert into genre(name, description) VALUES ('Jazz','New Jazz description');
insert into genre(name, description) VALUES ('Blues','New Blues description');
insert into genre(name, description) VALUES ('Gospel','New Gospel description');
insert into genre(name, description) VALUES ('Dubstep','New Dubstep description');
insert into genre(name, description) VALUES ('Ambient','New Ambient description');
insert into genre(name, description) VALUES ('Drum and Bass','New Drum and Bass description');

insert into category(name) values ('One-Shots');
insert into category(name) values ('Loops');
insert into category(name) values ('Vocals');
insert into category(name) values ('FX');

insert into key(harmony, key, type) VALUES ('major','C',null);
insert into key(harmony, key, type) VALUES ('major','Cb','Flat');
insert into key(harmony, key, type) VALUES ('major','C#','Sharp');
insert into key(harmony, key, type) VALUES ('major','D',null);
insert into key(harmony, key, type) VALUES ('major','Db','Flat');
insert into key(harmony, key, type) VALUES ('major','D#','Sharp');
insert into key(harmony, key, type) VALUES ('major','E',null);
insert into key(harmony, key, type) VALUES ('major','Eb','Flat');
insert into key(harmony, key, type) VALUES ('major','E#','Sharp');
insert into key(harmony, key, type) VALUES ('major','F',null);
insert into key(harmony, key, type) VALUES ('major','F#','Sharp');
insert into key(harmony, key, type) VALUES ('major','G',null);
insert into key(harmony, key, type) VALUES ('major','Gb','Flat');
insert into key(harmony, key, type) VALUES ('major','G#','Sharp');
insert into key(harmony, key, type) VALUES ('major','A',null);
insert into key(harmony, key, type) VALUES ('major','Ab','Flat');
insert into key(harmony, key, type) VALUES ('major','A#','Sharp');
insert into key(harmony, key, type) VALUES ('major','B',null);
insert into key(harmony, key, type) VALUES ('major','Bb','Flat');

insert into key(harmony, key, type) VALUES ('minor','C',null);
insert into key(harmony, key, type) VALUES ('minor','Cb','Flat');
insert into key(harmony, key, type) VALUES ('minor','C#','Sharp');
insert into key(harmony, key, type) VALUES ('minor','D',null);
insert into key(harmony, key, type) VALUES ('minor','Db','Flat');
insert into key(harmony, key, type) VALUES ('minor','D#','Sharp');
insert into key(harmony, key, type) VALUES ('minor','E',null);
insert into key(harmony, key, type) VALUES ('minor','Eb','Flat');
insert into key(harmony, key, type) VALUES ('minor','E#','Sharp');
insert into key(harmony, key, type) VALUES ('minor','F',null);
insert into key(harmony, key, type) VALUES ('minor','F#','Sharp');
insert into key(harmony, key, type) VALUES ('minor','G',null);
insert into key(harmony, key, type) VALUES ('minor','Gb','Flat');
insert into key(harmony, key, type) VALUES ('minor','G#','Sharp');
insert into key(harmony, key, type) VALUES ('minor','A',null);
insert into key(harmony, key, type) VALUES ('minor','Ab','Flat');
insert into key(harmony, key, type) VALUES ('minor','A#','Sharp');
insert into key(harmony, key, type) VALUES ('minor','B',null);
insert into key(harmony, key, type) VALUES ('minor','Bb','Flat');

insert into instrument(name) values ('Kicks');
insert into instrument(name) values ('Snares');
insert into instrument(name) values ('Hi-Hats');
insert into instrument(name) values ('Toms');
insert into instrument(name) values ('Low-Hats');
insert into instrument(name) values ('Cymbals');
insert into instrument(name) values ('Claps');
insert into instrument(name) values ('Bass');
insert into instrument(name) values ('Leads');
insert into instrument(name) values ('Arpeggio');
insert into instrument(name) values ('Stabs');
insert into instrument(name) values ('Plucks');
insert into instrument(name) values ('Synths');
insert into instrument(name) values ('Violins');
insert into instrument(name) values ('Strings');
insert into instrument(name) values ('Violas');
insert into instrument(name) values ('Organs');
insert into instrument(name) values ('Piano');
insert into instrument(name) values ('Keys');
insert into instrument(name) values ('Brass');
insert into instrument(name) values ('Saxophones');
insert into instrument(name) values ('Trumpets');
insert into instrument(name) values ('Trombones');
insert into instrument(name) values ('Flutes');

insert into pack(title, description, genre_id, image_id, label_id, demo_id)
VALUES ('Neo Soul R&B','Neo soul R&B new description for this pack',2,null,1,null);