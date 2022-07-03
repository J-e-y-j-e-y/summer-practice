INSERT INTO clients VALUES(uuid_in('4552d148-fab0-11ec-b939-0242ac120002'), 'Jane', '-', 'Doe', '444444', 'none@mail.ru');
INSERT INTO clients VALUES(uuid_in('52fddab8-fab0-11ec-b939-0242ac120002'), 'John', '-', 'Smith', '124343', 'j@mail.ru');
INSERT INTO clients VALUES(uuid_in('207536da-fab1-11ec-b939-0242ac120002'), 'Jorge', '-', 'Markon', '354654', 'ne@mail.ru');
INSERT INTO clients VALUES(uuid_in('3630cd5e-fab1-11ec-b939-0242ac120002'), 'Julia', '-', 'Gresp', '4675674', 'jj@mail.ru');


INSERT INTO realties VALUES(uuid_in('47ba919a-fab1-11ec-b939-0242ac120002'), 'nei1', 'add1', 234.4, 3, 3000560, '34:4534:35435');
INSERT INTO realties VALUES(uuid_in('5bf0dbce-fab1-11ec-b939-0242ac120002'), 'nei2', 'add2', 464.78, 4, 6900500, '453:45:352:254654');
INSERT INTO realties VALUES(uuid_in('6a2a513e-fab1-11ec-b939-0242ac120002'), 'nei3', 'add3', 768.1, 8, 15560330, '23434:35:43345');
INSERT INTO realties VALUES(uuid_in('7f2a2028-fab1-11ec-b939-0242ac120002'), 'nei4', 'add4', 444.98, 3, 5450650, '7879:45:96753:56');


INSERT INTO requests VALUES(uuid_generate_v4(), 'BUY', uuid_in('4552d148-fab0-11ec-b939-0242ac120002'), uuid_in('47ba919a-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO requests VALUES(uuid_generate_v4(), 'SALE', uuid_in('4552d148-fab0-11ec-b939-0242ac120002'), uuid_in('6a2a513e-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO requests VALUES(uuid_generate_v4(), 'SALE', uuid_in('52fddab8-fab0-11ec-b939-0242ac120002'), uuid_in('7f2a2028-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO requests VALUES(uuid_generate_v4(), 'BUY', uuid_in('207536da-fab1-11ec-b939-0242ac120002'), uuid_in('47ba919a-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO requests VALUES(uuid_generate_v4(), 'BUY', uuid_in('3630cd5e-fab1-11ec-b939-0242ac120002'), uuid_in('7f2a2028-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO requests VALUES(uuid_generate_v4(), 'BUY', uuid_in('207536da-fab1-11ec-b939-0242ac120002'), uuid_in('6a2a513e-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO requests VALUES(uuid_generate_v4(), 'SALE', uuid_in('3630cd5e-fab1-11ec-b939-0242ac120002'), uuid_in('47ba919a-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');

INSERT INTO deals VALUES(uuid_generate_v4(), uuid_in('4552d148-fab0-11ec-b939-0242ac120002'), uuid_in('207536da-fab1-11ec-b939-0242ac120002'), uuid_in('6a2a513e-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO deals VALUES(uuid_generate_v4(), uuid_in('52fddab8-fab0-11ec-b939-0242ac120002'), uuid_in('3630cd5e-fab1-11ec-b939-0242ac120002'), uuid_in('7f2a2028-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');
INSERT INTO deals VALUES(uuid_generate_v4(), uuid_in('3630cd5e-fab1-11ec-b939-0242ac120002'), uuid_in('4552d148-fab0-11ec-b939-0242ac120002'), uuid_in('47ba919a-fab1-11ec-b939-0242ac120002'), '2012-10-11 12:13:14.123');