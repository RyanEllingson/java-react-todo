create table users(
    user_id int unique not null generated always as identity,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(30) unique not null,
    password varchar(255) not null,
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