/**
 * Author:  nicolasl
 * Created: Mar 20, 2017
 */

DROP TABLE MEMBRE ; 
DROP TABLE JOUEUR ; 
DROP TABLE PARTIE ; 

CREATE SEQUENCE id_seq ; 

CREATE TABLE MEMBRE (
    login varchar(10) NOT NULL PRIMARY KEY,
    password varchar(10) NOT NULL
);


CREATE TABLE JOUEUR (
    login varchar(10) NOT NULL , 
    Rôle varchar(10) , 
    Statut int CONSTRAINT StatPos CHECK (Statut = 0 OR Statut = 1), 
    Pouvoir varchar(10),
    IdPartie int, 
    CONSTRAINT loginForeign FOREIGN KEY (login) REFERENCES MEMBRE(login)
) ; 



CREATE TABLE PARTIE (
    IdPartie number(3) DEFAULT id_seq.nextval PRIMARY KEY , 
    login varchar(10) , 
    NbJoueursMin int CONSTRAINT NbJouPos CHECK (NbJoueursMin > 0) , 
    NbJoueursMax int CONSTRAINT NbJouMaxPos CHECK (NbJoueursMax > 0) , 
    DureeJour int CONSTRAINT DureeJPos CHECK (DureeJour > 0),
    DureeNuit int CONSTRAINT DureeNPos CHECK (DureeNuit > 0), 
    HeureDebut int CONSTRAINT DebutPos CHECK (HeureDebut > 0),
    ProbaPouvoir float CONSTRAINT ProbaPos CHECK (ProbaPouvoir >= 0.0 AND ProbaPouvoir <= 1.0 ),
    ProportionLG float CONSTRAINT PropPos CHECK (ProportionLG > 0.0), 
    CONSTRAINT creatorForeign FOREIGN KEY (login) REFERENCES MEMBRE(login)
) ; 




INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (5, 10, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (10, 20, 1, 1, 8, 0.2, 0.5) ; 

 
SELECT * FROM MEMBRE;
