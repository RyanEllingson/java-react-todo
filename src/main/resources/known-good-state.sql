create or replace procedure known_good_state()
language plpgsql
as $$
begin

drop table if exists todos;

drop table if exists users;

create table users(
    user_id int unique not null generated always as identity,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(30) unique not null,
    password varchar(255) not null,
    reset_code varchar(255),
    primary key (user_id)
);

create table todos(
    todo_id int unique not null generated always as identity,
    user_id int not null,
    task varchar(255) not null,
    completed boolean not null,
    primary key (todo_id),
    foreign key (user_id) references users(user_id)
);

insert into users (first_name, last_name, email, password) values ('Testy', 'Testerson', 'test@test.com', 'password'), ('Bla', 'Blah', 'blah@blah.com', 'blabla');

insert into todos (user_id, task, completed) values (2, 'write test for todos', false);

end;$$