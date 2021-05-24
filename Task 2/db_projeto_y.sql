create database db_projeto_y;

use db_projeto_y;

create table tb_usuario(
id bigint primary key auto_increment,
nome varchar(100) not null,
email varchar(100) not null,
senha varchar(100) not null,
usuarioEmpregador boolean,
descSobre varchar(255)
);



create table tb_postagem(
id bigint primary key auto_increment,
tipoDePostagem boolean,
dataPostagem date,
titulo varchar(100) not null,
texto text not null,
qtCurtidas bigint,
tema_id bigint,
usuario_id bigint,
foreign key (tema_id) references tb_tema (id),
foreign key (usuario_id) references tb_usuario (id)
);



create table tb_tema(
id bigint primary key auto_increment,
categoria varchar(100) not null,
cargo varchar(100) not null,
remoto varchar(100) not null
);