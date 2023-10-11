--CREATE TABLES

CREATE TABLE IF NOT EXISTS `branch_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `owner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birth_date` date NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `number_of_house` int(11) NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `spz` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spz` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spz` varchar(8) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `emission_standard` double NOT NULL,
  `engine_power` double NOT NULL,
  `is_in_deposit` bit(1) NOT NULL,
  `is_stolen` bit(1) NOT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `torque` double NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `vin` varchar(255) DEFAULT NULL,
  `year_of_creation` date NOT NULL,
  `branch_office_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3oywalq8qxky4ras7do24ba9p` (`branch_office_id`),
  CONSTRAINT `FK3oywalq8qxky4ras7do24ba9p` FOREIGN KEY (`branch_office_id`) REFERENCES `branch_office` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `car_owner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_of_sign_up` date DEFAULT NULL,
  `start_of_sign_up` date NOT NULL,
  `car_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8j4i2l1h82bmtd7qlcifj6sca` (`car_id`),
  KEY `FKg4oheym6xvth3owqj7qucf7li` (`owner_id`),
  CONSTRAINT `FK8j4i2l1h82bmtd7qlcifj6sca` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `FKg4oheym6xvth3owqj7qucf7li` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS  `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `job_position` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `branch_office_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsiaffnsal76iywn4lhkluedaw` (`branch_office_id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKsiaffnsal76iywn4lhkluedaw` FOREIGN KEY (`branch_office_id`) REFERENCES `branch_office` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- INSERT DATA

INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (1,'Kolín','Kolín','Středočeský');
INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (2,'Pardubicee','Pardubice','Pardubický');
INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (3,'Kutná Hora','Kutná Hora','Středočeský');
INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (4,'Suchdol','Kutná Hora','Středočeský');
INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (5,'Chrudim','Chrudim','Pardubický');
INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (6,'Svitavy','Svitavy','Pardubický');
INSERT INTO `branch_office` (`id`,`city`,`district`,`region`) VALUES (7,'Ústí nad Orlicí','Ústí nad Orlicí','Pardubický');

INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (1,'1994-05-01','Kolín','petr@email.cz','Petr','Dolejš',1,'Jaselská',28002);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (2,'1999-06-02','Kolín','jiri@email.cz','Jiří','Dolejš',2,'Kutnohorská',28502);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (3,'1998-09-05','Kutná Hora','tomas@email.cz','Tomáš','Vdolek',3,'Česká',28401);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (4,'1997-09-08','Kutná Hora','jan@email.cz','Jan','Kostka',4,'Hornická',28401);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (5,'1983-10-09','Praha','nikol@email.com','Nikola','Velká',123,'Kutnuhorská',54002);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (6,'1995-01-03','Ratboř','smala@email.cz','Simona','Malá',124,'U hřiště',28401);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (7,'1998-01-17','Kolín','lprum@email.com','Luboš','Průměrný',245,'U Tesca',28400);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (8,'1993-02-24','Suchdol','natka@email.com','Natálie','Levná',123,'Suchdol',28502);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (9,'1995-02-15','Kolín','pkolac@emial.com','Petr','Koláček',234,'U hříště',28401);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (10,'1997-02-20','Miskovice','jmicek@email.com','Jan','Míček',94,'U rybníka',28301);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (11,'1993-02-24','Suchdol','tmarny@email.com','Tomáš','Marný',171,'Za hřištěm',28502);
INSERT INTO `owner` (`id`,`birth_date`,`city`,`email`,`first_name`,`last_name`,`number_of_house`,`street`,`zip_code`) VALUES (14,'1999-04-22','Suchdol','trnkys@email.com','Karel','Trnka',49,'U hříště',28502);

INSERT INTO `role` (`id`,`description`,`name`) VALUES (1,'Krajský pracovník CRV','ROLE_Kraj');
INSERT INTO `role` (`id`,`description`,`name`) VALUES (2,'Okresní pracovník CRV','ROLE_Okres');
INSERT INTO `role` (`id`,`description`,`name`) VALUES (3,'Administrátor','ROLE_Admin');

INSERT INTO `user` (`id`,`email`,`job_position`,`password`,`username`,`branch_office_id`,`role_id`) VALUES (1,'admin@admin.cz','Admin','$2a$10$l5HanlaURJAv2MrOZcw1veF8vnf7/RZabgv1evYWSBRf4OFGewSGi','admin',1,3);
INSERT INTO `user` (`id`,`email`,`job_position`,`password`,`username`,`branch_office_id`,`role_id`) VALUES (2,'okres@email.co','Úředník pobočk','$2a$10$oJFnhlasUU1KkRb34Zm0w.06TZGrm6W9msjPK66fbeWGpPlJN6AIe','okresKH',3,2);
INSERT INTO `user` (`id`,`email`,`job_position`,`password`,`username`,`branch_office_id`,`role_id`) VALUES (3,'krajS@email.com','Manažer kraj','$2a$10$oJFnhlasUU1KkRb34Zm0w.06TZGrm6W9msjPK66fbeWGpPlJN6AIe','krajPce',6,1);
INSERT INTO `user` (`id`,`email`,`job_position`,`password`,`username`,`branch_office_id`,`role_id`) VALUES (4,'krajKo@abc.com','Manažer kraje','$2a$10$oJFnhlasUU1KkRb34Zm0w.06TZGrm6W9msjPK66fbeWGpPlJN6AIe','krajKolin',1,1);
INSERT INTO `user` (`id`,`email`,`job_position`,`password`,`username`,`branch_office_id`,`role_id`) VALUES (5,'okresKO@mail.com','Okresní pracovník','$2a$10$3SFsOVQNQl7bLLRNeYBglOTXi/tjIC/GSmog0LFkomtXZZDnJCdR6','okresKO',1,2);

INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (1,'5M0 0247','Puce',3,167,0,0,'Lincoln',89,'Town Car','3C3CFFCR3FT219575','1991-04-10',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (2,'0U3 9027','Goldenrod',4,198,0,0,'Buick',98,'Electra','WDDEJ7KB8DA284736','2020-02-05',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (3,'9B2 9066','Teal',4,196,1,0,'Infiniti',69,'QX56','3VW4A7AT9DM702947','1990-11-01',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (4,'7F9 7853','Crimson',3,148,0,1,'Aston Martin',53,'Vantage','3C63D3DL1CG880205','1999-09-04',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (5,'7T4 6940','Turquoise',4,199,1,1,'Mercedes-Benz',52,'CLS-Class','2G4WD582561757146','2016-04-20',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (6,'7F9 7853','Purple',4,171,1,1,'Pontiac',89,'Bonneville','1FADP5BU5DL915270','1997-06-29',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (7,NULL,'Khaki',3,125,1,0,'Chrysler',77,'Concorde','19UUA65585A341557','2019-12-13',NULL);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (8,'','Fuscia',4,139,0,1,'Buick',93,'Century','4T1BB3EK6BU443792','1999-05-11',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (9,'4R2 7885','Puce',4,106,1,0,'Mazda',82,'RX-7','4T1BF1FK9EU438548','2003-05-12',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (10,'','Purple',4,156,0,0,'Hyundai',65,'Veracruz','1GYS4KEF4ER245659','2018-08-05',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (11,'','Orange',3,186,1,1,'Chrysler',54,'300','WUARL48H89K250428','2017-02-01',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (12,'','Khaki',4,190,0,1,'Suzuki',74,'Swift','KMHGH4JH8DU785218','2001-10-06',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (13,'','Teal',4,198,1,1,'Chevrolet',74,'Camaro','WAUDH74F86N054883','2019-03-29',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (14,'','Red',4,168,0,1,'Saab',74,'9-5','WBALM73559E800114','2003-08-22',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (15,'','Khaki',4,190,1,0,'Nissan',80,'Altima','WBAUT9C50BA808914','2019-09-24',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (16,'','Puce',4,199,0,0,'Volkswagen',87,'Cabriolet','1GT3C0BG2AF111520','2016-02-26',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (17,'','Teal',3,140,1,1,'Chrysler',98,'Town & Country','JTDZN3EU2FJ499974','2007-09-22',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (18,'','Pink',3,109,1,1,'Lamborghini',89,'Diablo','SALWR2TF6FA169303','2013-10-24',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (19,'','Turquoise',4,166,1,0,'Mercedes-Benz',86,'R-Class','WBAEJ13451A752180','1992-12-18',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (20,'','Khaki',3,180,1,0,'Mitsubishi',72,'Truck','WAUEG74F86N594774','2018-09-11',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (21,'','Red',3,200,0,0,'BMW',90,'5 Series','WBAEJ13491A338884','2015-05-01',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (22,'','Goldenrod',3,185,1,0,'GMC',82,'Sierra 1500','1G6KD57Y29U079265','1999-11-05',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (23,'','Green',4,191,0,1,'Chevrolet',71,'Classic','WBANB53547C137307','2016-06-16',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (24,'','Fuscia',4,124,1,1,'Bentley',61,'Arnage','1B3CB3HAXAD730449','1997-07-17',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (25,'','Aquamarine',3,130,0,0,'Saturn',59,'Relay','WBAAV33481F197954','2016-03-24',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (26,'','Turquoise',3,200,0,0,'Volkswagen',66,'Scirocco','JTHBL1EFXB5206241','2001-11-03',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (27,'','Puce',3,92,0,1,'GMC',80,'Savana 3500','WBAAV33431F319796','2005-02-19',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (28,'','Red',4,97,1,1,'Cadillac',67,'CTS-V','WBAPH575X9N478221','1993-07-10',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (29,'','Pink',3,82,1,0,'Chrysler',84,'Aspen','2G4WF521X41663531','1994-09-25',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (30,'','Violet',4,125,0,0,'Austin',60,'Mini Cooper','WBAVA33578P324638','1998-06-30',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (31,'','Teal',4,126,0,1,'Saab',73,'9-7X','WBAKB0C51AC090802','2009-11-25',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (32,'','Puce',3,84,0,1,'Lamborghini',82,'Countach','1C3BC7EG7BN782287','2004-06-23',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (33,'','Green',4,117,1,1,'Honda',66,'Odyssey','1C3CDZBGXEN191151','1994-07-06',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (34,'','Turquoise',4,161,0,1,'Audi',92,'4000s','1GTN2TEH9FZ182351','2003-05-18',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (35,'','Red',4,87,1,0,'Chrysler',71,'Concorde','19UUA9F75CA172001','1992-04-20',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (36,'','Orange',3,172,0,0,'Toyota',51,'4Runner','KNDPB3AC6E7890831','2016-04-23',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (37,'','Yellow',3,81,1,1,'Dodge',93,'Caravan','KNDJT2A22D7569341','2019-09-30',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (38,'','Orange',3,141,1,0,'Toyota',53,'Tundra','JN1AZ4EH4AM268093','2012-09-16',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (39,'','Orange',4,133,0,0,'Audi',66,'RS4','WAURV78T68A454014','2003-08-17',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (40,'','Violet',3,118,1,0,'Mazda',56,'RX-7','4USBT53525L775338','2019-02-19',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (41,'','Yellow',4,129,1,0,'Nissan',91,'Versa','WBAYF4C56FG524346','2005-10-25',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (42,'','Indigo',3,160,0,1,'Bentley',78,'Continental Flying Spur','KNAFK4A68F5173644','1999-02-21',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (43,'','Mauv',4,102,0,0,'Hyundai',60,'Azera','JM1GJ1T50F1161122','2011-06-09',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (44,'','Pink',3,146,0,1,'Pontiac',63,'Bonneville','5GAEV13D99J688858','1997-09-15',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (45,'','Goldenrod',3,116,1,0,'Buick',53,'Skylark','4T1BK3DB5BU133716','2015-07-23',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (46,'','Maroon',4,81,0,1,'Honda',90,'Fit','3CZRE3H38BG265122','2007-03-11',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (47,'','Crimson',4,185,1,0,'Chrysler',63,'Pacifica','1C3CCBBB3EN602372','1997-10-17',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (48,'','Aquamarine',3,91,0,1,'Isuzu',57,'Amigo','KNAFU6A20D5722379','2002-12-27',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (49,'','Green',4,152,1,0,'Porsche',70,'911','WDDHF2EB3DA260664','1995-06-25',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (50,'','Mauv',3,113,1,1,'Kia',58,'Optima','1GKS2FFJ5BR281284','2009-06-15',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (51,'','Aquamarine',3,150,0,1,'BMW',77,'X5','1G6DE8E58D0310041','2018-03-27',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (52,'','Fuscia',3,129,0,0,'BMW',68,'600','WAUWGBFC6EN981607','2003-08-24',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (53,'','Indigo',3,195,0,0,'Lotus',86,'Evora','5GADV33WX7D126083','1996-04-11',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (54,'','Pink',4,93,0,0,'Kia',100,'Sportage','1N6BF0KL6FN326862','1992-04-18',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (55,'','Pink',4,89,1,0,'Porsche',55,'Boxster','KNADM4A37C6519003','2006-12-20',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (56,'','Puce',4,194,0,0,'Hyundai',68,'Elantra','1FTEX1CM7BF059159','2010-07-11',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (57,'','Purple',4,125,0,1,'Chevrolet',90,'Monte Carlo','1G6KF54965U400564','2018-06-25',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (58,'','Indigo',4,172,0,0,'Toyota',56,'Highlander','SCFAB22383K012697','2014-09-27',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (59,'','Indigo',3,164,0,1,'Buick',60,'LeSabre','5N1AZ2MGXFN405903','2016-06-03',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (60,'','Green',3,120,0,0,'Suzuki',72,'Sidekick','WAUUL68E15A805810','1992-03-23',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (61,'','Puce',4,104,1,1,'Chevrolet',55,'Avalanche 1500','4T1BD1EB3DU223589','1997-01-20',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (62,'','Fuscia',3,154,0,0,'MINI',69,'Cooper','5XYZG3AB1BG994076','2017-04-17',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (63,'','Mauv',3,165,0,0,'Mazda',94,'626','WP1AA2A25CL057693','2018-03-12',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (64,'','Mauv',3,137,1,1,'Porsche',50,'944','1N6AD0CW7EN328211','2018-12-31',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (65,'','Khaki',4,95,1,0,'Mitsubishi',53,'Expo LRV','WAUDFBFL5AN073752','1994-07-06',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (66,'','Orange',3,168,1,1,'Rolls-Royce',96,'Ghost','3VW1K7AJ4FM786788','2015-03-19',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (67,'','Maroon',4,103,0,0,'Audi',98,'S4','1FTEW1CM1CK531481','2015-06-23',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (68,'','Yellow',3,153,0,1,'Hummer',91,'H2','1GYS4GEF8CR030019','2018-08-02',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (69,'','Yellow',3,124,0,1,'Mercury',59,'Grand Marquis','WAUPL58E35A593547','2019-01-27',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (70,'','Red',4,150,1,0,'Mazda',86,'B2600','2G61P5S38D9061647','1999-03-16',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (71,'','Fuscia',3,122,0,1,'Nissan',93,'Sentra','1VWAS7A37FC379003','2019-01-06',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (72,'','Pink',3,163,0,0,'Scion',94,'tC','5TDBW5G18ES022951','1991-05-06',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (73,'','Khaki',4,169,0,1,'Chevrolet',61,'Tahoe','19XFA1F30AE532503','1997-08-20',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (74,'','Pink',3,170,0,1,'Mazda',96,'Mazdaspeed 3','WBABW334X5P399454','2010-01-28',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (75,'','Khaki',4,106,0,0,'Chevrolet',75,'S10','WA1MKAFP0AA434298','1996-10-29',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (76,'','Aquamarine',4,109,0,1,'Jeep',79,'Grand Cherokee','1N6AD0CUXCC522772','2004-05-07',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (77,'','Teal',4,107,1,0,'Ford',86,'Mustang','3N1BC1CP9CK029723','1997-05-14',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (78,'','Mauv',4,125,1,0,'Mitsubishi',94,'Mighty Max','WP0AB2A84CS004222','2006-04-05',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (79,'','Crimson',4,137,0,1,'Audi',55,'TT','WAUEFBFLXCN478058','2005-07-01',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (80,'','Teal',4,95,0,0,'Chevrolet',98,'Monte Carlo','19UUA8F25BA946289','2000-05-02',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (81,'','Green',4,107,1,1,'Chevrolet',51,'Lumina','JHMZF1C66CS962421','1995-01-31',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (82,'','Khaki',4,173,0,1,'Hyundai',79,'Veracruz','1FTEW1CF9FK891006','2014-03-04',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (83,'','Fuscia',4,179,0,0,'Ford',68,'Econoline E250','JH4CU2E69BC918601','2016-02-26',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (84,'','Crimson',4,98,1,1,'Pontiac',78,'Vibe','WAUHGAFC4CN835304','2011-04-07',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (85,'','Violet',4,160,1,1,'Nissan',75,'Altima','WAUVT54BX3N198452','1997-10-14',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (86,'','Turquoise',3,89,1,1,'Chevrolet',82,'Express 2500','WDDGF4HB0DA437643','1996-10-08',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (87,'','Aquamarine',4,186,1,1,'Mercedes-Benz',53,'C-Class','JH4KA96612C215909','2018-03-07',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (88,'','Maroon',3,143,0,1,'Honda',62,'Civic','WBA3T3C56EP431385','1993-04-07',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (89,'','Green',4,87,0,1,'Buick',94,'Century','WA1C8AFP6DA094041','2004-06-20',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (90,'','Purple',3,148,1,1,'Chevrolet',93,'Bel Air','JTJBM7FX9E5455625','1998-03-14',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (91,'','Indigo',4,183,0,0,'Jaguar',77,'S-Type','19UUA9F5XEA086653','2005-07-12',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (92,'','Fuscia',3,165,0,1,'Toyota',96,'Sienna','1FTEW1E88AF840709','2006-09-28',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (93,'','Aquamarine',4,181,1,1,'Volkswagen',77,'Passat','2T1BURHE9FC802062','2009-12-12',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (94,'','Red',3,88,0,1,'GMC',93,'Savana 1500','1FTEW1CW4AK429364','2016-05-30',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (95,'','Mauv',3,114,0,0,'Toyota',54,'T100 Xtra','1FADP5AUXFL103872','1993-01-10',5);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (96,'','Aquamarine',3,155,1,0,'Dodge',76,'Ram 2500','JN8AF5MR5FT325585','2004-12-09',6);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (97,'','Yellow',3,194,1,0,'Mercedes-Benz',53,'S-Class','19UUA66238A526737','2000-10-21',1);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (98,'','Fuscia',4,178,0,1,'Ford',75,'LTD Crown Victoria','1FTEX1CM3BK284301','2006-06-10',2);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (99,'','Goldenrod',4,123,0,0,'Hummer',56,'H3','WAUDV94F29N035187','1994-04-25',3);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (100,'','Blue',4,127,0,0,'Jaguar',55,'XK Series','SCBBB7ZH3DC215674','2016-07-08',4);
INSERT INTO `car` (`id`,`spz`,`color`,`emission_standard`,`engine_power`,`is_in_deposit`,`is_stolen`,`manufacturer`,`torque`,`type`,`vin`,`year_of_creation`,`branch_office_id`) VALUES (103,NULL,'Black',3,50,0,0,'Škoda',0,'Fabia II','12345678901023456','2021-02-09',NULL);

INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (21,'2019-06-28','2018-09-28',1,1);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (22,'2021-09-28','2019-06-28',1,2);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (23,'2023-08-04','2021-09-28',1,3);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (26,'2021-10-09','2018-03-09',3,2);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (28,'2021-04-09','2017-04-09',6,4);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (29,NULL,'2021-10-09',9,3);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (35,NULL,'2023-08-04',1,4);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (36,NULL,'2023-10-10',2,1);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (37,NULL,'2021-10-09',3,5);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (38,NULL,'2023-10-10',4,6);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (39,NULL,'2023-10-10',5,7);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (40,NULL,'2021-04-09',6,8);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (41,'2010-05-05','2008-01-02',103,7);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (42,'2015-01-06','2010-05-05',103,7);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (43,'2023-10-10','2015-01-06',103,1);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (44,'2019-03-10','2018-09-10',7,9);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (45,'2020-03-03','2019-03-10',7,10);
INSERT INTO `car_owner` (`id`,`end_of_sign_up`,`start_of_sign_up`,`car_id`,`owner_id`) VALUES (46,'2023-10-10','2020-03-03',7,11);

INSERT INTO `spz` (`id`,`spz`) VALUES (3,'7A8 2618');
INSERT INTO `spz` (`id`,`spz`) VALUES (4,'7A8 2617');
INSERT INTO `spz` (`id`,`spz`) VALUES (14,'5M0 248');
INSERT INTO `spz` (`id`,`spz`) VALUES (15,'7A8 2616');
INSERT INTO `spz` (`id`,`spz`) VALUES (17,'9B2 9066');
INSERT INTO `spz` (`id`,`spz`) VALUES (22,'0U3 9030');
INSERT INTO `spz` (`id`,`spz`) VALUES (24,'0U3 9029');

