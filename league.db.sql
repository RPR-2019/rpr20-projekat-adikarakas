BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "fixtures" (
	"home_team"	TEXT,
	"away_team"	TEXT,
	FOREIGN KEY("away_team") REFERENCES "clubs"("name"),
	FOREIGN KEY("home_team") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "clubs" (
	"name"	TEXT,
	"nickname"	TEXT,
	"stadium"	TEXT,
	"mascot"	TEXT,
	"manager"	TEXT,
	"captain"	INTEGER DEFAULT null,
	FOREIGN KEY("captain") REFERENCES "players"("id"),
	PRIMARY KEY("name")
);
CREATE TABLE IF NOT EXISTS "players" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"date"	TEXT,
	"nationality"	TEXT,
	"club"	TEXT,
	"position"	TEXT,
	FOREIGN KEY("club") REFERENCES "clubs"("name"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "results" (
	"id"	INTEGER,
	"home_team"	TEXT,
	"away_team"	TEXT,
	"home_team_goals"	INTEGER,
	"away_team_goals"	INTEGER,
	FOREIGN KEY("home_team") REFERENCES "clubs"("name"),
	FOREIGN KEY("away_team") REFERENCES "clubs"("name"),
	PRIMARY KEY("id")
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
	FOREIGN KEY("result") REFERENCES "results"("id"),
	FOREIGN KEY("scorer") REFERENCES "players"("id"),
	FOREIGN KEY("assistent") REFERENCES "players"("id"),
	FOREIGN KEY("club") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "stats" (
	"id"	INTEGER,
	"appearances"	INTEGER,
	"clean_sheets"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "fixtures" VALUES ('Chelsea','Arsenal');
INSERT INTO "fixtures" VALUES ('Arsenal','Chelsea');
INSERT INTO "clubs" VALUES ('Liverpool','The Reds','Anfield','','Jurgen Klopp',39);
INSERT INTO "clubs" VALUES ('Chelsea','The Blues','Stamford Bridge','','Thomas Tuchel',52);
INSERT INTO "clubs" VALUES ('Arsenal','The Gunners','Emirates','','Mikel Arteta',28);
INSERT INTO "clubs" VALUES ('Southampton','Saints','St. Mary''s','','Ralph Hasenhuttl',68);
INSERT INTO "clubs" VALUES ('Burnley','Clarets','Turf Moor','','Sean Dyche',79);
INSERT INTO "clubs" VALUES ('Everton','The Toffes','Goodison Park','','Carlo Ancelotti',5);
INSERT INTO "players" VALUES (1,'Mat','Ryan','2021-05-07','Bangladesh','Arsenal','Goalkeeper');
INSERT INTO "players" VALUES (2,'Bernd','Leno','2021-04-29','Bahrain','Arsenal','Goalkeeper');
INSERT INTO "players" VALUES (3,'Jordan','Pickford','2021-04-29','Bahamas','Everton','Goalkeeper');
INSERT INTO "players" VALUES (4,'Robin','Olsen','2021-04-29','Bahamas','Everton','Goalkeeper');
INSERT INTO "players" VALUES (5,'Seamus','Coleman','2021-04-29','Bahrain','Everton','Defender');
INSERT INTO "players" VALUES (6,'Michael','Keane','2021-05-06','Bahamas','Everton','Defender');
INSERT INTO "players" VALUES (7,'Yerry','Mina','2021-05-05','Bahamas','Everton','Defender');
INSERT INTO "players" VALUES (8,'Lucas','Digne','2021-05-05','Barbados','Everton','Defender');
INSERT INTO "players" VALUES (9,'Mason','Holgate','2021-04-29','Bangladesh','Everton','Defender');
INSERT INTO "players" VALUES (10,'Allan',' ','2021-04-29','Brazil','Everton','Midfielder');
INSERT INTO "players" VALUES (11,'Abdoulaye','Doucoure','2021-05-12','Bangladesh','Everton','Midfielder');
INSERT INTO "players" VALUES (12,'James','Rodriguez','2021-04-28','Barbados','Everton','Midfielder');
INSERT INTO "players" VALUES (13,'Gylfi','Sigurdsson','2021-04-29','Azerbaijan','Everton','Midfielder');
INSERT INTO "players" VALUES (14,'Richarlison',' ','2021-04-29','Barbados','Everton','Attacker');
INSERT INTO "players" VALUES (15,'Dominic','Calvert-Lewin','2021-05-04','Bangladesh','Everton','Attacker');
INSERT INTO "players" VALUES (16,'Alex','Iwobi','2021-05-05','Bahrain','Everton','Attacker');
INSERT INTO "players" VALUES (17,'Bernard',' ','2021-05-13','Bahamas','Everton','Attacker');
INSERT INTO "players" VALUES (18,'Hector','Bellerin','2021-05-18','Barbados','Arsenal','Defender');
INSERT INTO "players" VALUES (19,'Rob','Holding','2021-04-28','Bahrain','Arsenal','Defender');
INSERT INTO "players" VALUES (20,'David','Luiz','2021-05-06','Bahamas','Arsenal','Defender');
INSERT INTO "players" VALUES (21,'Kieran','Tierney','2021-04-29','Bangladesh','Arsenal','Defender');
INSERT INTO "players" VALUES (22,'Callum','Chambers','2021-04-28','Bangladesh','Arsenal','Defender');
INSERT INTO "players" VALUES (23,'Granit','Xhaka','2021-04-29','Bahrain','Arsenal','Midfielder');
INSERT INTO "players" VALUES (24,'Thomas','Partey','2021-04-29','Bangladesh','Arsenal','Midfielder');
INSERT INTO "players" VALUES (25,'Emile','Smith Rowe','2021-05-06','Bahrain','Arsenal','Midfielder');
INSERT INTO "players" VALUES (26,'Daniel','Ceballos','2021-04-29','Bahrain','Arsenal','Midfielder');
INSERT INTO "players" VALUES (27,'Bukayo','Saka','2021-05-06','Bangladesh','Arsenal','Attacker');
INSERT INTO "players" VALUES (28,'Pierre Emerick','Aubameyang','2021-04-29','Bahrain','Arsenal','Attacker');
INSERT INTO "players" VALUES (29,'Nicolas','Pepe','2021-04-30','Bahrain','Arsenal','Attacker');
INSERT INTO "players" VALUES (30,'Alexandre','Lacazette','2021-04-29','Azerbaijan','Arsenal','Attacker');
INSERT INTO "players" VALUES (31,'Alisson','Becker','2021-04-29','Bahamas','Liverpool','Goalkeeper');
INSERT INTO "players" VALUES (32,'Caoimhin','Kelleher','2021-04-29','Bahamas','Liverpool','Goalkeeper');
INSERT INTO "players" VALUES (33,'Trent','Alexander-Arnold','2021-04-30','Bahamas','Liverpool','Defender');
INSERT INTO "players" VALUES (34,'Virgil','Van Dijk','2021-05-07','Bangladesh','Liverpool','Defender');
INSERT INTO "players" VALUES (35,'Joel','Matip','2021-04-29','Bahrain','Liverpool','Defender');
INSERT INTO "players" VALUES (36,'Andrew','Robertson','2021-04-29','Azerbaijan','Liverpool','Defender');
INSERT INTO "players" VALUES (37,'Joe','Gomez','2021-05-12','Bahamas','Liverpool','Defender');
INSERT INTO "players" VALUES (38,'Fabinho',' ','2021-05-07','Bahamas','Liverpool','Midfielder');
INSERT INTO "players" VALUES (39,'Jordan','Henderson','2021-05-06','Bahrain','Liverpool','Midfielder');
INSERT INTO "players" VALUES (40,'Thiago','Alcantara','2021-04-29','Bahamas','Liverpool','Midfielder');
INSERT INTO "players" VALUES (41,'Georginio','Wijnaldum','2021-05-06','Bahamas','Liverpool','Midfielder');
INSERT INTO "players" VALUES (42,'Mohamed','Salah','2021-05-06','Bahrain','Liverpool','Attacker');
INSERT INTO "players" VALUES (43,'Roberto','Firmino','2021-04-29','Bahamas','Liverpool','Attacker');
INSERT INTO "players" VALUES (44,'Sadio','Mane','2021-04-28','Bahrain','Liverpool','Attacker');
INSERT INTO "players" VALUES (45,'Diogo','Jota','2021-05-05','Bahamas','Liverpool','Attacker');
INSERT INTO "players" VALUES (46,'Edouard','Mendy','2021-05-06','Bahrain','Chelsea','Goalkeeper');
INSERT INTO "players" VALUES (47,'Kepa','Arrizabalaga','2021-05-06','Bahamas','Chelsea','Goalkeeper');
INSERT INTO "players" VALUES (48,'Reece','James','2021-04-29','Bahrain','Chelsea','Defender');
INSERT INTO "players" VALUES (49,'Thiago','Silva','2021-05-05','Bahrain','Chelsea','Defender');
INSERT INTO "players" VALUES (50,'Kurt','Zouma','2021-04-30','Bahamas','Chelsea','Defender');
INSERT INTO "players" VALUES (51,'Ben','Chilwell','2021-04-29','Azerbaijan','Chelsea','Defender');
INSERT INTO "players" VALUES (52,'Cezar','Azpilicueta','2021-05-05','Azerbaijan','Chelsea','Defender');
INSERT INTO "players" VALUES (53,'N''Golo','Kante','2021-04-30','Bahamas','Chelsea','Midfielder');
INSERT INTO "players" VALUES (54,'Mateo','Kovačić','2021-04-29','Bahamas','Chelsea','Midfielder');
INSERT INTO "players" VALUES (55,'Mason','Mount','2021-04-29','Bahamas','Chelsea','Midfielder');
INSERT INTO "players" VALUES (56,'Kai','Havertz','2021-04-29','Bahamas','Chelsea','Midfielder');
INSERT INTO "players" VALUES (57,'Christian','Pulišić','2021-05-06','Azerbaijan','Chelsea','Attacker');
INSERT INTO "players" VALUES (58,'Timo','Werner','2021-04-30','Bahamas','Chelsea','Attacker');
INSERT INTO "players" VALUES (59,'Hakim','Ziyech','2021-04-29','Azerbaijan','Chelsea','Attacker');
INSERT INTO "players" VALUES (60,'Olivier','Giroud','2021-04-29','Bahamas','Chelsea','Attacker');
INSERT INTO "players" VALUES (61,'Alex','McCarthy','2002-04-29','England','Southampton','Goalkeeper');
INSERT INTO "players" VALUES (62,'Fraser','Forster','2000-09-28','England','Southampton','Goalkeeper');
INSERT INTO "players" VALUES (63,'Kyle','Walker-Peters','2021-05-05','England','Southampton','Defender');
INSERT INTO "players" VALUES (64,'Jan','Bednarek','2021-05-05','Poland','Southampton','Defender');
INSERT INTO "players" VALUES (65,'Jannik','Vestergaard','2021-04-30','Denmark','Southampton','Defender');
INSERT INTO "players" VALUES (66,'Ryan','Bertrand','2021-05-12','England','Southampton','Defender');
INSERT INTO "players" VALUES (67,'Jack','Stephens','2021-04-30','England','Southampton','Defender');
INSERT INTO "players" VALUES (68,'James','Ward-Prowse','2021-04-27','Azerbaijan','Southampton','Midfielder');
INSERT INTO "players" VALUES (69,'Oriol','Romeu','2021-05-06','Bahrain','Southampton','Midfielder');
INSERT INTO "players" VALUES (70,'Stuart','Armstrong','2021-05-20','Bahamas','Southampton','Midfielder');
INSERT INTO "players" VALUES (71,'Ibrahima','Diallo','2021-05-06','France','Southampton','Midfielder');
INSERT INTO "players" VALUES (72,'Nathan','Redmond','2021-05-05','United Kingdom','Southampton','Attacker');
INSERT INTO "players" VALUES (73,'Danny','Ings','2021-05-06','Bangladesh','Southampton','Attacker');
INSERT INTO "players" VALUES (74,'Che','Adams','2021-05-10','Bangladesh','Southampton','Attacker');
INSERT INTO "players" VALUES (75,'Theo','Walcott','2021-05-05','Bangladesh','Southampton','Attacker');
INSERT INTO "players" VALUES (76,'Nick','Pope','2021-04-29','Azerbaijan','Burnley','Goalkeeper');
INSERT INTO "players" VALUES (77,'Bailey','Peacock-Farell','2021-04-30','Bangladesh','Burnley','Goalkeeper');
INSERT INTO "players" VALUES (78,'Matthew','Lowton','2021-05-13','Bangladesh','Burnley','Defender');
INSERT INTO "players" VALUES (79,'Ben','Mee','2021-05-06','Belarus','Burnley','Defender');
INSERT INTO "players" VALUES (80,'James','Tarkowski','2021-05-05','Belgium','Burnley','Defender');
INSERT INTO "players" VALUES (81,'Charlie','Taylor','2021-05-13','Bangladesh','Burnley','Defender');
INSERT INTO "players" VALUES (82,'Eric','Pieters','2021-05-13','Bangladesh','Burnley','Defender');
INSERT INTO "players" VALUES (83,'Jack','Cork','2021-05-12','Belarus','Burnley','Midfielder');
INSERT INTO "players" VALUES (84,'Ashley','Westwood','2021-05-06','Barbados','Burnley','Midfielder');
INSERT INTO "players" VALUES (85,'Josh','Brownhill','2021-05-06','Bahrain','Burnley','Midfielder');
INSERT INTO "players" VALUES (86,'Dale','Stephens','2021-05-12','Belize','Burnley','Midfielder');
INSERT INTO "players" VALUES (87,'Dwight','McNeil','2021-05-11','Benin','Burnley','Attacker');
INSERT INTO "players" VALUES (88,'Chris','Wood','2021-05-18','Barbados','Burnley','Attacker');
INSERT INTO "players" VALUES (89,'Ashley','Barnes','2021-05-12','Austria','Burnley','Attacker');
INSERT INTO "players" VALUES (90,'Jay','Rodriguez','2021-05-06','Bahamas','Burnley','Attacker');
INSERT INTO "stats" VALUES (1,0,0);
INSERT INTO "stats" VALUES (2,0,0);
INSERT INTO "stats" VALUES (3,0,0);
INSERT INTO "stats" VALUES (4,0,0);
INSERT INTO "stats" VALUES (5,0,0);
INSERT INTO "stats" VALUES (6,0,0);
INSERT INTO "stats" VALUES (7,0,0);
INSERT INTO "stats" VALUES (8,0,0);
INSERT INTO "stats" VALUES (9,0,0);
INSERT INTO "stats" VALUES (10,0,0);
INSERT INTO "stats" VALUES (11,0,0);
INSERT INTO "stats" VALUES (12,0,0);
INSERT INTO "stats" VALUES (13,0,0);
INSERT INTO "stats" VALUES (14,0,0);
INSERT INTO "stats" VALUES (15,0,0);
INSERT INTO "stats" VALUES (16,0,0);
INSERT INTO "stats" VALUES (17,0,0);
INSERT INTO "stats" VALUES (18,0,0);
INSERT INTO "stats" VALUES (19,0,0);
INSERT INTO "stats" VALUES (20,0,0);
INSERT INTO "stats" VALUES (21,0,0);
INSERT INTO "stats" VALUES (22,0,0);
INSERT INTO "stats" VALUES (23,0,0);
INSERT INTO "stats" VALUES (24,0,0);
INSERT INTO "stats" VALUES (25,0,0);
INSERT INTO "stats" VALUES (26,0,0);
INSERT INTO "stats" VALUES (27,0,0);
INSERT INTO "stats" VALUES (28,0,0);
INSERT INTO "stats" VALUES (29,0,0);
INSERT INTO "stats" VALUES (30,0,0);
INSERT INTO "stats" VALUES (31,0,0);
INSERT INTO "stats" VALUES (32,0,0);
INSERT INTO "stats" VALUES (33,0,0);
INSERT INTO "stats" VALUES (34,0,0);
INSERT INTO "stats" VALUES (35,0,0);
INSERT INTO "stats" VALUES (36,0,0);
INSERT INTO "stats" VALUES (37,0,0);
INSERT INTO "stats" VALUES (38,0,0);
INSERT INTO "stats" VALUES (39,0,0);
INSERT INTO "stats" VALUES (40,0,0);
INSERT INTO "stats" VALUES (41,0,0);
INSERT INTO "stats" VALUES (42,0,0);
INSERT INTO "stats" VALUES (43,0,0);
INSERT INTO "stats" VALUES (44,0,0);
INSERT INTO "stats" VALUES (45,0,0);
INSERT INTO "stats" VALUES (46,0,0);
INSERT INTO "stats" VALUES (47,0,0);
INSERT INTO "stats" VALUES (48,0,0);
INSERT INTO "stats" VALUES (49,0,0);
INSERT INTO "stats" VALUES (50,0,0);
INSERT INTO "stats" VALUES (51,0,0);
INSERT INTO "stats" VALUES (52,0,0);
INSERT INTO "stats" VALUES (53,0,0);
INSERT INTO "stats" VALUES (54,0,0);
INSERT INTO "stats" VALUES (55,0,0);
INSERT INTO "stats" VALUES (56,0,0);
INSERT INTO "stats" VALUES (57,0,0);
INSERT INTO "stats" VALUES (58,0,0);
INSERT INTO "stats" VALUES (59,0,0);
INSERT INTO "stats" VALUES (60,0,0);
INSERT INTO "stats" VALUES (61,0,0);
INSERT INTO "stats" VALUES (62,0,0);
INSERT INTO "stats" VALUES (63,0,0);
INSERT INTO "stats" VALUES (64,0,0);
INSERT INTO "stats" VALUES (65,0,0);
INSERT INTO "stats" VALUES (66,0,0);
INSERT INTO "stats" VALUES (67,0,0);
INSERT INTO "stats" VALUES (68,0,0);
INSERT INTO "stats" VALUES (69,0,0);
INSERT INTO "stats" VALUES (70,0,0);
INSERT INTO "stats" VALUES (71,0,0);
INSERT INTO "stats" VALUES (72,0,0);
INSERT INTO "stats" VALUES (73,0,0);
INSERT INTO "stats" VALUES (74,0,0);
INSERT INTO "stats" VALUES (75,0,0);
INSERT INTO "stats" VALUES (76,0,0);
INSERT INTO "stats" VALUES (77,0,0);
INSERT INTO "stats" VALUES (78,0,0);
INSERT INTO "stats" VALUES (79,0,0);
INSERT INTO "stats" VALUES (80,0,0);
INSERT INTO "stats" VALUES (81,0,0);
INSERT INTO "stats" VALUES (82,0,0);
INSERT INTO "stats" VALUES (83,0,0);
INSERT INTO "stats" VALUES (84,0,0);
INSERT INTO "stats" VALUES (85,0,0);
INSERT INTO "stats" VALUES (86,0,0);
INSERT INTO "stats" VALUES (87,0,0);
INSERT INTO "stats" VALUES (88,0,0);
INSERT INTO "stats" VALUES (89,0,0);
INSERT INTO "stats" VALUES (90,0,0);
COMMIT;
