CREATE TABLE company (
  company_id serial NOT NULL UNIQUE,
  company_name varchar(50) DEFAULT NULL,
  PRIMARY KEY (company_id)
) ;

CREATE TABLE person (
  person_id serial NOT NULL UNIQUE,
  person_name varchar(50) DEFAULT NULL,
  PRIMARY KEY (person_id)
) ;

CREATE TABLE person_company (
  company_id integer NOT NULL,
  person_id integer NOT NULL,
  PRIMARY KEY (company_id,person_id),
  CONSTRAINT company_person_fk_1 
   FOREIGN KEY (person_id) REFERENCES person (person_id),
  CONSTRAINT company_person_fk_2 
   FOREIGN KEY (company_id) REFERENCES company (company_id)
) ;