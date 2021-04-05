
DROP EXTENSION IF EXISTS pgcrypto CASCADE;
CREATE EXTENSION pgcrypto;

DROP TABLE IF EXISTS Azienda_Sanitaria CASCADE;
CREATE TABLE Azienda_Sanitaria (
  ID_Azienda VARCHAR(100) PRIMARY KEY,
  Nome VARCHAR(100) UNIQUE,
  Via VARCHAR(100),
  Citta VARCHAR(100),
  Provincia VARCHAR(100),
  Cap VARCHAR(5),
  Num_dip INTEGER
);

DROP TABLE IF EXISTS Ambulatorio CASCADE;
CREATE TABLE Ambulatorio (
  ID_Ambulatorio VARCHAR(100) PRIMARY KEY,
  Specializzazione VARCHAR(100),
  Nome VARCHAR(100) UNIQUE,
  Via VARCHAR(100),
  Citta VARCHAR(100),
  Provincia VARCHAR(100),
  Cap VARCHAR(5),
  Anno_stip INTEGER,
  ID_Azienda VARCHAR(100) REFERENCES Azienda_Sanitaria
);

DROP TABLE IF EXISTS Prestazioni CASCADE;
CREATE TABLE Prestazioni(
  ID_Prestazione VARCHAR(100) PRIMARY KEY,
  NomePrestazione VARCHAR (100) UNIQUE,
  Descrizione TEXT
);

DROP TABLE IF EXISTS Medico CASCADE;
CREATE TABLE Medico (
  CF_Medico VARCHAR(100) PRIMARY KEY,
  Nome VARCHAR(100),
  Cognome VARCHAR(100),
  eMail VARCHAR(100) UNIQUE,
  Via VARCHAR(100),
  Citta VARCHAR(100),
  Provincia VARCHAR(100),
  Cap VARCHAR(5)
);

DROP TABLE IF EXISTS Pazienti CASCADE;
CREATE TABLE Pazienti (
  CF_Paziente VARCHAR(100) PRIMARY KEY,
  Nome VARCHAR(100),
  Cognome VARCHAR(100),
  eMail VARCHAR(100) UNIQUE,
  Via VARCHAR(100),
  Citta VARCHAR(100),
  Provincia VARCHAR(100),
  Cap VARCHAR(5),
  ID_Azienda VARCHAR(100) REFERENCES Azienda_Sanitaria
);

DROP TABLE IF EXISTS Esito CASCADE;
CREATE TABLE Esito (
  ID_Esito serial PRIMARY KEY,
  Esito TEXT
);

DROP TABLE IF EXISTS Visite CASCADE;
CREATE TABLE Visite (
  ID_Visita serial PRIMARY KEY,
  data_visita DATE,
  ora VARCHAR(5),
  Urgenza VARCHAR(100),
  Regime VARCHAR(100),
  AvvenutaVisita VARCHAR(100),
  Note TEXT,
  ID_Ambulatorio VARCHAR(100) REFERENCES Ambulatorio, 
  ID_Paziente VARCHAR(100) REFERENCES Pazienti, 
  ID_Medico VARCHAR(100) REFERENCES Medico, 
  ID_Esito INTEGER REFERENCES Esito
);

DROP TABLE IF EXISTS ErogazionePrestazioni CASCADE;
CREATE TABLE ErogazionePrestazioni (
  ID_ErogPrest serial PRIMARY KEY,
  ID_Ambulatorio VARCHAR(100) REFERENCES Ambulatorio, 
  ID_Prestazione VARCHAR(100) REFERENCES Prestazioni
);

DROP TABLE IF EXISTS Utenti CASCADE;
CREATE TABLE Utenti (
  ID_email VARCHAR(100) NOT NULL PRIMARY KEY,
  password TEXT NOT NULL,
  ruolo VARCHAR(100) NOT NULL
);


