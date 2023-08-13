/*
INSERT INTO TEST.PUBLIC."user" (password,role,username)
VALUES ("$2a$10$vgBpFiPA.DbLWFefgTZlsO.Wq3ZihZjSwya5PFS5TjEOKX3XigNqm","USER","admin");*/

INSERT INTO "user" ("password", "role", "username")
VALUES ('$2a$10$vgBpFiPA.DbLWFefgTZlsO.Wq3ZihZjSwya5PFS5TjEOKX3XigNqm','USER','admin'),
       ('$2a$10$vgBpFiPA.DbLWFefgTZlsO.Wq3ZihZjSwya5PFS5TjEOKX3XigNqm','USER','admin2');

INSERT INTO "circuit" ("code", "name", "user_id")
VALUES ( 1,'Pomidory',1 ),
       ( 2,'Ogórki',1 ),
       ( 3,'Konopie',1 ),
       ( 4,'Truskawki',1 ),
       ( 1,'',2 ),
       ( 2,'Pomidory',2 ),
       ( 3,'Buraki',2 );

INSERT INTO "circuit_schedule" ("deactivated","start_date", "end_date", "frequency_days",  "start_time", "watering_time", "circuit_id")
VALUES
    (false,'2023-03-01','2023-08-01',1,'00:00:00',900,1),
    (true,'2023-02-11','2023-07-31',2,'07:59:59',600,2),
    (true,'2023-03-11','2023-08-11',4,'08:00:00',10,3),
    (false,'2023-03-31','2023-08-11',5,'08:00:00',1,4),
    (false,'2023-03-01','2023-08-31',7,'08:00:00',1200,5),
    (true,'2023-03-31','2023-08-01',7,'08:00:00',60,6);


