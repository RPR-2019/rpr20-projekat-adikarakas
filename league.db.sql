BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "matches" (
	"home_team"	INTEGER,
	"away_team"	INTEGER
);
CREATE TABLE IF NOT EXISTS "clubs" (
	"name"	TEXT,
	"nickname"	TEXT,
	"stadium"	TEXT,
	"mascot"	TEXT,
	"manager"	TEXT,
	"captain"	INTEGER,
	FOREIGN KEY("captain") REFERENCES "players"("id"),
	PRIMARY KEY("name")
);
CREATE TABLE IF NOT EXISTS "fixtures" (
	"home_team"	TEXT,
	"away_team"	TEXT,
	FOREIGN KEY("home_team") REFERENCES "clubs"("name"),
	FOREIGN KEY("away_team") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "players" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"date"	TEXT,
	"nationality"	TEXT,
	"club"	TEXT,
	FOREIGN KEY("club") REFERENCES "clubs"("name"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "results" (
    "home_team" TEXT,
    "away_team" TEXT,
    "home_team_goals" INTEGER,
    "away_team_goals" INTEGER,
    FOREIGN KEY("home_team") REFERENCES "clubs"("name"),
    FOREIGN KEY("away_team") REFERENCES "clubs"("name")
);
CREATE TABLE IF NOT EXISTS "goals" (
    "scorer" INTEGER,
    "assistent" INTEGER,
    "minute" INTEGER,
    "goal_type" TEXT,
    "goal_situation" TEXT,
    "goal_distance" TEXT,
    "club" TEXT,
    FOREIGN KEY("club") REFERENCES "clubs"("name"),
    FOREIGN KEY("scorer") REFERENCES "players"("id"),
    FOREIGN KEY("assistent") REFERENCES "players"("id")
);
CREATE TABLE IF NOT EXISTS "stats" (
    "id" INTEGER,
    "apperances" INTEGER,
    "clean_sheets" INTEGER,
    PRIMARY KEY("id")
);
COMMIT;