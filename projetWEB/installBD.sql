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
    login varchar(10) NOT NULL PRIMARY KEY, 
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

CREATE TABLE DECISION (
    login_expeditaire varchar(10) NOT NULL, 
    login_joueur_concerne varchar(10) NOT NULL, 
    est_valide int CONSTRAINT EtatValide CHECK (est_valide = 0 OR est_valide = 1), 
    date_envoi date,
    CONSTRAINT login_joueurForeign FOREIGN KEY 
        (login_joueur_concerne) REFERENCES JOUEUR(login), 
    CONSTRAINT login_expForeign FOREIGN KEY 
        (login_expeditaire) REFERENCES JOUEUR(login),
    PRIMARY KEY(login_expeditaire, date_envoi)
);

INSERT INTO PARTIE (NbJoueurs, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (5, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO PARTIE (NbJoueurs, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG)
VALUES (6, 1, 1, 8, 0.2, 0.5) ; 

INSERT INTO DECISION VALUES ('bagouc', 0);

SELECT * FROM MESSAGE_SALLE_DISCUSSION;
SELECT * FROM MESSAGE_REPAIRE;
SELECT * FROM DECISION; 

