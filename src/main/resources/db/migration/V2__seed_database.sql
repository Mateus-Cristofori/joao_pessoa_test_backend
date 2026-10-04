create extension if not exists pgcrypto;

insert into users (id, name, email, password, created_at) values
('3f8a1c52-7b94-4d6e-9a21-5c0e8d4b7f13', 'Admin', 'admin@email.com', crypt('123456', gen_salt('bf', 10)), now()),
('b27e9d04-61ac-4f38-8e5b-2a9c13d70e46', 'Maria Silva', 'maria@email.com', crypt('123456', gen_salt('bf', 10)), now()),
('9d41f6a8-0c3e-4b72-a6d9-e18f5b2c4a07', 'João Souza', 'joao@email.com', crypt('123456', gen_salt('bf', 10)), now()),
('4a1b2c3d-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'Ana Costa', 'ana@email.com', crypt('123456', gen_salt('bf', 10)), now()),
('7f8e9d0c-1b2a-3c4d-5e6f-7a8b9c0d1e2f', 'Carlos Mendes', 'carlos@email.com', crypt('123456', gen_salt('bf', 10)), now());

insert into ticket (id, user_id, code, title, description, category, status, created_at, updated_at) values
('5e2c8b91-d4a7-4136-b0f3-7a69c12e8d54', 'b27e9d04-61ac-4f38-8e5b-2a9c13d70e46', 'TCK-0001', 'Erro ao fazer login', 'Ao tentar entrar, a tela fica em branco.', 'IT', 'OPEN', now() - interval '3 days', null),
('c8a07f35-2e61-49bd-8c14-d3b5e970a2f6', 'b27e9d04-61ac-4f38-8e5b-2a9c13d70e46', 'TCK-0002', 'Solicitação de novo relatório', 'Preciso de um relatório mensal de tickets.', 'FINANCE', 'IN_PROGRESS', now() - interval '2 days', now() - interval '1 day'),
('14d9b6e2-8f50-4a7c-9e38-60c1f2a7b5d9', '9d41f6a8-0c3e-4b72-a6d9-e18f5b2c4a07', 'TCK-0003', 'Dúvida sobre acesso', 'Como altero minha senha?', 'IT', 'OPEN', now() - interval '1 day', null),
('2b3c4d5e-6f7a-8b9c-0d1e-2f3a4b5c6d7e', '4a1b2c3d-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'TCK-0004', 'Erro na VPN corporativa', 'Não consigo conectar na VPN da empresa de casa.', 'IT', 'OPEN', now() - interval '2 hours', null),
('8c9d0e1f-2a3b-4c5d-6e7f-8a9b0c1d2e3f', '7f8e9d0c-1b2a-3c4d-5e6f-7a8b9c0d1e2f', 'TCK-0005', 'Reembolso de hospedagem', 'Enviei os comprovantes da última viagem a trabalho.', 'FINANCE', 'IN_PROGRESS', now() - interval '4 hours', now() - interval '1 hour'),
('3d4e5f6a-7b8c-9d0e-1f2a-3b4c5d6e7f8a', '4a1b2c3d-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'TCK-0006', 'Manutenção no ar-condicionado', 'A sala de reuniões principal está com o aparelho pingando.', 'INFRASTRUCTURE', 'COMPLETED', now() - interval '6 hours', now() - interval '30 minutes'),
('9e0f1a2b-3c4d-5e6f-7a8b-9c0d1e2f3a4b', '7f8e9d0c-1b2a-3c4d-5e6f-7a8b9c0d1e2f', 'TCK-0007', 'Dúvida sobre benefícios', 'Onde encontro o espelho do plano de saúde atualizado?', 'HR', 'COMPLETED', now() - interval '5 days', now() - interval '4 days'),
('1f2a3b4c-5d6e-7f8a-9b0c-1d2e3f4a5b6c', '4a1b2c3d-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'TCK-0008', 'Compra de novo monitor', 'Solicitação de um segundo monitor 27 polegadas para desenvolvimento.', 'PURCHASES', 'OPEN', now() - interval '2 days', null);