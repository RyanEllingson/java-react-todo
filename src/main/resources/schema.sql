create table users(
    user_id int unique not null generated always as identity,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(30) not null,
    password varchar(255) not null,
    primary key (user_id)
);