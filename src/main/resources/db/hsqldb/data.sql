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

INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (1, 'Holly Clinic', 50,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');
INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (2, 'George Clinic', 30,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');
INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (3, 'Snt Paul Clinic', 10,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');
INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (4, 'Care Clinic', 60,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');
INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (5, 'Pet Clinic', 50,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');
INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (6, 'Holy Pet Clinic', 40,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');
INSERT INTO clinic(id,name,capacity,email,telephone,location,emergency) VALUES (7, 'Petisuit Clinic', 30,'clinic1@gmail.com', '665544331', 'Sevilla', '955910011');



INSERT INTO vets(id,first_name,last_name,address,city,telephone,clinic_id,username) VALUES (1, 'James', 'Carter', '110 W. Liberty St.', 'Madison', '6085551023', 1,'vet1');
INSERT INTO vets(id,first_name,last_name,address,city,telephone,clinic_id,username) VALUES (2, 'Helen', 'Leary', '110 W. Liberty St.', 'Madison', '6085551023', 1,'vet2');
INSERT INTO vets(id,first_name,last_name,address,city,telephone,clinic_id,username) VALUES (3, 'Linda', 'Douglas', '110 W. Liberty St.', 'Madison', '6085551023', 1,'vet3');
INSERT INTO vets(id,first_name,last_name,address,city,telephone,clinic_id,username) VALUES (4, 'Rafael', 'Ortega', '110 W. Liberty St.', 'Madison', '6085551023',2, 'vet4');
INSERT INTO vets(id,first_name,last_name,address,city,telephone,clinic_id,username) VALUES (5, 'Henry', 'Stevens', '110 W. Liberty St.', 'Madison', '6085551023', 3,'vet5');
INSERT INTO vets(id,first_name,last_name,address,city,telephone,clinic_id,username) VALUES (6, 'Sharon', 'Jenkins', '110 W. Liberty St.', 'Madison', '6085551023',4, 'vet6');

INSERT INTO specialties(id,name) VALUES (1, 'radiology');
INSERT INTO specialties(id,name) VALUES (2, 'surgery');
INSERT INTO specialties(id,name) VALUES (3, 'dentistry');

INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (2, 1);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 3);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (4, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (5, 1);

INSERT INTO types(id,name) VALUES (1, 'cat');
INSERT INTO types(id,name) VALUES (2, 'dog');
INSERT INTO types(id,name) VALUES (3, 'lizard');
INSERT INTO types(id,name) VALUES (4, 'snake');
INSERT INTO types(id,name) VALUES (5, 'bird');
INSERT INTO types(id,name) VALUES (6, 'hamster');

INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

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

INSERT INTO hotels(id,name,capacity,count,location) VALUES (1, 'Calle Cadiz', 10, 0, 'Sevilla');
INSERT INTO hotels(id,name,capacity,count,location) VALUES (2, 'Calle Madrid', 8, 0, 'Lugo');
INSERT INTO hotels(id,name,capacity,count,location) VALUES (3, 'You Happy Pet', 80, 20, 'Barcelona');
INSERT INTO hotels(id,name,capacity,count,location) VALUES (4, 'Pet Hostel', 13, 4, 'Malaga');
INSERT INTO hotels(id,name,capacity,count,location) VALUES (5, 'El nunca jamás', 40, 10, 'Valencia');

INSERT INTO visits(id,visit_date,description,pet_id,hotel_id) VALUES (1, '2020-10-10', 'estaba mal', 1, 1);

INSERT INTO pet_history(id,date,details,summary,pet_id) VALUES (1, '2010-09-07', 'details', 'summary',  1);
INSERT INTO pet_history(id,date,details,summary,pet_id) VALUES (2, '2011-09-07',  'details', 'summary 2',1);
INSERT INTO pet_history(id,date,details,summary,pet_id) VALUES (3, '2012-09-07',  'details', 'summary 3', 1);

INSERT INTO cause(id,name,budget_archivied,budget_target,description,organisation) VALUES (1, 'Pon una mascota en tu vida', 0.0, 1000.0, 'Description Cause', 'Organisation Cause');
INSERT INTO cause(id,name,budget_archivied,budget_target,description,organisation) VALUES (2, 'Save your cats', 0.0, 2000.0, 'Description Cause', 'Organisation Cause');
INSERT INTO cause(id,name,budget_archivied,budget_target,description,organisation) VALUES (3, 'Compasión', 0.0, 2000.0, 'Description Cause', 'Organisation Cause');
INSERT INTO cause(id,name,budget_archivied,budget_target,description,organisation) VALUES (4, 'Imagine dogs', 0.0, 3000.0, 'Description Cause', 'Organisation Cause');
INSERT INTO cause(id,name,budget_archivied,budget_target,description,organisation) VALUES (5, 'No pain', 0.0, 2000.0, 'Description Cause', 'Organisation Cause');

INSERT INTO reservations(id,telephone,reservation_date,status,response_Client,owner_id,clinic_id) VALUES (1,'664455667','2020-06-24','pending','adios',1,1);

INSERT INTO providers(id,name,city,telephone,description, clinic_id) VALUES(1,'mercadona','Sevilla','664455669','comida para los animales', 1);
INSERT INTO providers(id,name,city,telephone,description, clinic_id) VALUES(2,'UtilesApp','Sevilla','112233445','Material clinico', 1);
INSERT INTO providers(id,name,city,telephone,description, clinic_id) VALUES(3,'mercadona','Madrid','778866552','comida para los animales', 2);


INSERT INTO donations(id,name,amount,cause_id) VALUES (1, 'Donacion Juan', 100.0, 1);
INSERT INTO donations(id,name,amount,cause_id) VALUES (2, 'Donacion Carlos', 200.0, 1);
INSERT INTO donations(id,name,amount,cause_id) VALUES (3, 'Donacion Maria', 400.0, 1);
INSERT INTO donations(id,name,amount,cause_id) VALUES (4, 'Donacion Amanda', 500.0, 2);
INSERT INTO donations(id,name,amount,cause_id) VALUES (5, 'Donacion Raul', 400.0, 3);
INSERT INTO donations(id,name,amount,cause_id) VALUES (6, 'Donacion Jose', 300.0, 4);
INSERT INTO donations(id,name,amount,cause_id) VALUES (7, 'Donacion Fran', 600.0, 5);






