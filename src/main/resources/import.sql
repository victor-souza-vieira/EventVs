insert into categoria() values (null, "Simboliza um evento em formato de palestra", "Palestra");
insert into categoria() values (null, "Simboliza um evento em formato de curso", "Curso");

insert into pessoa() values (null, "00000000000", "produtor@eventvs.com" ,"Produtor", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");
insert into pessoa() values (null, "00000000001", "participante@eventvs.com" ,"Participante", "$2y$12$v4nbKBU7V5Vz.0yYymbfm.TUvmgXNtMPIdpLtdVRsksPZlCAX/K4G");

insert into produtor() values(null, 0, 1);
insert into participante() values(null, 2);

insert into endereco() values(null, '49100000', 'Rosa Elze', 'São Cristóvão', 'Sergipe', 'Av. Marechal Rondom', 'S/N');

insert into evento() values(null, '2021/06/30', '2021/06/19', 'Evento voltado para o curso de engenharia de software 2', 'Evento Eng2', 2, 2, 1, 1);
