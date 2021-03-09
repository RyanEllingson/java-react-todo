create or replace procedure known_good_state()
language plpgsql
as $$
begin

drop table if exists users;

create table users(
    user_id int unique not null generated always as identity,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(30) unique not null,
    password varchar(255) not null,
    primary key (user_id)
);

insert into users (first_name, last_name, email, password) values ('Testy', 'Testerson', 'test@test.com', 'password');

end;$$