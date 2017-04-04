/**8888888
 * Author:  nicolasl
 * Created: Mar 20, 2017
 */

DROP TABLE MESSAGE_SALLE_DISCUSSION ;
DROP TABLE MESSAGE_REPAIRE ; 
DROP TABLE DECISION_LOUP ; 
DROP TABLE DECISION_HUMAIN ; 
DROP TABLE JOUEUR ; 
DROP TABLE PARTIE ;
DROP TABLE MEMBRE ;  

CREATE SEQUENCE id_seq ; 

CREATE TABLE MEMBRE (
    login varchar(10) NOT NULL PRIMARY KEY,
    password varchar(10) NOT NULL
);


CREATE TABLE JOUEUR (
    login varchar(10) NOT NULL , 
    rolePartie int , 
    Statut int CONSTRAINT StatPos CHECK (Statut = 0 OR Statut = 1), 
    Pouvoir varchar(20),
    IdPartie int, 
    CONSTRAINT loginForeign FOREIGN KEY (login) REFERENCES MEMBRE(login),
    PRIMARY KEY (login)
) ; 


CREATE TABLE MESSAGE_SALLE_DISCUSSION (
    idPartie int NOT NULL,
    login_expediteur varchar(10) NOT NULL, 
    contenu varchar(100) NOT NULL, 
    date_envoi date, 
    CONSTRAINT login_expediteurForeign FOREIGN KEY 
        (login_expediteur) REFERENCES JOUEUR(login) ,
    PRIMARY KEY (login_expediteur, date_envoi)
);

CREATE TABLE MESSAGE_REPAIRE (
    idPartie int NOT NULL,
    login_expediteur varchar(10) NOT NULL, 
    contenu varchar(100) NOT NULL, 
    date_envoi date, 
    CONSTRAINT login_expediteurForeign2 FOREIGN KEY 
        (login_expediteur) REFERENCES JOUEUR(login) ,
    PRIMARY KEY (login_expediteur, date_envoi)
    
);

CREATE TABLE DECISION_LOUP (
    login_joueur_concerne varchar(10) NOT NULL PRIMARY KEY, 
    id_partie int, 
    login_expeditaire varchar(10) NOT NULL, 
    ratifie int CONSTRAINT EtatValide CHECK (ratifie = 0 OR ratifie = 1), 
    date_envoi date, 
    nbreVote int, 
    votant1 varchar(10),
    votant2 varchar(10),
    votant3 varchar(10),
    votant4 varchar(10),
    votant5 varchar(10),
    votant6 varchar(10),
    votant7 varchar(10),
    votant8 varchar(10),
    votant9 varchar(10),
    votant10 varchar(10),
    votant11 varchar(10),
    votant12 varchar(10),
    votant13 varchar(10)
);

CREATE TABLE DECISION_HUMAIN (
    login_joueur_concerne varchar(10) NOT NULL PRIMARY KEY, 
    id_partie int,
    login_expeditaire varchar(10) NOT NULL, 
    ratifie int CONSTRAINT EtatValide2 CHECK (ratifie = 0 OR ratifie = 1), 
    date_envoi date, 
    nbreVote int, 
    votant1 varchar(10),
    votant2 varchar(10),
    votant3 varchar(10),
    votant4 varchar(10),
    votant5 varchar(10),
    votant6 varchar(10),
    votant7 varchar(10),
    votant8 varchar(10),
    votant9 varchar(10),
    votant10 varchar(10),
    votant11 varchar(10),
    votant12 varchar(10),
    votant13 varchar(10)
);


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
    nbJoueursVivants int,
    CONSTRAINT creatorForeign FOREIGN KEY (login) REFERENCES MEMBRE(login)
) ; 




INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (5, 10, 1, 1, 1490714515, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (2, 2, 1, 1, 10, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (10, 20, 1, 1, 10, 0.2, 0.5) ; 

/*INSERT INTO DECISION_HUMAIN VALUES (1, 321, 'bagouc', 'loulou', 0, SYSDATE, 1 , null, null , null , null , null ,
 null , null , null , null , null , null , null , null );
*/

/*
SELECT * FROM MESSAGE_SALLE_DISCUSSION;
SELECT * FROM MESSAGE_REPAIRE;
SELECT * FROM DECISION_HUMAIN; 
SELECT * FROM PARTIE; 
*/
SELECT * FROM JOUEUR;

