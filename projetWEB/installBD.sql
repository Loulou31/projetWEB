/**8888888
 * Author:  nicolasl
 * Created: Mar 20, 2017
 */


DROP TABLE MEMBRE ; 
DROP TABLE JOUEUR ; 
DROP TABLE PARTIE ;
DROP TABLE DECISION_HUMAIN; 
DROP TABLE DECISION_LOUP; 


CREATE SEQUENCE id_seq ; 

CREATE TABLE MEMBRE (
    login varchar(10) NOT NULL PRIMARY KEY,
    password varchar(10) NOT NULL
);


CREATE TABLE JOUEUR (
    login varchar(10) NOT NULL , 
    rolePartie int CONSTRAINT roleCheck (rolePartie >= 0 AND rolePartie < 3)  , 
    Statut int CONSTRAINT StatPos CHECK (Statut = 0 OR Statut = 1), 
    Pouvoir varchar(10),
    IdPartie int, 
    CONSTRAINT loginForeign FOREIGN KEY (login) REFERENCES MEMBRE(login)
) ; 


CREATE TABLE MESSAGE_SALLE_DISCUSSION (
    login_expediteur varchar(10) NOT NULL, 
    contenu varchar(100) NOT NULL, 
    date_envoi date, 
    PRIMARY KEY (login_expediteur, date_envoi), 
    CONSTRAINT login_expediteurForeign FOREIGN KEY 
        (login_expediteur) REFERENCES JOUEUR(login)
);

CREATE TABLE MESSAGE_REPAIRE (
    login_expediteur varchar(10) NOT NULL, 
    contenu varchar(100) NOT NULL, 
    date_envoi date, 
    PRIMARY KEY (login_expediteur, date_envoi), 
    CONSTRAINT login_expediteurForeign2 FOREIGN KEY 
        (login_expediteur) REFERENCES JOUEUR(login)
);

CREATE TABLE DECISION_LOUP (
    id_decision int primary key,
    id_partie int, 
    login_expeditaire varchar(10) NOT NULL, 
    login_joueur_concerne varchar(10) NOT NULL, 
    est_valide int CONSTRAINT EtatValide CHECK (est_valide = 0 OR est_valide = 1), 
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
    id_decision int primary key,
    id_partie int,
    login_expeditaire varchar(10) NOT NULL, 
    login_joueur_concerne varchar(10) NOT NULL, 
    est_valide int CONSTRAINT EtatValide2 CHECK (est_valide = 0 OR est_valide = 1), 
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
    CONSTRAINT creatorForeign FOREIGN KEY (login) REFERENCES MEMBRE(login)
) ; 




INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (5, 10, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (2, 2, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (10, 20, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO DECISION_HUMAIN VALUES (1, 321, 'bagouc', 'loulou', 0, SYSDATE, 1 , null, null , null , null , null ,
 null , null , null , null , null , null , null , null );

SELECT * FROM MESSAGE_SALLE_DISCUSSION;
SELECT * FROM MESSAGE_REPAIRE;
SELECT * FROM DECISION_HUMAIN; 
SELECT * FROM PARTIE; 

