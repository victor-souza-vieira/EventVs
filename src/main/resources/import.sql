insert into categoria() values (null, "Simboliza um evento em formato de palestra", "Palestra");
insert into categoria() values (null, "Simboliza um evento em formato de curso", "Curso");

insert into pessoa() values (null, "00000000001", "produtor@eventvs.com" ,"Produtor", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000002", "produtor2@eventvs.com" ,"Produtor 2", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000003", "produtor3@eventvs.com" ,"Produtor 3", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into produtor() values(null, 0, 1);
insert into produtor() values(null, 0, 2);
insert into produtor() values(null, 2, 3);

insert into pessoa() values (null, "00000000004", "participante1@eventvs.com" ,"Participante1", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000005", "participante2@eventvs.com" ,"Participante2", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000006", "participante3@eventvs.com" ,"Participante3", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000007", "participante4@eventvs.com" ,"Participante4", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into participante() values(null, 4);
insert into participante() values(null, 5);
insert into participante() values(null, 6);
insert into participante() values(null, 7);

insert into pessoa() values (null, "00000000008", "admin@eventvs.com" ,"Administrador O Brabo", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into administrador() values(null, 8);

insert into endereco() values(null, '49100000', 'Rosa Elze', 'São Cristóvão', 'Sergipe', 'Av. Marechal Rondom', 'S/N');
insert into endereco() values(null, '49100000', 'Eduardo Gomes', 'São Cristóvão', 'Sergipe', 'Rua dos Bobos', '0');

insert into evento() values(null, '2021/06/30', '2021/06/19', 'Evento voltado para o curso de engenharia de software 2', 'Evento Eng2', 2, 2, 2, 1);
insert into evento() values(null, '2021/06/30', '2021/06/19', 'Palestra do Erick Wendel abordando diversos tópicos avançados em JavaScript', 'JS Like a Boss', 5, 1, 1, 2);

insert into inscricao() values(null, '2021/06/15', 0, 1, 1);
insert into inscricao() values(null, '2021/06/15', 0, 1, 2);