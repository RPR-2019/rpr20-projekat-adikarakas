BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "fixtures" (
	"home_team"	TEXT,
	"away_team"	TEXT,
	FOREIGN KEY("home_team") REFERENCES "clubs"("name"),
	FOREIGN KEY("away_team") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "clubs" (
	"name"	TEXT,
	"nickname"	TEXT,
	"stadium"	TEXT,
	"mascot"	TEXT,
	"manager"	TEXT,
	"captain"	INTEGER DEFAULT null,
	"color" TEXT,
	PRIMARY KEY("name"),
	FOREIGN KEY("captain") REFERENCES "players"("id")
);
CREATE TABLE IF NOT EXISTS "players" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"date"	TEXT,
	"nationality"	TEXT,
	"club"	TEXT,
	"position"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("club") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "results" (
	"id"	INTEGER,
	"home_team"	TEXT,
	"away_team"	TEXT,
	"home_team_goals"	INTEGER,
	"away_team_goals"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("home_team") REFERENCES "clubs"("name"),
	FOREIGN KEY("away_team") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "goals" (
	"scorer"	INTEGER,
	"assistent"	INTEGER,
	"minute"	INTEGER,
	"goal_type"	TEXT,
	"goal_situation"	TEXT,
	"goal_distance"	TEXT,
	"club"	TEXT,
	"result"	INTEGER,
	FOREIGN KEY("assistent") REFERENCES "players"("id"),
	FOREIGN KEY("scorer") REFERENCES "players"("id"),
	FOREIGN KEY("club") REFERENCES "clubs"("name"),
	FOREIGN KEY("result") REFERENCES "results"("id")
);
CREATE TABLE IF NOT EXISTS "stats" (
	"id"	INTEGER,
	"appearances"	INTEGER,
	"clean_sheets"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "clubs" VALUES ('Chelsea','The Blues','Stamford Bridge','','Thomas Tuchel',7,'0x0000ffff');
INSERT INTO "clubs" VALUES ('Liverpool','Reds','Anfield','','Jurgen Klopp',39,'0xff0000ff');
INSERT INTO "clubs" VALUES ('Arsenal','Gunners','Emirates','Gunnersaurus','Mikel Arteta',28, '0xff0000ff');
INSERT INTO "clubs" VALUES ('Everton','The Toffies','Goodison Park','','Carlo Ancelotti',48, '0x0000ffff');
INSERT INTO "players" VALUES (1,'Edouard','Mendy','1992-03-01','Senegal','Chelsea','Goalkeeper');
INSERT INTO "players" VALUES (2,'Kepa','Arrizabalaga','1994-10-03','Spain','Chelsea','Goalkeeper');
INSERT INTO "players" VALUES (3,'Reece','James','1999-12-08','England','Chelsea','Defender');
INSERT INTO "players" VALUES (4,'Thiago','Silva','1984-09-22','Brazil','Chelsea','Defender');
INSERT INTO "players" VALUES (5,'Kurt','Zouma','1994-10-27','France','Chelsea','Defender');
INSERT INTO "players" VALUES (6,'Ben','Chilwell','1996-12-21','England','Chelsea','Defender');
INSERT INTO "players" VALUES (7,'Cesar','Azpilicueta','1989-08-28','Spain','Chelsea','Defender');
INSERT INTO "players" VALUES (8,'N''Golo','Kante','1991-03-29','France','Chelsea','Midfielder');
INSERT INTO "players" VALUES (9,'Mateo','Kovačić','1994-05-06','Croatia','Chelsea','Midfielder');
INSERT INTO "players" VALUES (10,'Mason','Mount','1999-01-10','England','Chelsea','Midfielder');
INSERT INTO "players" VALUES (11,'Kai','Havertz','1999-06-11','Germany','Chelsea','Midfielder');
INSERT INTO "players" VALUES (12,'Christian','Pulišić','1998-09-18','USA','Chelsea','Attacker');
INSERT INTO "players" VALUES (13,'Timo','Werner','1996-03-06','Germany','Chelsea','Attacker');
INSERT INTO "players" VALUES (14,'Hakim','Ziyech','1993-03-19','Morocco','Chelsea','Attacker');
INSERT INTO "players" VALUES (15,'Olivier','Giroud','1986-09-30','France','Chelsea','Attacker');
INSERT INTO "players" VALUES (16,'Bernd','Leno','1992-03-04','Germany','Arsenal','Goalkeeper');
INSERT INTO "players" VALUES (17,'Mat','Ryan','1992-04-08','Australia','Arsenal','Goalkeeper');
INSERT INTO "players" VALUES (18,'Hector','Bellerin','1995-03-19','Spain','Arsenal','Defender');
INSERT INTO "players" VALUES (19,'Pablo','Mari','1993-08-31','Spain','Arsenal','Defender');
INSERT INTO "players" VALUES (20,'David','Luiz','1987-04-22','Brazil','Arsenal','Defender');
INSERT INTO "players" VALUES (21,'Kieran','Tierney','1997-06-05','Scotland','Arsenal','Defender');
INSERT INTO "players" VALUES (22,'Calum','Chambers','1995-01-20','England','Arsenal','Defender');
INSERT INTO "players" VALUES (23,'Granit','Xhaka','1992-09-27','Switzerland','Arsenal','Midfielder');
INSERT INTO "players" VALUES (24,'Thomas','Partey','1993-06-13','Ghana','Arsenal','Midfielder');
INSERT INTO "players" VALUES (25,'Emile','Smith-Rowe','2000-07-28','England','Arsenal','Midfielder');
INSERT INTO "players" VALUES (26,'Martin','Odegaard','1998-12-17','Norway','Arsenal','Midfielder');
INSERT INTO "players" VALUES (27,'Nicolas','Pepe','1995-05-29','Ivory Coast','Arsenal','Attacker');
INSERT INTO "players" VALUES (28,'Pierre-Emerick','Aubameyang','1989-06-18','Gabon','Arsenal','Attacker');
INSERT INTO "players" VALUES (29,'Bukayo','Saka','2001-09-05','England','Arsenal','Attacker');
INSERT INTO "players" VALUES (30,'Alexandre','Lacazette','1991-05-28','France','Arsenal','Attacker');
INSERT INTO "players" VALUES (31,'Alisson','Becker','1992-10-02','Brazil','Liverpool','Goalkeeper');
INSERT INTO "players" VALUES (32,'Caoimhin','Kelleher','1998-11-23','Ireland','Liverpool','Goalkeeper');
INSERT INTO "players" VALUES (33,'Trent','Alexander-Arnold','1998-10-07','England','Liverpool','Defender');
INSERT INTO "players" VALUES (34,'Joel','Matip','1991-08-08','Cameroon','Liverpool','Defender');
INSERT INTO "players" VALUES (35,'Virgil','Van Dijk','1991-07-08','Netherlands','Liverpool','Defender');
INSERT INTO "players" VALUES (36,'Andrew','Robertson','1994-03-11','Scotland','Liverpool','Defender');
INSERT INTO "players" VALUES (37,'Joe','Gomez','1997-05-23','England','Liverpool','Defender');
INSERT INTO "players" VALUES (38,'Fabinho','','1991-10-23','Brazil','Liverpool','Midfielder');
INSERT INTO "players" VALUES (39,'Jordan','Henderson','1990-06-17','England','Liverpool','Midfielder');
INSERT INTO "players" VALUES (40,'Thiago','Alcantara','1991-04-11','Spain','Liverpool','Midfielder');
INSERT INTO "players" VALUES (41,'Georginio','Wijnaldum','1990-11-11','Netherlands','Liverpool','Midfielder');
INSERT INTO "players" VALUES (42,'Sadio','Mane','1992-04-10','Senegal','Liverpool','Attacker');
INSERT INTO "players" VALUES (43,'Roberto','Firmino','1991-10-02','Brazil','Liverpool','Attacker');
INSERT INTO "players" VALUES (44,'Mohamed','Salah','1992-06-15','Egypt','Liverpool','Attacker');
INSERT INTO "players" VALUES (45,'Diogo','Jota','1996-12-04','Portugal','Liverpool','Attacker');
INSERT INTO "players" VALUES (46,'Jordan','Pickford','1994-03-07','England','Everton','Goalkeeper');
INSERT INTO "players" VALUES (47,'Robin','Olsen','1990-01-08','Sweden','Everton','Goalkeeper');
INSERT INTO "players" VALUES (48,'Seamus','Coleman','1988-10-11','Ireland','Everton','Defender');
INSERT INTO "players" VALUES (49,'Yerry','Mina','1994-09-23','Columbia','Everton','Defender');
INSERT INTO "players" VALUES (50,'Michael','Keane','1993-01-11','England','Everton','Defender');
INSERT INTO "players" VALUES (51,'Lucas','Digne','1993-07-20','France','Everton','Defender');
INSERT INTO "players" VALUES (52,'Ben','Godfrey','1998-01-15','England','Everton','Defender');
INSERT INTO "players" VALUES (53,'Allan','','1991-01-08','Brazil','Everton','Midfielder');
INSERT INTO "players" VALUES (54,'Abdoulaye','Doucoure','1993-01-01','France','Everton','Midfielder');
INSERT INTO "players" VALUES (55,'James','Rodriguez','1991-07-12','Columbia','Everton','Midfielder');
INSERT INTO "players" VALUES (56,'Gylfi','Sigurdsson','1989-09-08','Iceland','Everton','Midfielder');
INSERT INTO "players" VALUES (57,'Richarlison','','1997-05-10','Brazil','Everton','Attacker');
INSERT INTO "players" VALUES (58,'Dominic','Calvert-Lewin','1998-03-16','England','Everton','Attacker');
INSERT INTO "players" VALUES (59,'Alex','Iwobi','1996-05-03','Nigeria','Everton','Attacker');
INSERT INTO "players" VALUES (60,'Bernard','','1992-09-08','Brazil','Everton','Attacker');
INSERT INTO "players" VALUES (61,'Antonio','Rudiger','1993-03-03','Germany','Chelsea','Defender');
INSERT INTO "players" VALUES (62,'Tammy','Abraham','1997-10-02','England','Chelsea','Attacker');
COMMIT;
