use cesep;

create table agendamento (
   id bigint not null auto_increment,
   fim_agendamento datetime(6),
   inicio_agendamento datetime(6),
   estagiario_id bigint,
   paciente_id bigint,
   sala_id bigint,
   primary key (id)
);

create table docente (
   ruscs varchar(255),
   id bigint not null,
   primary key (id)
);

create table docentes_servicos (
   servico_fk bigint not null,
   docente_fk bigint not null,
   primary key (servico_fk, docente_fk)
);

create table estagiario (
   ra varchar(255),
   semestre integer,
   turma varchar(255),
   turno varchar(255),
   id bigint not null,
   professor_responsavel_id bigint,
   primary key (id)
);

create table estagiarios_servicos (
   servico_fk bigint not null,
   estagiario_fk bigint not null,
   primary key (servico_fk, estagiario_fk)
);

create table funcionario (
   ruscs varchar(255),
   id bigint not null,
   primary key (id)
);

create table horario_trabalho (
   id bigint not null auto_increment,
   dia_semana integer,
   horario_fim time,
   horario_inicio time,
   estagiario_id bigint,
   primary key (id)
);

create table paciente (
   data_nascimento date,
   endereco_bairro varchar(255),
   endereco_cep varchar(255),
   endereco_cidade varchar(255),
   endereco_rua varchar(255),
   estado_civil varchar(255),
   etnia_racial varchar(255),
   genero varchar(255),
   is_menor_idade bit,
   nome_social varchar(255),
   relacao_representante varchar(255),
   representante_nome varchar(255),
   id bigint not null,
   estagiario_id bigint,
   primary key (id)
);

create table paciente_telefone (
   paciente_id bigint not null,
   telefone_telefone varchar(255),
   telefone_tipo varchar(255)
);

create table sala (
   id bigint not null auto_increment,
   local varchar(255),
   nome varchar(255),
   primary key (id)
);

create table servico (
   id bigint not null auto_increment,
   acronimo varchar(255),
   descricao varchar(255),
   nome varchar(255),
   primary key (id)
);

create table user (
   id bigint not null auto_increment,
   confirmed bit,
   email varchar(255),
   nome varchar(255),
   role varchar(255),
   senha varchar(255),
   sobrenome varchar(255),
   token varchar(255),
   primary key (id)
);

alter table docente 
    add constraint unique_ruscs unique (ruscs);
    
alter table funcionario 
   add constraint unique_ruscs unique (ruscs);

alter table servico 
   add constraint unique_acronimo unique (acronimo);

alter table user 
   add constraint unique_email unique (email);

alter table agendamento 
   add constraint fk_agendamento_estagiario_id
   foreign key (estagiario_id) 
   references estagiario (id);

alter table agendamento 
   add constraint fk_paciente_id
   foreign key (paciente_id) 
   references paciente (id);

alter table agendamento 
   add constraint fk_sala_id
   foreign key (sala_id) 
   references sala (id);

alter table docente 
   add constraint fk_docente_user_id
   foreign key (id) 
   references user (id);

alter table docentes_servicos 
   add constraint fk_docente_id 
   foreign key (docente_fk) 
   references docente (id);

alter table docentes_servicos 
   add constraint fk_docente_servico_id 
   foreign key (servico_fk) 
   references servico (id);

alter table estagiario 
   add constraint fk_processor_responsavel_id 
   foreign key (professor_responsavel_id) 
   references docente (id);

alter table estagiario 
   add constraint fk_estagiario_user_id 
   foreign key (id) 
   references user (id);

alter table estagiarios_servicos 
   add constraint fk_servicos_estagiario_id 
   foreign key (estagiario_fk) 
   references estagiario (id);

alter table estagiarios_servicos 
   add constraint fk_estagiario_servico_id 
   foreign key (servico_fk) 
   references servico (id);

alter table funcionario 
   add constraint fk_funcionario_user_id 
   foreign key (id) 
   references user (id);

alter table horario_trabalho 
   add constraint fk_horario_estagiario_id 
   foreign key (estagiario_id) 
   references estagiario (id);

alter table paciente 
   add constraint fk_paciente_estagiario_id 
   foreign key (estagiario_id) 
   references estagiario (id);

alter table paciente 
   add constraint fk_paciente_user_id 
   foreign key (id) 
   references user (id);

alter table paciente_telefone 
   add constraint fk_telefone_paciente_id 
   foreign key (paciente_id) 
   references paciente (id);


-- Insere o Docente admin
-- SENHA = 123456
insert into user (email, nome, role, senha, sobrenome, token, confirmed)
    values  ("admin@uscsonline.com.br", "Admin", "DOCENTE", "$2a$10$QrBWPzOZb1SG3Rx5DlM9H.0sQhc.J7.q/2jHabqIKE0.hv5gqY.Q2", "Agenda", null, 1);

insert into docente (ruscs, id) values ("0000000", 1);


-- Insere Paciente de teste
-- SENHA = teste1
insert 
into
   user
   (confirmed, email, nome, role, senha, sobrenome, token) 
values
   (1, "paciente@dominio.com.br", "Roberta", "PACIENTE", "$2a$10$iWfH/HZjVVS5Gs8lTkjfKuY3PjloqpljPREAsgaUGn9S5ifjrLLDS", "Bochetti", null);
   
insert 
into
   paciente
   (data_nascimento, endereco_bairro, endereco_cep, endereco_cidade, endereco_rua, estado_civil, estagiario_id, etnia_racial, genero, is_menor_idade, nome_social, relacao_representante, representante_nome, id) 
values
   ("1994-05-25", "Santa Terezinha", "71583-881", "Sao Bernardo", "Rua Tiradentes", "CASADO", null, "Branca", "Feminino", 0, "Roberta", null, null, 2);
   
insert 
into
   paciente_telefone
   (paciente_id, telefone_telefone, telefone_tipo) 
values
   (2, "(11)95555-6666", "CELULAR");


-- Insere estagiario de teste
-- SENHA = teste1
insert 
into
   user
   (confirmed, email, nome, role, senha, sobrenome, token) 
values
   (1, "estagiario@uscsonline.com.br", "Joaquim", "ESTAGIARIO", "$2a$10$HP3kGM3/bVZWIWhpf55odOUjynksuQAi8F4cagDNYQGt9.DmMOwzW", "Favini", null);
   
insert 
into
   estagiario
   (professor_responsavel_id, ra, semestre, turma, turno, id) 
values
   (null, "5699363", 10, "7an", "NOTURNO", 3);
   
insert 
into
   horario_trabalho
   (dia_semana, estagiario_id, horario_fim, horario_inicio) 
values
   (1, 3,"17:00:00" , "08:00:00");
