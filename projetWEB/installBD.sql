/**
 * Author:  nicolasl
 * Created: Mar 20, 2017
 */



CREATE SEQUENCE id_seq ; 

CREATE TABLE PARTIE (
    IdPartie number(3) DEFAULT id_seq.nextval PRIMARY KEY , 
    NbJoueurs int CONSTRAINT NbJouPos CHECK (NbJoueurs > 0) , 
    DureeJour int CONSTRAINT DureeJPos CHECK (DureeJour > 0),
    DureeNuit int CONSTRAINT DureeNPos CHECK (DureeNuit > 0), 
    HeureDebut int CONSTRAINT DebutPos CHECK (HeureDebut > 0),
    ProbaPouvoir float CONSTRAINT ProbaPos CHECK (ProbaPouvoir > 0.0),
    ProportionLG float CONSTRAINT PropPos CHECK (ProportionLG > 0.0)
) ; 

CREATE TABLE JOUEUR (
    login varchar(10) NOT NULL, 
    Rôle varchar(10) , 
    Statut int CONSTRAINT StatPos CHECK (Statut = 0 OR Statut = 1), 
    Pouvoir varchar(10),
    IdPartie int, 
    CONSTRAINT loginForeign FOREIGN KEY (login) REFERENCES MEMBRE(login)
) ; 

CREATE TABLE MEMBRE (
    login varchar(10) NOT NULL PRIMARY KEY,
    password varchar(10) NOT NULL
);


INSERT INTO PARTIE (NbJoueurs, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (5, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueurs, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (6, 1, 1, 8, 0.2, 0.5) ; 

 
SELECT * FROM MEMBRE;
