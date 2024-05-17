create table consultas_canceladas(

    id bigint not null auto_increment,
    consulta_id bigint not null,
    motivo varchar(100) not null,

    primary key(id),
    constraint fk_consultas_canceladas_consultas_id foreign key(consulta_id) references consultas(id)
);