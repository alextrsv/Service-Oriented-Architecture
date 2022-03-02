INSERT INTO location(x_coord, y_coord, name) VALUES
(1,1,'f1'),
(1,2,'f2'),
(1,3,'f3'),
(2,1,'s1');

insert into location(x_coord, y_coord, name) VALUES
(0,0, 'Sarov'),
(-1,1, 'Moscow'),
(-2,1.5, 'St.Petersburg'),
(4,-1, 'Novosibirsk');

insert into address(zip_code, location_id) VALUES
('11111-1111', 5),
('11111-2222', 5),
('22222-1111', 6),
('22222-1111', 6),
('33333-1111', 7),
('33333-1111', 7),
('33333-1111', 7),
('44444-1111', 8);

insert into organization(annual_turnover, employees_count, address_id) VALUES
(1000, 20, 1),
(100, 6, 2),
--Moscow
(100000, 90, 3),
(600000, 120, 4),
--St.Petersburg
(4500000, 150, 5),
(34500, 18, 6),
(1300105, 36, 6),
--Novosibirsk
(861001, 104, 8);

