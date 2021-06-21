insert into categoria() values (null, "Simboliza um evento em formato de palestra", "Palestra");
insert into categoria() values (null, "Simboliza um evento em formato de curso", "Curso");

insert into pessoa() values (null, "00000000000", "produtor@eventvs.com" ,"Produtor", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000001", "produtor2@eventvs.com" ,"Produtor 2", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000003", "produtor3@eventvs.com" ,"Produtor 3", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into produtor() values(null, 0, 1);
insert into produtor() values(null, 0, 2);
insert into produtor() values(null, 2, 3);

insert into pessoa() values (null, "00000000004", "participante@eventvs.com" ,"Participante", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into participante() values(null, 4);


insert into endereco() values(null, '49100000', 'Rosa Elze', 'São Cristóvão', 'Sergipe', 'Av. Marechal Rondom', 'S/N');

insert into evento() values(null, '2021/06/30', '2021/06/19', 'Evento voltado para o curso de engenharia de software 2', 'Evento Eng2', 2, 2, 1, 1);
insert into evento() values(null, '2021/06/30', '2021/06/19', 'Palestra do Erick Wendel abordando diversos tópicos avançados em JavaScript', 'JS Like a Boss', 5, 1, 1, 2);
