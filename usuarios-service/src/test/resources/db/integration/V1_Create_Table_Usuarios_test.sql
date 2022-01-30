CREATE TABLE usuarios (
id bigint IDENTITY primary key,
  nome varchar(40) not null,
  sobre_nome varchar(80) not null,
  email varchar(80) not null,
  login varchar(20) not null,
  telefone varchar(20) not null,
  senha varchar(120) not null,
  situacao int not null,
  codigo_ativar char(4) null,  
  data_limite_ativar datetime null
);