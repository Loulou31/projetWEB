/**
 * Author:  nicolasl
 * Created: Mar 20, 2017
 */

DROP TABLE PARTIE ; 
DROP TABLE JOUEUR ; 

CREATE TABLE PARTIE (
    IdPartie int CONSTRAINT IdPartiePos CHECK (IdPartie >= 0) PRIMARY KEY , 
    NbJoueurs int CONSTRAINT NbJouPos CHECK (NbJoueurs > 0) , 
    DureeJour int CONSTRAINT DureeJPos CHECK (DureeJour > 0),
    DureeNuit int CONSTRAINT DureeNPos CHECK (DureeNuit > 0), 
    HeureDebut int CONSTRAINT DebutPos CHECK (HeureDebut > 0),
    ProbaPouvoir float CONSTRAINT ProbaPos CHECK (ProbaPouvoir > 0.0),
    ProportionLG float CONSTRAINT PropPos CHECK (ProportionLG > 0.0)
) ; 

CREATE TABLE JOUEUR (
    Pseudo varchar(10) NOT NULL PRIMARY KEY , 
    Rôle varchar(10) , 
    Statut int CONSTRAINT StatPos CHECK (Statut = 0 OR Statut = 1), 
    Pouvoir varchar(10),
    IdPartie int 
) ; 

CREATE TABLE MEMBRE (
    login varchar(10) NOT NULL PRIMARY KEY,
    password varchar(10) NOT NULL
);