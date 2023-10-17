-- Insere o Docente admin
-- SENHA = 123456
insert into `user` (email, nome, `role`, senha, sobrenome, token, confirmed) values ('admin@uscsonline.com.br', 'Admin', 'DOCENTE', '$2a$10$QrBWPzOZb1SG3Rx5DlM9H.0sQhc.J7.q/2jHabqIKE0.hv5gqY.Q2', 'Agenda', null, 1);
insert into `docente` (ruscs, id) values ('0000000', 1);

-- Insere Paciente de teste
-- SENHA = teste1
insert into `user` (confirmed, email, nome, `role`, senha, sobrenome, token) values (1, 'paciente@dominio.com.br', 'Roberta', 'PACIENTE', '$2a$10$iWfH/HZjVVS5Gs8lTkjfKuY3PjloqpljPREAsgaUGn9S5ifjrLLDS', 'Bochetti', null);

insert into `paciente` (data_nascimento, endereco_bairro, endereco_cep, endereco_cidade, endereco_rua, estado_civil, estagiario_id, etnia_racial, genero, is_menor_idade, nome_social, relacao_representante, representante_nome, id) values ('1994-05-25', 'Santa Terezinha', '71583-881', 'Sao Bernardo', 'Rua Tiradentes', 'CASADO', null, 'Branca', 'Feminino', 0, 'Roberta', null, null, 2);

insert into `paciente_telefone` (paciente_id, telefone_telefone, telefone_tipo) values (2, '(11)95555-6666', 'CELULAR');

-- Insere Paciente2 de teste
-- SENHA = teste1
insert into `user` (confirmed, email, nome, `role`, senha, sobrenome, token) values (1, 'paciente1@dominio.com.br', 'Olavo', 'PACIENTE', '$2a$10$iWfH/HZjVVS5Gs8lTkjfKuY3PjloqpljPREAsgaUGn9S5ifjrLLDS', 'Favini', null);

insert into `paciente` (data_nascimento, endereco_bairro, endereco_cep, endereco_cidade, endereco_rua, estado_civil, estagiario_id, etnia_racial, genero, is_menor_idade, nome_social, relacao_representante, representante_nome, id) values ('1994-05-25', 'Santa Terezinha', '71583-881', 'Sao Bernardo', 'Rua Tiradentes', 'CASADO', null, 'Branca', 'Feminino', 0, 'Roberta', null, null, 3);

insert into `paciente_telefone` (paciente_id, telefone_telefone, telefone_tipo) values (3, '(11)95555-6665', 'CELULAR');


-- Insere estagiario de teste
-- SENHA = teste1
insert into `user` (confirmed, email, nome, `role`, senha, sobrenome, token) values (1, 'estagiario@uscsonline.com.br', 'Joaquim', 'ESTAGIARIO', '$2a$10$HP3kGM3/bVZWIWhpf55odOUjynksuQAi8F4cagDNYQGt9.DmMOwzW', 'Favini', null);

insert into `estagiario` (professor_responsavel_id, ra, semestre, turma, turno, id) values (null, '5699363', 10, '7an', 'NOTURNO', 4);

insert into `horario_trabalho` (dia_semana, estagiario_id, horario_fim, horario_inicio) values ('MONDAY', 4,'17:00:00' , '08:00:00');

-- Insere sala de teste

insert into sala (local, nome) values ('bloco A', 'Sala 10')