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
INSERT INTO "clubs" VALUES ('Chelsea','The Blues','Stamford Bridge','','Thomas Tuchel',7);
INSERT INTO "clubs" VALUES ('Liverpool','Reds','Anfield','','Jurgen Klopp',39);
INSERT INTO "clubs" VALUES ('Arsenal','Gunners','Emirates','Gunnersaurus','Mikel Arteta',28);
INSERT INTO "clubs" VALUES ('Everton','The Toffies','Goodison Park','','Carlo Ancelotti',48);
INSERT INTO "players" VALUES (1,'Edouard','Mendy','1992-03-01','Senegal','Chelsea','Goalkeeper');
INSERT INTO "players" VALUES (2,'Kepa','Arrizabalaga','1994-10-03','Spain','Chelsea','Goalkeeper');
INSERT INTO "players" VALUES (3,'Reece','James','2020-01-01','England','Chelsea','Defender');
INSERT INTO "players" VALUES (4,'Thiago','Silva','2020-01-01','Brazil','Chelsea','Defender');
INSERT INTO "players" VALUES (5,'Kurt','Zouma','2020-01-01','France','Chelsea','Defender');
INSERT INTO "players" VALUES (6,'Ben','Chilwell','2020-01-01','England','Chelsea','Defender');
INSERT INTO "players" VALUES (7,'Cesar','Azpilicueta','1989-08-28','Spain','Chelsea','Defender');
INSERT INTO "players" VALUES (8,'N''Golo','Kante','1991-03-29','France','Chelsea','Midfielder');
INSERT INTO "players" VALUES (9,'Mateo','Kovačić','2020-01-01','Hrvatska','Chelsea','Midfielder');
INSERT INTO "players" VALUES (10,'Mason','Mount','1999-01-10','England','Chelsea','Midfielder');
INSERT INTO "players" VALUES (11,'Kai','Havertz','1999-07-07','Germany','Chelsea','Midfielder');
INSERT INTO "players" VALUES (12,'Christian','Pulišić','2020-01-01','USA','Chelsea','Attacker');
INSERT INTO "players" VALUES (13,'Timo','Werner','2020-01-01','Germany','Chelsea','Attacker');
INSERT INTO "players" VALUES (14,'Hakim','Ziyech','2020-01-01','Morocco','Chelsea','Attacker');
INSERT INTO "players" VALUES (15,'Olivier','Giroud','2020-01-01','France','Chelsea','Attacker');
INSERT INTO "players" VALUES (16,'Bernd','Leno','2020-01-01','Germany','Arsenal','Goalkeeper');
INSERT INTO "players" VALUES (17,'Mat','Ryan','2020-01-01','Australia','Arsenal','Goalkeeper');
INSERT INTO "players" VALUES (18,'Hector','Bellerin','2020-01-01','Spain','Arsenal','Defender');
INSERT INTO "players" VALUES (19,'Pablo','Mari','2020-01-01','Spain','Arsenal','Defender');
INSERT INTO "players" VALUES (20,'David','Luiz','2020-01-01','Brazil','Arsenal','Defender');
INSERT INTO "players" VALUES (21,'Kieran','Tierney','2020-01-01','Scotland','Arsenal','Defender');
INSERT INTO "players" VALUES (22,'Calum','Chambers','2020-01-01','England','Arsenal','Defender');
INSERT INTO "players" VALUES (23,'Granit','Xhaka','2020-01-01','Switzerland','Arsenal','Midfielder');
INSERT INTO "players" VALUES (24,'Thomas','Partey','2020-01-01','Ghana','Arsenal','Midfielder');
INSERT INTO "players" VALUES (25,'Emile','Smith-Rowe','2020-01-01','England','Arsenal','Midfielder');
INSERT INTO "players" VALUES (26,'Martin','Odegaard','2020-01-01','Norway','Arsenal','Midfielder');
INSERT INTO "players" VALUES (27,'Nicolas','Pepe','2020-01-01','Ivory Coast','Arsenal','Attacker');
INSERT INTO "players" VALUES (28,'Pierre-Emerick','Aubameyang','2020-01-01','Gabon','Arsenal','Attacker');
INSERT INTO "players" VALUES (29,'Bukayo','Saka','2020-01-01','England','Arsenal','Attacker');
INSERT INTO "players" VALUES (30,'Alexandre','Lacazette','2020-01-01','France','Arsenal','Attacker');
INSERT INTO "players" VALUES (31,'Alisson','Becker','2020-01-01','Brazil','Liverpool','Goalkeeper');
INSERT INTO "players" VALUES (32,'Caoimhin','Kelleher','2020-01-01','Ireland','Liverpool','Goalkeeper');
INSERT INTO "players" VALUES (33,'Trent','Alexander-Arnold','2020-01-01','England','Liverpool','Defender');
INSERT INTO "players" VALUES (34,'Joel','Matip','2020-01-01','Cameroon','Liverpool','Defender');
INSERT INTO "players" VALUES (35,'Virgil','Van Dijk','2020-01-01','Netherlands','Liverpool','Defender');
INSERT INTO "players" VALUES (36,'Andrew','Robertson','2020-01-01','Scotland','Liverpool','Defender');
INSERT INTO "players" VALUES (37,'Joe','Gomez','2020-01-01','England','Liverpool','Defender');
INSERT INTO "players" VALUES (38,'Fabinho','','2020-01-01','Brazil','Liverpool','Midfielder');
INSERT INTO "players" VALUES (39,'Jordan','Henderson','2020-01-01','England','Liverpool','Midfielder');
INSERT INTO "players" VALUES (40,'Thiago','Alcantara','2020-01-01','Spain','Liverpool','Midfielder');
INSERT INTO "players" VALUES (41,'Georginio','Wijnaldum','2020-01-01','Netherlands','Liverpool','Midfielder');
INSERT INTO "players" VALUES (42,'Sadio','Mane','2020-01-01','Senegal','Liverpool','Attacker');
INSERT INTO "players" VALUES (43,'Roberto','Firmino','2020-01-01','Brazil','Liverpool','Attacker');
INSERT INTO "players" VALUES (44,'Mohamed','Salah','2020-01-01','Egypt','Liverpool','Attacker');
INSERT INTO "players" VALUES (45,'Diogo','Jota','2020-01-01','Portugal','Liverpool','Attacker');
INSERT INTO "players" VALUES (46,'Jordan','Pickford','2020-01-01','England','Everton','Goalkeeper');
INSERT INTO "players" VALUES (47,'Robin','Olsen','2020-01-01','Sweden','Everton','Goalkeeper');
INSERT INTO "players" VALUES (48,'Seamus','Coleman','2020-01-01','Ireland','Everton','Defender');
INSERT INTO "players" VALUES (49,'Yerry','Mina','2020-01-01','Columbia','Everton','Defender');
INSERT INTO "players" VALUES (50,'Michael','Keane','2020-01-01','England','Everton','Defender');
INSERT INTO "players" VALUES (51,'Lucas','Digne','2020-01-01','France','Everton','Defender');
INSERT INTO "players" VALUES (52,'Ben','Godfrey','2020-01-01','England','Everton','Defender');
INSERT INTO "players" VALUES (53,'Allan','','2020-01-01','Brazil','Everton','Midfielder');
INSERT INTO "players" VALUES (54,'Abdoulaye','Doucoure','2020-01-01','France','Everton','Midfielder');
INSERT INTO "players" VALUES (55,'James','Rodriguez','2020-01-01','Columbia','Everton','Midfielder');
INSERT INTO "players" VALUES (56,'Gylfi','Sigurdsson','2020-01-01','Iceland','Everton','Midfielder');
INSERT INTO "players" VALUES (57,'Richarlison','','2020-01-01','Brazil','Everton','Attacker');
INSERT INTO "players" VALUES (58,'Dominic','Calvert-Lewin','2020-01-01','England','Everton','Attacker');
INSERT INTO "players" VALUES (59,'Alex','Iwobi','2020-01-01','Nigeria','Everton','Attacker');
INSERT INTO "players" VALUES (60,'Bernard','','2020-01-01','Brazil','Everton','Attacker');
INSERT INTO "players" VALUES (61,'Antonio','Rudiger','2020-01-01','Germany','Chelsea','Defender');
INSERT INTO "players" VALUES (62,'Tammy','Abraham','2020-01-01','England','Chelsea','Attacker');
COMMIT;
