create table DBCENTRE
(
	EIRCODE CHAR(8) not null
		primary key,
	NAME VARCHAR(50)
);

create table DBCOVID
(
	INFO CLOB not null,
	SYMPTOMS CLOB not null,
	ROLLOUT CLOB not null,
	TIMELINE CLOB not null,
	FIG CLOB
);

create table DBUSER
(
	USERID VARCHAR(25) not null
		primary key,
	PWORD VARCHAR(25),
	FNAME VARCHAR(25),
	LNAME VARCHAR(25),
	ACCTYPE VARCHAR(9)
);

create table DBCLINICIAN
(
	USERID VARCHAR(25) not null
		primary key
		references DBUSER
                ON DELETE CASCADE,
	POSITION VARCHAR(15)
);

create table DBPATIENT
(
	USERID VARCHAR(25) not null
		primary key
		references DBUSER
                ON DELETE CASCADE,
	EMAIL VARCHAR(25),
	EIRCODE CHAR(8),
	MOBILE CHAR(10),
	DOB TIMESTAMP,
	ELIG BOOLEAN,
	SCHED BOOLEAN
);

create table DBPHA
(
	USERID VARCHAR(25) not null
		primary key
		references DBUSER
                ON DELETE CASCADE
);

create table DBDISEASE
(
	NAME VARCHAR(25) not null
		primary key,
	PHAAUTHOR VARCHAR(25) not null
		references DBPHA
                ON DELETE CASCADE,
	LASTEDITED TIMESTAMP,
	INFO CLOB not null,
	SYMPTOMS CLOB not null,
	REMEDIES CLOB
);

create table DBVACCINE
(
	VACCID INTEGER not null
		primary key,
	NAME VARCHAR(30) not null
		unique,
	AUTHORISED BOOLEAN,
	DESCRIPTION VARCHAR(50),
	DOSES INTEGER,
	TEMP DOUBLE,
	EFFICACY DOUBLE
);

create table DBBATCH
(
	BATCHNO INTEGER not null
		primary key,
	VACCID INTEGER not null
		references DBVACCINE
                ON DELETE CASCADE,
	ORDEREDQTY INTEGER not null,
	QTYUSED INTEGER,
	EXPIRY TIMESTAMP
);

create table DBAPPOINTMENT
(
	APPOINTMENTID INTEGER not null
		primary key,
	PATIENT VARCHAR(25)
		references DBPATIENT
                ON DELETE CASCADE,
	CLINICIAN VARCHAR(25)
		references DBCLINICIAN
                ON DELETE CASCADE,
	APPDATE TIMESTAMP,
	APPSTATUS VARCHAR(15) not null,
	BATCH INTEGER
		references DBBATCH
                ON DELETE CASCADE,
	CENTRE CHAR(8)
		references DBCENTRE
                ON DELETE CASCADE
);

/*Centres*/
INSERT INTO ROOT.DBCENTRE (EIRCODE, NAME) VALUES ('V94 H521', 'St John''s Cathedral');
INSERT INTO ROOT.DBCENTRE (EIRCODE, NAME) VALUES ('D01 P5W9', 'Rotunda Hospital');
INSERT INTO ROOT.DBCENTRE (EIRCODE, NAME) VALUES ('T12 DV56', 'Bon Secours Hospital Galway');
/*COVID*/
INSERT INTO ROOT.DBCOVID (INFO, SYMPTOMS, ROLLOUT, TIMELINE, FIG) VALUES ('COVID-19 is an illness that can affect your lungs and airways. It''s caused by a virus called coronavirus.', 'Fever, Dry Cough, Tiredness', 'People are gonna be vaccinated or something', 'Began on the 12 March 2020. We have had 3 waves of the virus to date.', 'Around 500 a day');
/*User*/
INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('7482091L', 'ILikeGoats2367', 'Kanye', 'South', 'Patient');
INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('jclint897@hse.ie', 'ExcellentElegance', 'Johnny', 'Clintwood', 'PHA');
INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('lcotter546@hse.ie', 'PotsAndPans12', 'Leanne', 'Cotter', 'Clinician');
INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('ndrake198@hse.ie', 'ifoundShambala2009', 'Nathan', 'Drake', 'Clinician');
INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('5194903D', 'HaveYouSeen', 'Quebert', 'Johnson', 'Patient');
INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('1234567X', 'anthonyisbae', 'Shane', 'Pots', 'Patient');
/*Clinician*/
INSERT INTO ROOT.DBCLINICIAN (USERID, POSITION) VALUES ('lcotter546@hse.ie', 'Nurse');
INSERT INTO ROOT.DBCLINICIAN (USERID, POSITION) VALUES ('ndrake198@hse.ie', 'Doctor');
/*Patient*/
INSERT INTO ROOT.DBPATIENT (USERID, EMAIL, EIRCODE, MOBILE, DOB, ELIG, SCHED) VALUES ('7482091L', 'YeSouth183@gmail.com', 'Y21 W351', '0859041749', '1989-11-17 00:00:00.000000000', true, true);
INSERT INTO ROOT.DBPATIENT (USERID, EMAIL, EIRCODE, MOBILE, DOB, ELIG, SCHED) VALUES ('5194903D', 'QuebertJ800@gmaill.com', 'K9P KMX0', '0861912983', '1966-03-22 00:00:00.000000000', true, true);
INSERT INTO ROOT.DBPATIENT (USERID, EMAIL, EIRCODE, MOBILE, DOB, ELIG, SCHED) VALUES ('1234567X', 'potsshane@hotmail.com', 'T67 L2NT', '0876511234', '2000-06-08 00:00:00.000000000', true, true);
/*PHA*/
INSERT INTO ROOT.DBPHA (USERID) VALUES ('jclint897@hse.ie');
/*Diseases*/
INSERT INTO ROOT.DBDISEASE (NAME, PHAAUTHOR, LASTEDITED, INFO, SYMPTOMS, REMEDIES) VALUES ('SwineFlu', 'jclint897@hse.ie', '2021-03-22 16:56:27.000000000', 'The H1N1 flu, commonly known as swine flu, is primarily caused by the H1N1 strain of the flu (influenza) virus.', 'Fever, Chills, Cough, Sore Throat', 'Over-the-counter remedies, Washing hands, Balanced Diet');
INSERT INTO ROOT.DBDISEASE (NAME, PHAAUTHOR, LASTEDITED, INFO, SYMPTOMS, REMEDIES) VALUES ('COVID-19', 'jclint897@hse.ie', '2021-03-23 14:36:10.000000000', 'COVID-19 is an illness that can affect your lungs and airways.', 'Fever, Dry Cough, Tiredness, Difficulty Breathing', 'Washing hands, Stay in a well ventilated room');
INSERT INTO ROOT.DBDISEASE (NAME, PHAAUTHOR, LASTEDITED, INFO, SYMPTOMS, REMEDIES) VALUES ('Tuberculosis', 'jclint897@hse.ie', '2021-03-27 18:02:31.000000000', 'Tuberculosis (TB) is a disease caused by germs that are spread from person to person through the air.', 'Fever, Diarrhea, Fatigue, Muscle Aches, Coughing', 'Vaccination, Washing hands');
/*Vaccine*/
INSERT INTO ROOT.DBVACCINE (VACCID, NAME, AUTHORISED, DESCRIPTION, DOSES, TEMP, EFFICACY) VALUES (2, 'Moderna', true, 'RNA-Based', 2, -15, 0.94);
INSERT INTO ROOT.DBVACCINE (VACCID, NAME, AUTHORISED, DESCRIPTION, DOSES, TEMP, EFFICACY) VALUES (1, 'Pfizer/BioNTech', true, 'RNA-Based', 2, -70, 0.95);
INSERT INTO ROOT.DBVACCINE (VACCID, NAME, AUTHORISED, DESCRIPTION, DOSES, TEMP, EFFICACY) VALUES (3, 'AstraZeneca', false, 'Non-Replicating Viral Vector', 1, 2, 0.9);
/*Batch*/
INSERT INTO ROOT.DBBATCH (BATCHNO, VACCID, ORDEREDQTY, QTYUSED, EXPIRY) VALUES (1, 1, 200, 180, '2021-04-19 23:18:39.000000000');
INSERT INTO ROOT.DBBATCH (BATCHNO, VACCID, ORDEREDQTY, QTYUSED, EXPIRY) VALUES (2, 1, 240, 200, '2021-04-30 18:18:53.000000000');
INSERT INTO ROOT.DBBATCH (BATCHNO, VACCID, ORDEREDQTY, QTYUSED, EXPIRY) VALUES (3, 2, 300, 150, '2021-08-20 18:19:09.000000000');
INSERT INTO ROOT.DBBATCH (BATCHNO, VACCID, ORDEREDQTY, QTYUSED, EXPIRY) VALUES (4, 1, 400, 400, '2021-06-27 18:19:21.000000000');
INSERT INTO ROOT.DBBATCH (BATCHNO, VACCID, ORDEREDQTY, QTYUSED, EXPIRY) VALUES (5, 2, 100, 100, '2021-03-27 18:19:30.000000000');
/*Appointment*/
INSERT INTO ROOT.DBAPPOINTMENT (APPOINTMENTID, PATIENT, CLINICIAN, APPDATE, APPSTATUS, BATCH, CENTRE) VALUES (1, '7482091L', 'lcotter546@hse.ie', '2021-05-22 09:45:00.000000000', 'Authorised', 1, 'T12 DV56');
INSERT INTO ROOT.DBAPPOINTMENT (APPOINTMENTID, PATIENT, CLINICIAN, APPDATE, APPSTATUS, BATCH, CENTRE) VALUES (2, '5194903D', 'ndrake198@hse.ie', '2021-03-18 13:00:00.000000000', 'Absent', 1, 'D01 P5W9');
INSERT INTO ROOT.DBAPPOINTMENT (APPOINTMENTID, PATIENT, CLINICIAN, APPDATE, APPSTATUS, BATCH, CENTRE) VALUES (3, '5194903D', 'lcotter546@hse.ie', '2021-05-02 16:20:00.000000000', 'Authorised', 1, 'T12 DV56');