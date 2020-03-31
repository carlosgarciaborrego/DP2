-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet2','v3t',TRUE);
INSERT INTO authorities VALUES ('vet2','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet3','v3t',TRUE);
INSERT INTO authorities VALUES ('vet3','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet4','v3t',TRUE);
INSERT INTO authorities VALUES ('vet4','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet5','v3t',TRUE);
INSERT INTO authorities VALUES ('vet5','veterinarian');
INSERT INTO users(username,password,enabled) VALUES ('vet6','v3t',TRUE);
INSERT INTO authorities VALUES ('vet6','veterinarian');


INSERT INTO vets VALUES (1, 'James', 'Carter', '110 W. Liberty St.', 'Madison', '6085551023', 'vet1');
INSERT INTO vets VALUES (2, 'Helen', 'Leary', '110 W. Liberty St.', 'Madison', '6085551023', 'vet2');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas', '110 W. Liberty St.', 'Madison', '6085551023', 'vet3');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega', '110 W. Liberty St.', 'Madison', '6085551023', 'vet4');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens', '110 W. Liberty St.', 'Madison', '6085551023', 'vet5');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins', '110 W. Liberty St.', 'Madison', '6085551023', 'vet6');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (1, 'Leo', '2010-09-07', 1, 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (2, 'Basil', '2012-08-06', 6, 2, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (6, 'George', '2010-01-20', 4, 5, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (8, 'Max', '2012-09-04', 1, 6, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8,4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id, vet_id) VALUES (13, 'Sly', '2012-06-08', 1, 10, 1);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO hotels VALUES (1, '', 10, 3, 'Sevilla');

INSERT INTO pet_history VALUES (1, '2010-09-07', 'details', 'summary',  1);
INSERT INTO pet_history VALUES (2, '2011-09-07',  'details', 'summary 2',1);
INSERT INTO pet_history VALUES (3, '2012-09-07',  'details', 'summary 3', 1);

INSERT INTO cause VALUES (1, 'NombreCause', 0.0, 1000.0, 'Description Cause', 'Organisation Cause'); 
 
INSERT INTO donations VALUES (1, 'Donacion Prueba', 100.0, 1); 
