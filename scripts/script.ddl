#@(#) script.ddl

CREATE TABLE person
(
	email varchar (255) NOT NULL,
	password varchar (255) NOT NULL,
	id_person integer AUTO_INCREMENT,
	PRIMARY KEY(id_person)
);

CREATE TABLE entry
(
	tittle varchar (255),
	text varchar (255),
	id_entry integer AUTO_INCREMENT,
	fk_personid_person integer NOT NULL,
	PRIMARY KEY(id_entry),
	FOREIGN KEY(fk_personid_person) REFERENCES person (id_person)
);
