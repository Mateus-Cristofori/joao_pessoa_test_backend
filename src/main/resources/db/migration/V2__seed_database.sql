create extension if not exists pgcrypto;

insert into users (id, name, email, password, created_at) values
('3f8a1c52-7b94-4d6e-9a21-5c0e8d4b7f13', 'Admin', 'admin@email.com', crypt('123456', gen_salt('bf', 10)), now());
