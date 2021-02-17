BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "field" (
	"id"	INTEGER,
	"title"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "gender" (
	"id"	INTEGER,
	"title"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "publication_type" (
	"id"	INTEGER,
	"title"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "role" (
	"id"	INTEGER,
	"title"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "person" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"date_of_birth"	TEXT,
	"gender_id"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "scientific_work" (
	"id"	INTEGER,
	"title"	TEXT NOT NULL,
	"type"	INTEGER,
	"year"	INTEGER,
	"tags"	TEXT,
	"field"	INTEGER,
	"content" TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("field") REFERENCES "field"("id"),
	FOREIGN KEY("type") REFERENCES "publication_type"("id")
);
CREATE TABLE IF NOT EXISTS "author" (
	"id"	INTEGER,
	"person_id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("person_id") REFERENCES "person"
);
CREATE TABLE IF NOT EXISTS "scientific_work_author" (
	"author_id"	INTEGER,
	"scientific_work_id"	INTEGER,
	FOREIGN KEY("author_id") REFERENCES "author"("id"),
	FOREIGN KEY("scientific_work_id") REFERENCES "scientific_work"("id")
);
CREATE TABLE IF NOT EXISTS "user" (
	"id"	INTEGER,
	"username"	TEXT,
	"password"	TEXT,
	"email"	TEXT,
	"image"	TEXT,
	"person_id"	INTEGER,
	"role_id"	INTEGER,
	FOREIGN KEY("role_id") REFERENCES "role"("id"),
	PRIMARY KEY("id"),
	FOREIGN KEY("person_id") REFERENCES "person"("id")
);
INSERT INTO "gender" VALUES (1,'male');
INSERT INTO "gender" VALUES (2,'female');
INSERT INTO "field" VALUES (1,'computer science');
INSERT INTO "field" VALUES (2,'engineering');
INSERT INTO "field" VALUES (3,'psychology');
INSERT INTO "field" VALUES (4,'mathematics');
INSERT INTO "field" VALUES (5,'history');
INSERT INTO "field" VALUES (6,'medicine');
INSERT INTO "field" VALUES (7,'art');
INSERT INTO "field" VALUES (8,'business');
INSERT INTO "field" VALUES (9,'sociology');
INSERT INTO "field" VALUES (10,'biology');
INSERT INTO "field" VALUES (11,'other');
INSERT INTO "publication_type" VALUES (1,'book');
INSERT INTO "publication_type" VALUES (2,'journal article');
INSERT INTO "publication_type" VALUES (3,'letter');
INSERT INTO "publication_type" VALUES (4,'patent');
INSERT INTO "publication_type" VALUES (5,'other');
INSERT INTO "role" VALUES (1,'administrator');
INSERT INTO "role" VALUES (2,'member');
INSERT INTO "person" VALUES (1,'Lotfi','Zadeh','04/02/1921',1);
INSERT INTO "person" VALUES (2,'Ela','Kagocic','20/12/1997',2);
INSERT INTO "person" VALUES (3,'Lejla','Kafedzic','20/12/1997',2);
INSERT INTO "person" VALUES (4,'James','Collip','19/07/1965',1);
INSERT INTO "author" VALUES (1,1);
INSERT INTO "author" VALUES (2,4);
INSERT INTO "scientific_work" VALUES (1,'Fuzzy sets',1,1965,'fuzzy, artificial intelligence, mathematics, set',1);
INSERT INTO "scientific_work" VALUES (2,'Pancreatic extracts in the treatment of diabetes mellitus',1,1992,'medicine, endocrinology',6);
INSERT INTO "scientific_work_author" VALUES (1,1);
INSERT INTO "scientific_work_author" VALUES (2,2);
INSERT INTO "user" VALUES (1,'admin','adminadmin','admin@gmail.com',NULL,2,1);
INSERT INTO "user" VALUES (2,'lejla','lejlalejla','lejla@gmail.com',NULL,3,2);
COMMIT;
