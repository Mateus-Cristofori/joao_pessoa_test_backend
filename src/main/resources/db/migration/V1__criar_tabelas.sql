create table users (
   id uuid primary key,
   name varchar(255) not null,
   email varchar(255) not null,
   password varchar(255) not null,
   created_at timestamp(6)
);

create table ticket (
    id uuid primary key,
    user_id uuid not null,
    code varchar(255) not null,
    title varchar(255) not null,
    description varchar(255) not null,
    category varchar(30) not null,
    status varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6),
    constraint uk_ticket_code unique (code)
);