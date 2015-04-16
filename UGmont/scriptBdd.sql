DROP TABLE FILM IF EXISTS;
DROP TABLE SALLE IF EXISTS;
DROP TABLE SEANCE IF EXISTS;

CREATE TABLE FILM (
	FILM_ID INT NOT NULL PRIMARY KEY,
	IMDB_ID VARCHAR(10)
);

CREATE TABLE SALLE (
	SALLE_ID INT NOT NULL PRIMARY KEY,
	NUMERO_SALLE INT,
	NB_PLACES INT,
	SUPPORT_3D BOOLEAN,
	FILM_ID INT,
  FOREIGN KEY (FILM_ID) REFERENCES FILM(FILM_ID)
);

INSERT INTO FILM VALUES (1, 'tt0120737');
INSERT INTO FILM VALUES (2, 'tt0133093');
INSERT INTO FILM VALUES (3, 'tt0780504');
INSERT INTO FILM VALUES (4, 'tt3612616');
INSERT INTO FILM VALUES (5, 'tt0075314');

INSERT INTO SALLE VALUES (1, 1, 500, true, null);
INSERT INTO SALLE VALUES (2, 2, 100, false, null);
INSERT INTO SALLE VALUES (3, 3, 60, false, null);
INSERT INTO SALLE VALUES (4, 4, 130, true, null);