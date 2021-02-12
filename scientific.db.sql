BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "author" (
	"id"	INTEGER,
	"person_id"	INTEGER,
	FOREIGN KEY("person_id") REFERENCES "person",
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "person" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"date_of_birth"	TEXT,
	"gender" TEXT CHECK(gender='male' or gender='female'),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "role" (
	"id"	INTEGER,
	"title"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "scientific_work" (
	"id"	INTEGER,
	"title"	TEXT NOT NULL,
	"type"	TEXT,
	"year"	INTEGER,
	"tags"	TEXT,
	"language"	TEXT,
	"field"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "scientific_work_author" (
	"author_id"	INTEGER,
	"scientific_work_id"	INTEGER,
	FOREIGN KEY("scientific_work_id") REFERENCES "scientific_work"("id"),
	FOREIGN KEY("author_id") REFERENCES "author"("id")
);
CREATE TABLE IF NOT EXISTS "user" (
	"id"	INTEGER,
	"username"	TEXT,
	"password"	NUMERIC,
	"email"	TEXT,
	"image"	TEXT,
	"person_id"	INTEGER,
	"role_id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("person_id") REFERENCES "person"("id"),
	FOREIGN KEY("role_id") REFERENCES "role"("id")
);
COMMIT;
