--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ambulatorio; Type: TABLE; Schema: public; Owner: alvenza
--

CREATE TABLE ambulatorio (
    id_ambulatorio character varying(100) NOT NULL,
    specializzazione character varying(100),
    nome character varying(100),
    via character varying(100),
    citta character varying(100),
    provincia character varying(100),
    cap character varying(5),
    anno_stip integer,
    id_azienda character varying(100)
);


ALTER TABLE ambulatorio OWNER TO alvenza;

--
-- Name: azienda_sanitaria; Type: TABLE; Schema: public; Owner: alvenza
--

CREATE TABLE azienda_sanitaria (
    id_azienda character varying(100) NOT NULL,
    nome character varying(100),
    via character varying(100),
    citta character varying(100),
    provincia character varying(100),
    cap character varying(5),
    num_dip integer
);


ALTER TABLE azienda_sanitaria OWNER TO alvenza;

--
-- Name: erogazioneprestazioni; Type: TABLE; Schema: public; Owner: justin
--

CREATE TABLE erogazioneprestazioni (
    id_erogprest integer NOT NULL,
    id_ambulatorio character varying(100),
    id_prestazione character varying(100)
);


ALTER TABLE erogazioneprestazioni OWNER TO justin;

--
-- Name: erogazioneprestazioni_id_erogprest_seq; Type: SEQUENCE; Schema: public; Owner: justin
--

CREATE SEQUENCE erogazioneprestazioni_id_erogprest_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE erogazioneprestazioni_id_erogprest_seq OWNER TO justin;

--
-- Name: erogazioneprestazioni_id_erogprest_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justin
--

ALTER SEQUENCE erogazioneprestazioni_id_erogprest_seq OWNED BY erogazioneprestazioni.id_erogprest;


--
-- Name: esito; Type: TABLE; Schema: public; Owner: alvenza
--

CREATE TABLE esito (
    id_esito integer NOT NULL,
    esito text
);


ALTER TABLE esito OWNER TO alvenza;

--
-- Name: esito_id_esito_seq; Type: SEQUENCE; Schema: public; Owner: alvenza
--

CREATE SEQUENCE esito_id_esito_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE esito_id_esito_seq OWNER TO alvenza;

--
-- Name: esito_id_esito_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alvenza
--

ALTER SEQUENCE esito_id_esito_seq OWNED BY esito.id_esito;


--
-- Name: medico; Type: TABLE; Schema: public; Owner: alvenza
--

CREATE TABLE medico (
    cf_medico character varying(100) NOT NULL,
    nome character varying(100),
    cognome character varying(100),
    email character varying(100),
    via character varying(100),
    citta character varying(100),
    cap character varying(5),
    provincia character varying(100)
);


ALTER TABLE medico OWNER TO alvenza;

--
-- Name: pazienti; Type: TABLE; Schema: public; Owner: justin
--

CREATE TABLE pazienti (
    cf_paziente character varying(100) NOT NULL,
    nome character varying(100),
    cognome character varying(100),
    email character varying(100),
    via character varying(100),
    citta character varying(100),
    provincia character varying(100),
    cap character varying(5),
    id_azienda character varying(100)
);


ALTER TABLE pazienti OWNER TO justin;

--
-- Name: prestazioni; Type: TABLE; Schema: public; Owner: alvenza
--

CREATE TABLE prestazioni (
    id_prestazione character varying(100) NOT NULL,
    nomeprestazione character varying(100),
    descrizione character varying(100)
);


ALTER TABLE prestazioni OWNER TO alvenza;

--
-- Name: utenti; Type: TABLE; Schema: public; Owner: alvenza
--

CREATE TABLE utenti (
    id_email character varying(100) NOT NULL,
    password text NOT NULL,
    ruolo character varying(100) NOT NULL
);


ALTER TABLE utenti OWNER TO alvenza;

--
-- Name: visite; Type: TABLE; Schema: public; Owner: justin
--

CREATE TABLE visite (
    id_visita integer NOT NULL,
    data_visita date,
    ora character varying(5),
    urgenza character varying(100),
    regime character varying(100),
    avvenutavisita character varying(100),
    id_ambulatorio character varying(100),
    id_paziente character varying(100),
    id_medico character varying(100),
    id_esito integer,
    note text,
    id_prestazione character varying(100)
);


ALTER TABLE visite OWNER TO justin;

--
-- Name: visite_id_visita_seq; Type: SEQUENCE; Schema: public; Owner: justin
--

CREATE SEQUENCE visite_id_visita_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE visite_id_visita_seq OWNER TO justin;

--
-- Name: visite_id_visita_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justin
--

ALTER SEQUENCE visite_id_visita_seq OWNED BY visite.id_visita;


--
-- Name: erogazioneprestazioni id_erogprest; Type: DEFAULT; Schema: public; Owner: justin
--

ALTER TABLE ONLY erogazioneprestazioni ALTER COLUMN id_erogprest SET DEFAULT nextval('erogazioneprestazioni_id_erogprest_seq'::regclass);


--
-- Name: esito id_esito; Type: DEFAULT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY esito ALTER COLUMN id_esito SET DEFAULT nextval('esito_id_esito_seq'::regclass);


--
-- Name: visite id_visita; Type: DEFAULT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite ALTER COLUMN id_visita SET DEFAULT nextval('visite_id_visita_seq'::regclass);


--
-- Data for Name: ambulatorio; Type: TABLE DATA; Schema: public; Owner: alvenza
--

COPY ambulatorio (id_ambulatorio, specializzazione, nome, via, citta, provincia, cap, anno_stip, id_azienda) FROM stdin;
AMB007	Cardiologia	Ambulatorio Gondola	San Marco, 3	Venezia	VE	33200	2001	ASL204
AMB004	Colonscopia	Ambulatorio Co. Lon.	Via bucherelli, 66	Verona	VR	37138	2001	ASL209
AMB011	Fisioterapia	Ambulatorio da Carlo	Martiri d'Istria e Dalmazia	Legnago	VR	37045	2010	ASL209
AMB008	Traumatologia	Ambulatorio E. Gregorietto	Esperanto 15	Treviso	TV	31100	2000	ASL202
AMB010	Visita ginecologica	Ambulatorio di Franco Guarato	Respighi, 15	Venezia	VE	37044	2006	ASL203
AMB003	Ginecologia	Ambulatorio Barca	Gondolieri allegri, 4	Padova	PD	36100	1990	ASL206
AMB002	Neurologia	Ambulatorio T. Dr. Etro	Magia nera, 12	Verona	VR	37138	1995	ASL209
AMB012	Chirurgia Plastica	Ambulatorio Pasetti Lina	Via Nizza, 44	Rovigo	RO	45102	1999	ASL205
AMB001	Traumatologia	Ambulatorio De Rossi	Torricelli, 19	Belluno	BL	32100	2016	ASL201
AMB005	Cardiochirurgia	Ambulatorio Gatti	Lettiere, 2	Vicenza	VI	32301	2005	ASL207
AMB006	Reumatologia	Ambulatorio M. Alessere	del Dolore, 1	Vicenza	VI	32301	2002	ASL208
AMB009	Cardiologia	Ambulatorio Bassi Marco	Via monte bianco, 33	Belluno	BL	32104	2009	ASL201
\.


--
-- Data for Name: azienda_sanitaria; Type: TABLE DATA; Schema: public; Owner: alvenza
--

COPY azienda_sanitaria (id_azienda, nome, via, citta, provincia, cap, num_dip) FROM stdin;
ASL201	ASL 1 - Dolomiti	via Feltre, 57	Belluno	BL	32100	180
ASL202	Asl 2 - Marca Trevigiana	Borgo Cavalli, 42	Treviso	TV	31100	200
ASL203	Asl 3 - Serenissima	via Don Federico Tosatto, 14	Venezia	VE	30174	164
ASL204	Asl 4 - Veneto Orientale	piazza de Gasperi, 5	San Dona' di Piave	VE	30027	144
ASL205	Asl 5 - Polesana	viale Tre Martiri, 89	Rovigo	RO	45100	112
ASL206	Asl 6 - Euganea	via E. degli Scrovegni, 14	Padova	PD	35131	280
ASL207	Asl 7 - Pedemontana	via dei Lotti, 40	Bassano del Grappa	VI	35061	84
ASL208	Asl 8 - Berica	viale Rodolfi, 37	Vicenza	VI	36100	221
ASL209	Asl 9 - Scaligera	via Valverde, 42	Verona	VR	37122	280
\.


--
-- Data for Name: erogazioneprestazioni; Type: TABLE DATA; Schema: public; Owner: justin
--

COPY erogazioneprestazioni (id_erogprest, id_ambulatorio, id_prestazione) FROM stdin;
1	AMB001	PREST001
2	AMB001	PREST002
42	AMB011	PREST005
43	AMB012	PREST006
44	AMB012	PREST007
45	AMB012	PREST008
46	AMB001	PREST009
7	AMB002	PREST003
8	AMB002	PREST004
9	AMB002	PREST001
10	AMB003	PREST002
11	AMB003	PREST004
12	AMB003	PREST001
13	AMB001	PREST004
47	AMB001	PREST010
48	AMB001	PREST011
49	AMB001	PREST012
50	AMB002	PREST013
51	AMB002	PREST014
52	AMB002	PREST015
53	AMB003	PREST016
54	AMB003	PREST017
23	AMB004	PREST001
17	AMB004	PREST002
24	AMB004	PREST003
25	AMB005	PREST004
55	AMB003	PREST018
27	AMB005	PREST002
28	AMB006	PREST004
29	AMB006	PREST002
30	AMB006	PREST005
31	AMB007	PREST001
32	AMB007	PREST003
26	AMB005	PREST003
33	AMB007	PREST002
59	AMB005	PREST022
37	AMB010	PREST001
38	AMB010	PREST004
39	AMB010	PREST003
40	AMB011	PREST003
41	AMB011	PREST004
60	AMB005	PREST023
61	AMB005	PREST024
56	AMB004	PREST019
57	AMB004	PREST020
58	AMB004	PREST021
62	AMB006	PREST025
63	AMB006	PREST026
64	AMB006	PREST027
65	AMB007	PREST028
66	AMB007	PREST029
67	AMB007	PREST030
68	AMB008	PREST031
69	AMB008	PREST032
70	AMB008	PREST033
71	AMB010	PREST034
72	AMB010	PREST035
73	AMB010	PREST036
74	AMB011	PREST037
75	AMB011	PREST038
76	AMB011	PREST039
77	AMB012	PREST040
34	AMB008	PREST002
36	AMB008	PREST005
78	AMB008	PREST040
79	AMB009	PREST028
80	AMB009	PREST029
81	AMB009	PREST030
82	AMB009	PREST032
83	AMB009	PREST015
84	AMB009	PREST016
\.


--
-- Name: erogazioneprestazioni_id_erogprest_seq; Type: SEQUENCE SET; Schema: public; Owner: justin
--

SELECT pg_catalog.setval('erogazioneprestazioni_id_erogprest_seq', 84, true);


--
-- Data for Name: esito; Type: TABLE DATA; Schema: public; Owner: alvenza
--

COPY esito (id_esito, esito) FROM stdin;
1	Molto Buono
2	Buono
3	Insomma
4	Malino
5	Male
6	Molto Male
7	Nessuna Speranza
\.


--
-- Name: esito_id_esito_seq; Type: SEQUENCE SET; Schema: public; Owner: alvenza
--

SELECT pg_catalog.setval('esito_id_esito_seq', 1, true);


--
-- Data for Name: medico; Type: TABLE DATA; Schema: public; Owner: alvenza
--

COPY medico (cf_medico, nome, cognome, email, via, citta, cap, provincia) FROM stdin;
MED002	Gianluca	Bianchi	bianchi@medico.com	Giuseppe Verdi, 12	Verona	37100	VR
MED001	Mario	Rossi	rossi@medico.com	Esperanto, 14	Verona	37100	VR
MED003	Gianmarco	Bestemmia	dc@alvi.it	Via Vo, 54	Treviso	45600	TV
MED004	Roberto	Furfaro	medic@medico.com	delle minigone, 5	Verona	37100	VR
MED005	Francesco	Totti	f.totter@medico.com	palla medica, 7	Treviso	43200	TV
MED006	Nereo	Percaso	nerxcaso@medico.it	san remo, 42	Verona	37056	VR
\.


--
-- Data for Name: pazienti; Type: TABLE DATA; Schema: public; Owner: justin
--

COPY pazienti (cf_paziente, nome, cognome, email, via, citta, provincia, cap, id_azienda) FROM stdin;
ALVVNZ12A34B567C	Alvise	Vivenza	alvenza@gmail.com	San Nazaro	Verona	VR	37100	ASL209
JTOSNC92A27Z129A	Justin	Rigon	justin.rigon.92@gmail.com	Stradone Sabbion 2/b	cologna veneta	VR	37044	ASL209
GNMCLD12A34B567C	Gianmarco	Caldana	gianmarco.caldana@gmail.com	Roma	Verona	VR	37100	ASL209
GGEMDN86B02Z100O	Meridian	giga	Adi199020@gmail.com	hkjhkjhklh	verona	VR	37131	ASL209
ZCCLSE95D56A459P	Elisa	Zocca	zoccaelisa95@gmail.com	Corte, 16	Vestenanova	VR	37030	ASL209
\.


--
-- Data for Name: prestazioni; Type: TABLE DATA; Schema: public; Owner: alvenza
--

COPY prestazioni (id_prestazione, nomeprestazione, descrizione) FROM stdin;
PREST054	Test da sforzo	esempio di descrizione della prestazione
PREST053	TAC	esempio di descrizione della prestazione
PREST051	Senologia	esempio di descrizione della prestazione
PREST052	Spermiogramma	esempio di descrizione della prestazione
PREST050	Rettoscopia	esempio di descrizione della prestazione
PREST049	Radiologia	esempio di descrizione della prestazione
PREST048	Radiografia	esempio di descrizione della prestazione
PREST047	Psichiatria	esempio di descrizione della prestazione
PREST010	Chirurgia plastica	esempio di descrizione della prestazione
PREST012	Colonscopia	esempio di descrizione della prestazione
PREST016	Diatermocoagulazione	esempio di descrizione della prestazione
PREST021	Endocrinologia	esempio di descrizione della prestazione
PREST019	Elettrocardiogramma	esempio di descrizione della prestazione
PREST017	Ecocolordoppler	esempio di descrizione della prestazione
PREST020	Ematologia	esempio di descrizione della prestazione
PREST024	Flebologia	esempio di descrizione della prestazione
PREST022	Endoscopia	esempio di descrizione della prestazione
PREST013	Densitometria ossea	esempio di descrizione della prestazione
PREST011	Chirurgia vascolare	esempio di descrizione della prestazione
PREST009	Chirurgia proctologica	esempio di descrizione della prestazione
PREST008	Cardiologia	esempio di descrizione della prestazione
PREST007	Biopsia	esempio di descrizione della prestazione
PREST006	Breath test	esempio di descrizione della prestazione
PREST005	Audiovestibologia	esempio di descrizione della prestazione
PREST004	Angiologia	esempio di descrizione della prestazione
PREST003	Angiografia	esempio di descrizione della prestazione
PREST002	Alzheimer U.V.A.	esempio di descrizione della prestazione
PREST001	Allergologia	esempio di descrizione della prestazione
PREST014	Dermatologia	esempio di descrizione della prestazione
PREST018	Ecocardiografia	esempio di descrizione della prestazione
PREST015	Diabetologia	esempio di descrizione della prestazione
PREST059	Urografia	esempio di descrizione della prestazione
PREST058	Urologia	esempio di descrizione della prestazione
PREST057	Uroflussometria	esempio di descrizione della prestazione
PREST056	Uretroscopia	esempio di descrizione della prestazione
PREST055	Tossicodipendenza	esempio di descrizione della prestazione
PREST046	Proctologia	esempio di descrizione della prestazione
PREST045	Prick test	esempio di descrizione della prestazione
PREST044	Pneumologia	esempio di descrizione della prestazione
PREST043	Pediatria	esempio di descrizione della prestazione
PREST042	Pap test	esempio di descrizione della prestazione
PREST041	Ortopedia	esempio di descrizione della prestazione
PREST040	Oncologia	esempio di descrizione della prestazione
PREST039	Odontoiatria	esempio di descrizione della prestazione
PREST038	Oculistica	esempio di descrizione della prestazione
PREST026	Fluorangiografia	esempio di descrizione della prestazione
PREST035	Nafrologia	esempio di descrizione della prestazione
PREST037	Neuromotoria	esempio di descrizione della prestazione
PREST036	Neurologia	esempio di descrizione della prestazione
PREST034	Mammografia	esempio di descrizione della prestazione
PREST033	Logopedia	esempio di descrizione della prestazione
PREST032	Impedenzometria	esempio di descrizione della prestazione
PREST031	Infiltrazioni	esempio di descrizione della prestazione
PREST029	Ginecologia e Ostetricia	esempio di descrizione della prestazione
PREST028	Geriatria	esempio di descrizione della prestazione
PREST025	Foniatria	esempio di descrizione della prestazione
PREST023	Fisiatria	esempio di descrizione della prestazione
PREST027	Gastroenterologia	esempio di descrizione della prestazione
PREST030	Holter	esempio di descrizione della prestazione
dd	dddss	ciao
\.


--
-- Data for Name: utenti; Type: TABLE DATA; Schema: public; Owner: alvenza
--

COPY utenti (id_email, password, ruolo) FROM stdin;
justin.rigon.92@gmail.com	$2a$08$ngQIV60hunLJXIueTgc3OudhCIBxlyxxSrCJHS4hXWpzQnYhE6AE2	PAZIENTE
alvenza@gmail.com	$2a$08$rxWEqJRO0kEZCiY/xKGQfOQDpzvDSpXV0/Cf2/SXwMWvR0fz86Pfa	PAZIENTE
gianmarco.caldana@gmail.com	$2a$08$6mgXO6dW8cf/vWroRct1eOdg6ZWCQJGMpn3qfPIGojFEwqd.TdxpC	PAZIENTE
asl1@asl.com	$2a$08$WrI/y4ak9DmpYUm01UNFreBi.nUPiJ0qXq7wskD8JYNy3jBZiKhXW	PERSONALE ASL
amb1@amb.com	$2a$08$iWuA2UtpiaCLW7jPxhlIlOtQNUQVViMmkUUX3Rrr1wtJTCV3oSlsO	PERSONALE AMBULATORIO
Adi199020@gmail.com	$2a$08$.1h0r5zrMJ//KXlIUh5fye4/379ZwcHhaVzeFowXiEmYm0R3wSdA6	PAZIENTE
zoccaelisa95@gmail.com	$2a$08$APKd.EV09eZwa4F0uyFlAOm8AA9nt3ld8zexAzqKWjBJusj5Waipm	PAZIENTE
admin	$2a$08$0bGnOOTbF0fuYKHOBmzCoefWHhwxIDhmnhc/txgjdBFOvnTIAXBzO	ADMIN
med1@med.com	$2a$08$Sa8vFrQE4ayTz4tKwHhd1Odm1iBHJMYMcVkm0lsDTB3PQLkqnp.HK	MEDICO
\.


--
-- Data for Name: visite; Type: TABLE DATA; Schema: public; Owner: justin
--

COPY visite (id_visita, data_visita, ora, urgenza, regime, avvenutavisita, id_ambulatorio, id_paziente, id_medico, id_esito, note, id_prestazione) FROM stdin;
40	2017-06-17	08:00	\N	Nazionale	NO	AMB004	JTOSNC92A27Z129A	\N	\N	jkhkd	PREST002
42	2017-06-29	15:00	\N	Di Solvenza	NO	AMB004	ALVVNZ12A34B567C	\N	\N	òlkserjdòfldsk	PREST003
44	2017-06-24	10:00	\N	Nazionale	NO	AMB004	GNMCLD12A34B567C	\N	\N	Allergia al cortisone	PREST001
14	2017-05-25	08:00	Codice Verde	Nazionale	SI	AMB002	JTOSNC92A27Z129A	MED001	3	Nessuna Nota Disponibile	PREST002
13	2017-05-25	11:00	Codice Giallo	Nazionale	SI	AMB002	JTOSNC92A27Z129A	MED001	4	Nessuna Nota Disponibile	PREST002
12	2017-05-25	12:00	Codice Rosso	Nazionale	SI	AMB002	JTOSNC92A27Z129A	MED001	7	Nessuna Nota Disponibile	PREST002
22	2017-06-15	08:00	\N	Extra Moenia	NO	AMB001	JTOSNC92A27Z129A	MED002	4	Nessuna Nota Disponibile	PREST002
24	2017-05-27	08:00	Codice Rosso	Di Solvenza	SI	AMB002	JTOSNC92A27Z129A	MED001	7	Nessuna Nota Disponibile	PREST004
25	2017-05-27	09:00	Codice Rosso	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	6	Nessuna Nota Disponibile	PREST002
26	2017-05-27	11:00	Codice Verde	Di Solvenza	SI	AMB002	JTOSNC92A27Z129A	MED001	2	Nessuna Nota Disponibile	PREST004
27	2017-05-27	10:00	Codice Giallo	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	4	Nessuna Nota Disponibile	PREST002
29	2017-06-05	11:00	\N	Nazionale	NO	AMB002	GNMCLD12A34B567C	MED004	1	Nessuna Nota Disponibile	PREST004
28	2017-06-14	08:00	\N	Nazionale	NO	AMB010	ALVVNZ12A34B567C	MED002	1	Nessuna Nota Disponibile	PREST002
46	2017-06-30	10:00	\N	Nazionale	NO	AMB004	GNMCLD12A34B567C	\N	\N	prova allerologica standard	PREST001
21	2017-05-26	11:00	Codice Rosso	Extra Moenia	SI	AMB001	JTOSNC92A27Z129A	MED001	7	Nessuna Nota Disponibile	PREST004
23	2017-05-31	09:00	\N	Extra Moenia	NO	AMB002	JTOSNC92A27Z129A	MED002	5	Nessuna Nota Disponibile	PREST001
33	2017-06-01	09:00	\N	Nazionale	NO	AMB001	JTOSNC92A27Z129A	MED003	3	Nessuna Nota Disponibile	PREST001
48	2017-06-30	09:00	\N	Nazionale	NO	AMB002	JTOSNC92A27Z129A	\N	\N	,daslkjahsdl	PREST013
49	2017-07-14	10:00	\N	Nazionale	NO	AMB004	GNMCLD12A34B567C	\N	\N	prova numero 1	PREST001
31	2017-05-29	08:00	\N	Di Solvenza	NO	AMB002	JTOSNC92A27Z129A	MED002	2	Nessuna Nota Disponibile	PREST004
32	2017-05-31	09:00	\N	Di Solvenza	NO	AMB001	JTOSNC92A27Z129A	MED002	2	Nessuna Nota Disponibile	PREST001
35	2017-06-30	09:00	\N	Di Solvenza	NO	AMB001	JTOSNC92A27Z129A	MED003	3	Nessuna Nota Disponibile	PREST001
37	2017-06-28	12:00	\N	Di Solvenza	NO	AMB004	JTOSNC92A27Z129A	MED004	5	Nessuna Nota Disponibile	PREST002
39	2017-06-25	09:00	\N	Di Solvenza	NO	AMB001	ALVVNZ12A34B567C	MED005	7	Nessuna Nota Disponibile	PREST002
51	2017-07-02	09:00	Codice Verde	Di Solvenza	SI	AMB011	GNMCLD12A34B567C	MED001	2	 ajgnjondoèndgoègn	PREST005
53	2017-07-26	13:00	Codice Verde	Nazionale	NO	AMB002	GNMCLD12A34B567C	MED003	3	prova ancora	PREST014
55	2017-07-06	08:00	\N	Nazionale	NO	AMB004	GGEMDN86B02Z100O	\N	\N	mal di pancia	PREST001
57	2017-07-23	12:00	\N	Di Solvenza	NO	AMB002	GGEMDN86B02Z100O	\N	\N	a presto	PREST013
59	2017-07-14	11:00	\N	Di Solvenza	NO	AMB011	ZCCLSE95D56A459P	\N	\N	Problema di miopia all'occhio sinistro. 	PREST038
41	2017-06-17	12:00	\N	Nazionale	NO	AMB004	JTOSNC92A27Z129A	\N	\N	hgf	PREST002
43	2017-06-24	14:00	\N	Nazionale	NO	AMB011	GNMCLD12A34B567C	\N	\N		PREST004
45	2017-06-30	14:00	\N	Di Solvenza	NO	AMB002	GNMCLD12A34B567C	\N	\N	Acne sul mento	PREST014
47	2017-06-23	08:00	\N	Nazionale	NO	AMB002	GNMCLD12A34B567C	\N	\N	Brufoli in fronte	PREST014
52	2017-10-20	08:00	\N	Nazionale	NO	AMB004	JTOSNC92A27Z129A	\N	\N	ciao	PREST001
50	2017-07-01	12:00	\N	Nazionale	NO	AMB004	GNMCLD12A34B567C	MED002	2	abcdefghijklmnopqrsubpinoòmkù,è	PREST002
54	2017-07-15	09:00	Codice Verde	Nazionale	SI	AMB004	GNMCLD12A34B567C	MED001	1	j gjiafg kjdngèajdsgkjsdmg	PREST001
56	2017-07-23	11:00	\N	Nazionale	NO	AMB002	GGEMDN86B02Z100O	\N	\N	oh oh che dolor	PREST003
58	2017-10-19	13:00	\N	Nazionale	NO	AMB011	GGEMDN86B02Z100O	\N	\N	urgenza	PREST038
6	2017-05-24	08:00	Codice Verde	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	1	Nessuna Nota Disponibile	PREST001
18	2017-05-29	13:00	\N	Nazionale	NO	AMB001	JTOSNC92A27Z129A	MED004	2	Nessuna Nota Disponibile	PREST001
17	2017-05-27	14:00	Codice Rosso	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	7	Nessuna Nota Disponibile	PREST002
16	2017-05-26	14:00	Codice Verde	Nazionale	SI	AMB002	JTOSNC92A27Z129A	MED001	1	Nessuna Nota Disponibile	PREST004
15	2017-05-26	10:00	Codice Giallo	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	2	Nessuna Nota Disponibile	PREST003
9	2017-05-25	12:00	Codice Rosso	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	4	Nessuna Nota Disponibile	PREST001
7	2017-05-25	08:00	Codice Rosso	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	2	Nessuna Nota Disponibile	PREST001
11	2017-05-25	16:00	Codice Giallo	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	6	Nessuna Nota Disponibile	PREST002
10	2017-05-25	14:00	Codice Verde	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	5	Nessuna Nota Disponibile	PREST001
8	2017-05-25	11:00	Codice Giallo	Nazionale	SI	AMB001	JTOSNC92A27Z129A	MED001	3	Nessuna Nota Disponibile	PREST001
19	2017-05-29	14:00	\N	Nazionale	NO	AMB001	JTOSNC92A27Z129A	MED004	3	Nessuna Nota Disponibile	PREST002
34	2017-06-30	08:00	\N	Nazionale	NO	AMB001	JTOSNC92A27Z129A	MED003	4	Nessuna Nota Disponibile	PREST001
36	2017-06-08	08:00	\N	Di Solvenza	NO	AMB001	ALVVNZ12A34B567C	MED004	2	Nessuna Nota Disponibile	PREST002
38	2017-06-25	11:00	\N	Nazionale	NO	AMB001	ALVVNZ12A34B567C	MED005	4	Nessuna Nota Disponibile	PREST002
\.


--
-- Name: visite_id_visita_seq; Type: SEQUENCE SET; Schema: public; Owner: justin
--

SELECT pg_catalog.setval('visite_id_visita_seq', 59, true);


--
-- Name: ambulatorio ambulatorio_nome_key; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY ambulatorio
    ADD CONSTRAINT ambulatorio_nome_key UNIQUE (nome);


--
-- Name: ambulatorio ambulatorio_pkey; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY ambulatorio
    ADD CONSTRAINT ambulatorio_pkey PRIMARY KEY (id_ambulatorio);


--
-- Name: azienda_sanitaria azienda_sanitaria_nome_key; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY azienda_sanitaria
    ADD CONSTRAINT azienda_sanitaria_nome_key UNIQUE (nome);


--
-- Name: azienda_sanitaria azienda_sanitaria_pkey; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY azienda_sanitaria
    ADD CONSTRAINT azienda_sanitaria_pkey PRIMARY KEY (id_azienda);


--
-- Name: erogazioneprestazioni erogazioneprestazioni_pkey; Type: CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY erogazioneprestazioni
    ADD CONSTRAINT erogazioneprestazioni_pkey PRIMARY KEY (id_erogprest);


--
-- Name: esito esito_pkey; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY esito
    ADD CONSTRAINT esito_pkey PRIMARY KEY (id_esito);


--
-- Name: medico medico_email_key; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT medico_email_key UNIQUE (email);


--
-- Name: medico medico_pkey; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT medico_pkey PRIMARY KEY (cf_medico);


--
-- Name: pazienti pazienti_email_key; Type: CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY pazienti
    ADD CONSTRAINT pazienti_email_key UNIQUE (email);


--
-- Name: pazienti pazienti_pkey; Type: CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY pazienti
    ADD CONSTRAINT pazienti_pkey PRIMARY KEY (cf_paziente);


--
-- Name: prestazioni prestazioni_nomeprestazione_key; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY prestazioni
    ADD CONSTRAINT prestazioni_nomeprestazione_key UNIQUE (nomeprestazione);


--
-- Name: prestazioni prestazioni_pkey; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY prestazioni
    ADD CONSTRAINT prestazioni_pkey PRIMARY KEY (id_prestazione);


--
-- Name: utenti utenti_pkey; Type: CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY utenti
    ADD CONSTRAINT utenti_pkey PRIMARY KEY (id_email);


--
-- Name: visite visite_pkey; Type: CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite
    ADD CONSTRAINT visite_pkey PRIMARY KEY (id_visita);


--
-- Name: ambulatorio ambulatorio_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alvenza
--

ALTER TABLE ONLY ambulatorio
    ADD CONSTRAINT ambulatorio_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES azienda_sanitaria(id_azienda) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: erogazioneprestazioni erogazioneprestazioni_id_ambulatorio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY erogazioneprestazioni
    ADD CONSTRAINT erogazioneprestazioni_id_ambulatorio_fkey FOREIGN KEY (id_ambulatorio) REFERENCES ambulatorio(id_ambulatorio) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: erogazioneprestazioni erogazioneprestazioni_id_prestazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY erogazioneprestazioni
    ADD CONSTRAINT erogazioneprestazioni_id_prestazione_fkey FOREIGN KEY (id_prestazione) REFERENCES prestazioni(id_prestazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: pazienti id_azienda; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY pazienti
    ADD CONSTRAINT id_azienda FOREIGN KEY (id_azienda) REFERENCES azienda_sanitaria(id_azienda) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: visite id_prestazione; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite
    ADD CONSTRAINT id_prestazione FOREIGN KEY (id_prestazione) REFERENCES prestazioni(id_prestazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: visite visite_id_ambulatorio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite
    ADD CONSTRAINT visite_id_ambulatorio_fkey FOREIGN KEY (id_ambulatorio) REFERENCES ambulatorio(id_ambulatorio) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: visite visite_id_esito_fkey; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite
    ADD CONSTRAINT visite_id_esito_fkey FOREIGN KEY (id_esito) REFERENCES esito(id_esito) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: visite visite_id_medico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite
    ADD CONSTRAINT visite_id_medico_fkey FOREIGN KEY (id_medico) REFERENCES medico(cf_medico) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: visite visite_id_paziente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: justin
--

ALTER TABLE ONLY visite
    ADD CONSTRAINT visite_id_paziente_fkey FOREIGN KEY (id_paziente) REFERENCES pazienti(cf_paziente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

